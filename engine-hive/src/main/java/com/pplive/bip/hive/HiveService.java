package com.pplive.bip.hive;


import com.pplive.bip.engine.EngineService;
import com.pplive.bip.report.ResultColumn;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.parse.*;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
public class HiveService extends EngineService {

    private static final Logger LOG = LoggerFactory.getLogger(HiveService.class);

    private SemanticAnalyzer sa;
    private ParseDriver pb;

    private static Map<String, String> sqlToHiveTypesMap = new HashMap();

    @Autowired
    private DataSource dataSource;


    @Bean
    public DataSource dataSource() throws Exception{
        Resource resource = new ClassPathResource("/hive-center.xml");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        return BasicDataSourceFactory.createDataSource(props);
    }


    static {
        sqlToHiveTypesMap.put("BOOLEAN", "BIT");
        sqlToHiveTypesMap.put("TINYINT", "TINYINT");

    }


    @PostConstruct
    public boolean init(){
        boolean initSuccess = false;
        try {
            HiveConf conf = new HiveConf();
            SessionState.start(conf);
            SessionState.get().initTxnMgr(conf);
            sa = new SemanticAnalyzer(conf);
            pb = new ParseDriver();
            initSuccess = true;
        } catch (Exception e) {
            LOG.error("fail to init hive service", e);
        }

        return initSuccess;
    }


    /**
     * ref org.apache.hadoop.hive.ql.Driver.compile(String command)
     * @param hql
     * @return
     * @throws SQLException
     */
    private ParseContext parse(String hql) throws SQLException{
        for(int i = 0; i < 3; ++i) {
            try {
                Context ctx = new Context(SessionState.getSessionConf());
                ctx.setTryCount(2);
                ctx.setCmd(hql);
                ASTNode tree = pb.parse(hql);
                tree = ParseUtils.findRootNonNullToken(tree);
                sa.analyze(tree, ctx);
                break;
            }catch (IOException e1) {
                if (i < 2) {
                    init();
                } else {
                    throw new SQLException(e1);
                }
            }catch (ParseException e2) {
                LOG.error("parse error", e2);
                throw new SQLException(e2);
            }catch (SemanticException e3) {
                LOG.error("semantic error", e3);
                throw new SQLException(e3);
            }
        }

        return sa.getParseContext();
    }

    public Set<String> getQueryTable(String hql) throws SQLException{
        ParseContext pc = this.parse(hql);
        return pc.getTopOps().keySet();
    }

    public List<ResultColumn> getSchema(String hql) throws SQLException{
        List<ResultColumn> rc = new ArrayList<>();
        this.parse(hql);
        List<FieldSchema> schemas = sa.getResultSchema();
        schemas.forEach(s->{
            rc.add(new ResultColumn(s.getName(), s.getType()));
        });
        return rc;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
}
