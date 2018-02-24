package com.pplive.bip.hive;

import com.alibaba.druid.util.JdbcConstants;
import com.pplive.bip.engine.EngineController;
import com.pplive.bip.report.AlterColumn;
import com.pplive.bip.report.ResultColumn;
import com.pplive.bip.util.SQLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path="/")
public class HiveController implements EngineController {

    private static final Logger LOG = LoggerFactory.getLogger(HiveController.class);

    private static Map<String, String> sqlToHiveTypesMap = new HashMap();

    static {
        sqlToHiveTypesMap.put("BOOLEAN", "BOOLEAN");
        sqlToHiveTypesMap.put("TINYINT", "TINYINT");
        sqlToHiveTypesMap.put("SMALLINT", "SMALLINT");
        sqlToHiveTypesMap.put("INTEGER", "INT");
        sqlToHiveTypesMap.put("VARCHAR", "STRING");
    }

    @Autowired
    private HiveService service;


    @Override
    public Map<String, String> getJdbc2EngineTypeMap() {
        return sqlToHiveTypesMap;
    }

    /**
     * 不能像mysql一样通过HivePreparedStatement.getMetaData(),此方法在在HIVE JDBC中并未实现
     * @param ql
     * @return
     */
    @Override
    public List<ResultColumn> getResultSchema(@RequestParam String ql) throws SQLException{
        LOG.info("generate hive schema for {}", ql);
        return service.getSchema(ql);
    }

    @RequestMapping(value = "/query_table")
    @Override
    public Set<String> getQueryTable(@RequestParam String ql) {
        LOG.info("get query table for {}", ql);
        return SQLUtil.getQueryTables(ql, JdbcConstants.HIVE);
    }

    @RequestMapping(path = "/create/{table}")
    @Override
    public boolean createResultTable(@PathVariable("table") String table, @RequestBody List<ResultColumn> columns) throws SQLException {
        LOG.info("create hive table {} with columns {}", table, columns);
        String sql = getCreateTableSQL(table, columns);
        LOG.info("create hive table {} sql: {}", table, sql);
        service.executeUpdate(sql);
        return true;
    }

    @RequestMapping(path = "/alter/{table}", method = RequestMethod.POST)
    @Override
    public boolean alterResultTable(@PathVariable("table") String table, @RequestBody AlterColumn alter) throws SQLException {
        List<ResultColumn> addColumns = alter.getAddColumns();
        List<ResultColumn> modifyColumns = alter.getModifyColumns();

        StringBuilder sb = new StringBuilder();
        addColumns.forEach(c->{
            sb.append(String.format(", add %s %s", c.getName(), sqlToHiveTypesMap.get(c.getDataType())));
        });

        modifyColumns.forEach(c->{
            sb.append(String.format(", change %s %s", c.getName(), sqlToHiveTypesMap.get(c.getDataType())));
        });

        String sql = String.format("alter table %s %s", table, sb.substring(1));
        LOG.info("alter hive table {} sql: {}", table, sql);
        service.executeUpdate(sql);
        return true;
    }

    @Override
    public List<Map<String, Object>> getResult(@RequestParam String ql) throws SQLException {
        throw new SQLException("cannot get result data from hive");
    }
}
