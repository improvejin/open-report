package com.pplive.bip.report;

import com.pplive.bip.authorization.SysUser;
import com.pplive.bip.report.exception.ReportRemoveException;
import com.pplive.bip.report.exception.SQLErrorException;
import com.pplive.bip.report.rest.IEngineRestClient;
import com.pplive.bip.report.rest.RestClientManager;
import com.pplive.bip.report.domain.ReportStatus;
import com.pplive.bip.report.exception.AuditException;
import com.pplive.bip.response.RestResponse;
import com.pplive.bip.report.domain.Report;
import com.pplive.bip.report.exception.ReportNotFountException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path="/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping("")
    @ResponseBody
    String home() {
        SysUser u = SysUser.getCurrentUser();
        return "Hello " + u.getUsername() + ", Welcome to BIP!";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResponse<Report> addNewReport(@RequestBody Report r) {
        Report ret = reportService.addReport(r);
        RestResponse<Report> rsp = new RestResponse<>(ret);
        return rsp;
    }

    @RequestMapping(value = "/list")
    public RestResponse<List<Report> > getReportsDetail() {
        List<Report> reports = reportService.getAllReport();
        RestResponse<List<Report> > rsp = new RestResponse<>(reports);
        return rsp;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, authentication.name)")
    @GetMapping(path="/id/{id}")
    @ResponseBody
    public RestResponse<Report> getReportById(@PathVariable Long id) {
        Report ret = reportService.getReportById(id);
        if(ret == null) {
            throw new ReportNotFountException(id);
        }
        RestResponse<Report> rsp = new RestResponse<>(ret);
        return rsp;
    }

    @GetMapping(path="/name/{name}")
    @ResponseBody
    public RestResponse<Report> getReportByName(@PathVariable String name) {
        Report ret = reportService.getReportByName(name);
        if(ret == null) {
            throw new ReportNotFountException(name);
        }
        RestResponse<Report> rsp = new RestResponse<>(ret);
        return rsp;
    }

    @GetMapping(path="/frequency/{f}")
    @ResponseBody
    public Map<String, SchedulableReport> getReportsByFrequery(@PathVariable String f){
        Map<String, SchedulableReport> m = new HashMap<>();
        List<Report> reports =reportService.getReportsByFrequency(Frequency.valueOf(f.toUpperCase()));
        reports.forEach(r->{
            m.put(r.getName(), convertToSchedulable(r));
        });
        return  m;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public RestResponse deleteReportById(@PathVariable Long id) {
        Report r = reportService.getReportById(id);
        if (r == null) {
            throw new ReportNotFountException(id);
        }

        List<Report> depended = reportService.getDependedReports(r);
        if (depended.size() > 0) {
            throw new ReportRemoveException(r.getName(), depended.toString());
        }

        reportService.deleteReport(id);
        return RestResponse.NullRestResponse;
    }

    /**
     * update时更新属性，
     * @param r
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Report updateReport(@RequestBody Report r) {
        Report ret =  reportService.updateReport(r);
        return ret;
    }

    @RequestMapping(value = "/reload")
    public RestResponse reloadReport() {
        reportService.reloadCache();
        return RestResponse.NullRestResponse;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;




    @RequestMapping("/rest")
    public Set<String>  restTest() {
        IEngineRestClient client = RestClientManager.getRestClient(Engine.MYSQL);
        return client.getQueryTable("abc");
    }


    @RequestMapping(value = "/schema", method = RequestMethod.GET)
    public RestResponse getSchema(@RequestBody Report r) {
        IEngineRestClient client = RestClientManager.getRestClient(r.getEngine());
        List<ResultColumn> columns = null;
        try {
            columns = client.getResultSchema(getExeSql(r));
        } catch (SQLException e) {
            throw new SQLErrorException(e.getMessage());
        }
        RestResponse<List<ResultColumn>> rsp = new RestResponse<>(columns);
        return rsp;
    }

    /**
     * 获取被依赖的报表
     * @param name
     * @return
     */
    @RequestMapping("/depended/{name}")
    public List<Report> getDependedReports(@PathVariable String name) {
        Report r = reportService.getReportByName(name);
        if (r == null) {
            throw new ReportNotFountException(name);
        }
        return reportService.getDependedReports(r);
    }

    /**
     * 获取依赖的报表
     * @param name
     * @return
     */
    @RequestMapping("/depend/{name}")
    public Set<String> getDependReports(@PathVariable String name) {
        Report r = reportService.getReportByName(name);
        if (r == null) {
            throw new ReportNotFountException(name);
        }
        return reportService.getReportsByStorage(r.getQueryTables(), r.getEngine());
    }


    /**
     * 审核报表
     * @param r
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Report auditReport(@RequestBody Report r) {
//        ResponseEntity<RestResponse> entity = restTemplate.getForEntity(service, RestResponse.class);
//        RestResponse rsp = entity.getBody();
        // TODO: 2018/1/23  需要检测一些必需的参数，比如dt
        Report persistence = reportService.getReportById(r.getId());
        assert persistence != null;

        //视图报表
        if (persistence.isView()) {
            assert r.isView() == true;
            return reportService.updateReport(r);
        }

        IEngineRestClient client = RestClientManager.getRestClient(r.getEngine());

        if (StringUtils.isEmpty(r.getResultTable())) {
            r.setResultTable(r.generateResultTable());
        }
        try {
            //存储位置变了或表名变了都重建
            if (persistence.isSchemaCreated() == false || r.getStorePlace().equals(persistence.getStorePlace()) == false
                    || r.getResultTable().equalsIgnoreCase(persistence.getResultTable()) == false) {
                client.createResultTable(r.getResultTable(), r.getResultSchema());
            } else {
                assert persistence.isSchemaCreated() == true;
                AlterColumn alterColumn = getAlterColumns(persistence.getResultSchema(), r.getResultSchema());
                client.alterResultTable(r.getResultTable(), alterColumn);
            }
        } catch (SQLException e) {
            throw new SQLErrorException(e.getMessage());
        }

        r.setSchemaCreated(true);

        Set<String> queryTables = client.getQueryTable(getExeSql(r));
        r.setQueryTables(queryTables);

        Set<String> dependencies = reportService.getReportsByStorage(queryTables, r.getEngine());
        r.setDependencies(dependencies);

        r.setStatus(ReportStatus.RUNNING);

        Report ret =  reportService.updateReport(r);
        return ret;
    }

    //不能删除列，添加列只能添加到末尾，更改列
    private AlterColumn getAlterColumns(List<ResultColumn> oldSchema, List<ResultColumn> newSchema) {
        if (oldSchema.size() > newSchema.size()) {
            throw new AuditException(String.format("cannot delete column, new schema size %d is smaller than old %d", newSchema.size(), oldSchema.size()));
        }

        ResultColumn[] oldSchemaA = new ResultColumn[oldSchema.size()];
        oldSchema.toArray(oldSchemaA);
        ResultColumn[] newSchemaA = new ResultColumn[newSchema.size()];
        newSchema.toArray(newSchemaA);

        List<ResultColumn> modifyColumns = new ArrayList<>();
        List<ResultColumn> addColumns = new ArrayList<>();
        int i = 0;
        for(; i< oldSchemaA.length; ++i) {
            if (oldSchemaA[i].name.equals(newSchemaA[i].name) == false) {
                throw new AuditException(String.format("column %s id deleted or order changed", oldSchemaA[i].name));
            }

            if (oldSchemaA[i].dataType.equals(newSchemaA[i].dataType)) {
                modifyColumns.add(newSchemaA[i]);
            }
        }

        for (; i < newSchemaA.length; ++ i) {
            addColumns.add(newSchemaA[i]);
        }

        return AlterColumn.of(modifyColumns, addColumns);
    }


    /**
     * 将report中的特殊参数@dt等去掉
     * @param report
     * @return
     */
    private String getExeSql(Report report) {
        SchedulableReport s = convertToSchedulable(report);
        return s.getExecuteScript(LocalDateTime.now());
    }

    private static SchedulableReport convertToSchedulable(Report r) {
        SchedulableReport schedulable = new SchedulableReport(r.getId(), r.getName(), r.getQl(), r.getFrequency(), r.getEngine(), r.getStorePlace());
        return schedulable;
    }

    @RequestMapping(value = "/result/{name}/{startDate}/{endDate}/{callback}")
    public RestResponse getReportResult(@PathVariable String name, @PathVariable Integer startDate,
                                        @PathVariable Integer endDate, @PathVariable(required = false) String callback){
        Report r = reportService.getReportByName(name);
        if (r == null) {
            throw new ReportNotFountException(name);
        }

        String query;
        if (r.isView()) {
            query = String.format("select * from (%s)tmp where dt >= %s and dt < %s", r.getQl(), startDate, endDate);
        } else {
            List<ResultColumn> columns = r.getResultSchema();
            StringBuilder sb = new StringBuilder();
            columns.forEach(c -> sb.append(String.format(",%s ", c.getName())));
            query = String.format("select %s from where dt >= %s and dt < %s", sb.substring(1), startDate, endDate);
        }

        IEngineRestClient client = RestClientManager.getRestClient(r.getStorePlace());
        List<Map<String, Object>> result;
        try {
            result = client.getResult(query);
        } catch (SQLException e) {
            throw new SQLErrorException(e.getMessage());
        }

        RestResponse< List<Map<String, Object>> > rsp = new RestResponse<>(result);
        return rsp;
    }
}
