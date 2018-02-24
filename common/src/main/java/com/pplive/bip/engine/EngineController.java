package com.pplive.bip.engine;

import com.pplive.bip.report.AlterColumn;
import com.pplive.bip.report.ResultColumn;
import com.pplive.bip.response.RestResponse;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface EngineController {

    /**
     * 根据sql生成结果列信息
     * @param ql
     * @return
     */
    @RequestMapping(value = "/schema")
    List<ResultColumn> getResultSchema(@RequestParam String ql) throws SQLException;


    /**
     * 获取sql查询的表
     * @param ql
     * @return
     */
    @RequestMapping(value = "/query_table")
    Set<String> getQueryTable(@RequestParam String ql);

    /**
     * 创建存储报表结果表
     * @param table
     * @param resultColumns
     * @return
     */
    @RequestMapping(path = "/create/{table}")
    boolean createResultTable(@PathVariable("table") String table, @RequestBody List<ResultColumn> resultColumns) throws SQLException;

    /**
     * 更改表
     * @param table
     * @param alter
     * @return
     */
    @RequestMapping(path = "/alter/{table}", method = RequestMethod.POST)
    boolean alterResultTable(@PathVariable("table") String table, @RequestBody AlterColumn alter) throws SQLException;

    /**
     * 获取报表结果接口
     */
    @RequestMapping(value = "/query")
    List<Map<String, Object>> getResult(@RequestParam String ql) throws SQLException;


    default String getCreateTableSQL(String table, List<ResultColumn> columns) {
        StringBuilder sb = new StringBuilder();
        columns.forEach(c->{
            sb.append(String.format(", %s %s", c.getName(), getJdbc2EngineTypeMap().get(c.getDataType())));
        });

        String sql = String.format("create table %s (%s)", table, sb.substring(1));
        return sql;
    }


    Map<String, String> getJdbc2EngineTypeMap();
}
