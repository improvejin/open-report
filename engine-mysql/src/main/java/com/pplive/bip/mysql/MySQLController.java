package com.pplive.bip.mysql;


import com.alibaba.druid.util.JdbcConstants;
import com.pplive.bip.report.AlterColumn;
import com.pplive.bip.report.ResultColumn;
import com.pplive.bip.engine.EngineController;
import com.pplive.bip.util.SQLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;


@RestController
@RequestMapping(path="/")
public class MySQLController implements EngineController {

    private Logger LOG = LoggerFactory.getLogger(MySQLController.class);

    private static Map<String, String> sqlToMysqlTypesMap = new HashMap();

    //ref com.mysql.jdbc.MysqlDefs
    static {
        sqlToMysqlTypesMap.put("BOOLEAN", "BIT");
        sqlToMysqlTypesMap.put("TINYINT", "TINYINT");
        sqlToMysqlTypesMap.put("SMALLINT", "SMALLINT");
        sqlToMysqlTypesMap.put("INTEGER", "INT");
        sqlToMysqlTypesMap.put("BIGINT", "BIGINT");
        sqlToMysqlTypesMap.put("FLOAT", "FLOAT");
        sqlToMysqlTypesMap.put("REAL", "REAL");
        sqlToMysqlTypesMap.put("DOUBLE", "DOUBLE");
        sqlToMysqlTypesMap.put("CHAR", "CHAR");
        sqlToMysqlTypesMap.put("VARCHAR", "VARCHAR(255)");
    }

    @Autowired
    private MySQLService service;


    @RequestMapping("health")
    public String home(){
        return "welcome to mysql engine service";
    }

    /**
     * 根据sql生成结果列信息
     * @param ql
     * @return
     */
    @RequestMapping(value = "/schema")
    public List<ResultColumn> getResultSchema(@RequestParam String ql) throws SQLException{
        LOG.info("generate mysql schema for {}", ql);

        List<ResultColumn> resultColumns = new ArrayList<>();
        ResultSetMetaData metaData = service.getMetaData(ql);
        int c = metaData.getColumnCount();
        for(int i = 1; i <= c; ++i) {
            resultColumns.add(new ResultColumn(metaData.getColumnLabel(i), metaData.getColumnTypeName(i)));
        }
        return resultColumns;
    }

    /**
     * 获取sql查询的表
     * @param ql
     * @return
     */
    @RequestMapping(value = "/query_table")
    public Set<String> getQueryTable(@RequestParam String ql) {
        LOG.info("get query table for {}", ql);
        return SQLUtil.getQueryTables(ql, JdbcConstants.MYSQL);
    }

    public Map<String, String> getJdbc2EngineTypeMap() {
        return sqlToMysqlTypesMap;
    }

    /**
     * 创建存储报表结果表
     * @param table
     * @param columns
     * @return
     */
    @RequestMapping(path = "/create/{table}")
    public boolean createResultTable(@PathVariable String table, @RequestBody List<ResultColumn> columns) throws SQLException{
        LOG.info("create mysql table {} with columns {}", table, columns);
        String sql = getCreateTableSQL(table, columns);
        LOG.info("create mysql table {} sql: {}", table, sql);
        service.executeUpdate(sql);
        return true;
    }

    @RequestMapping(path = "/alter/{table}", method = RequestMethod.POST)
    @Override
    public boolean alterResultTable(@PathVariable("table") String table, @RequestBody AlterColumn alter) throws SQLException{
        List<ResultColumn> addColumns = alter.getAddColumns();
        List<ResultColumn> modifyColumns = alter.getModifyColumns();

        StringBuilder sb = new StringBuilder();
        addColumns.forEach(c->{
            sb.append(String.format(", add %s %s", c.getName(), getJdbc2EngineTypeMap().get(c.getDataType())));
        });

        modifyColumns.forEach(c->{
            sb.append(String.format(", modify %s %s", c.getName(), getJdbc2EngineTypeMap().get(c.getDataType())));
        });

        String sql = String.format("alter table %s %s", table, sb.substring(1));
        LOG.info("alter mysql table {} sql: {}", table, sql);
        service.executeUpdate(sql);
        return true;
    }

    /**
     * 根据ql获取报表结果接口
     */
    @RequestMapping(value = "/query")
    public List<Map<String, Object> > getResult(@RequestParam String ql) throws SQLException{
        LOG.info("mysql query {}", ql);
        return service.executeQuery(ql);
    }

}
