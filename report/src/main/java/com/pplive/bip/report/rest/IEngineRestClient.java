package com.pplive.bip.report.rest;

import com.pplive.bip.report.AlterColumn;
import com.pplive.bip.report.ResultColumn;
import com.pplive.bip.response.RestResponse;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IEngineRestClient {

    /**
     * 根据sql生成结果列信息
     * @param ql
     * @return
     */
    @RequestMapping(value = "/schema")
    List<ResultColumn> getResultSchema(@RequestParam("ql") String ql) throws SQLException;

    /**
     * @RequestParam必须带上参数
     * @param ql
     * @return
     */
    @RequestMapping(value = "/query_table", consumes = "application/json")
    Set<String> getQueryTable(@RequestParam("ql") String ql);

    /**
     * 创建存储报表结果表
     * @param table
     * @param resultColumns
     * @return
     */
    @RequestMapping(path = "/create/{table}")
    boolean createResultTable(@PathVariable("table") String table, @RequestBody List<ResultColumn> resultColumns) throws SQLException;


    @RequestMapping(path = "/alter/{table}", method = RequestMethod.POST)
    boolean alterResultTable(@PathVariable("table") String table, @RequestBody AlterColumn alter) throws SQLException;
    /**
     * 获取报表结果接口
     */
    @RequestMapping(value = "/query")
    List<Map<String, Object>> getResult(@RequestParam("ql") String ql) throws SQLException;
}
