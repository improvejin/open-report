package com.pplove.bip.report;

/**
 * Created by jiatingjin on 2017/12/20.
 */

import com.pplove.bip.report.domain.Report;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r from Report r where r.name = ?1")
    Report findByName(String name);

    @Modifying
    @Query("update Report r set status = 'Delete' where id = ?1")
    void delete(Long id);

    @Caching(put={
            @CachePut(value = "reportsByName", key = "#result.getName()"),
            @CachePut(value = "reportsById", key = "#result.getId()")
    })
    Report save(Report r);


    @Query("select r from Report r where r.frequency = ?1 and r.status = 'Running' and r.view = FALSE ")
    List<Report> findByFrequency(Frequency f);

    @Query("select r from Report r where r.engine = ?1 and r.status = 'Running' and r.view = 0")
    List<Report> findByEngine(Engine e);

    @Query("select r from Report r where r.resultTable = ?1 and r.storePlace = ?2 and r.status = 'Running' and r.view = 0")
    Report findByResultTable(String table, Engine e);

}
