package com.pplive.bip.report;

import com.pplive.bip.report.domain.Report;
import com.pplive.bip.report.exception.ReportNotFountException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
//导入静态方法使用import static
import static org.mockito.BDDMockito.*;

/**
 * mock及异常测试
 */
@SpringBootTest
@RunWith(SpringRunner.class) // 启动SprintContext装在Bean
public class MockReportServiceTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetReport() {

        when(reportRepository.findOne(1L)).thenReturn(null);
        this.thrown.expect(ReportNotFountException.class);
        this.thrown.expectMessage("Report with Id[1] does not exist");

        Report r1 = new Report();
        r1.setId(1L);
        r1.setName("jjt");
        r1.setDomain("sm");
        this.reportService.updateReport(r1);
    }

}
