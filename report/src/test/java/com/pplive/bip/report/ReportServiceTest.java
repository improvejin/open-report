package com.pplive.bip.report;

import com.pplive.bip.report.domain.Report;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class) // 启动SprintContext装在Bean
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void testGetReport() {
        Report r1 = reportService.getReportById(1L);
        Report r2 = reportService.getReportByName(r1.getName());
        Assert.assertEquals(r2.getId(), 1L);
    }
}
