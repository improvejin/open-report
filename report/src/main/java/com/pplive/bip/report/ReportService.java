package com.pplive.bip.report;

import com.pplive.bip.report.domain.Report;
import com.pplive.bip.report.exception.ReportNotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ReportService implements Serializable{
    @Autowired
    private ReportRepository reportRepository;


    public Report addReport(Report report) {
        return reportRepository.save(report);
    }



//    @Caching (cacheable={@Cacheable("reportsById")}, put={@CachePut(value = "reportsByName", key = "#result.getName()")})
//    @CachePut(value = "reportsByName", key = "#result.getName()")
    @Cacheable("reportsById")
    public Report getReportById(Long id) {
        return reportRepository.findOne(id);
    }

//    @CachePut(value = "reportsById", key = "#result.getId()")
    @Cacheable("reportsByName")
    public Report getReportByName(String name) {
        return reportRepository.findByName(name);
    }

    public List<Report> getAllReport() {
        return reportRepository.findAll();
    }


    public List<Report> getReportsByFrequency(Frequency f) {
        return reportRepository.findByFrequency(f);
    }

    /**
     * 清空缓存
     */
    @CacheEvict(value={"reportsById", "reportsByName"},allEntries=true)// 清空 accountCache 缓存
    public void reloadCache() {
    }

    /**
     * 更新报表
     * @param report
     * @return
     */
    public Report updateReport(Report report) {

        Long id = report.getId();
        Report r = reportRepository.findOne(id);

        if(r == null) {
            throw new ReportNotFountException(id);
        }

        r.edit(report);

        return reportRepository.save(r);
    }

    /**
     * 删除报表
     * @param id
     */

    public void deleteReport(Long id) {
        reportRepository.delete(id);
    }


    /**
     * 找到被直接依赖的报表
     * @param report
     * @return
     */
    public List<Report> getDependedReports(Report report) {
        List<Report> reports = reportRepository.findByEngine(report.getStorePlace());
        List<Report> depended = new ArrayList<>();
        reports.forEach(r->{
            if (r.getQueryTables().contains(report.getResultTable())) {
                depended.add(r);
            }
        });

        return  depended;
    }
    /**
     * 根据存储位置获取报表, 目前无缓存
     * @param storageTables
     * @param engine
     * @return
     */
    public Set<String> getReportsByStorage(Set<String> storageTables, Engine engine) {
        Set<String> reports = new HashSet<>();
        storageTables.forEach(t->{
            Report r = reportRepository.findByResultTable(t, engine);
            if (r != null) {
                reports.add(r.getName());
            }
        });
        return reports;
    }
}
