package com.pplive.bip.scheduler;

import com.pplive.bip.report.Frequency;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * Created by jiatingjin on 2018/1/3.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReportSchedulerTest extends BaseUnitTest{


    @Autowired
    ReportScheduler scheduler;

    /**
     * a->b->c
     */
    @Test
    public void testScheduleReport1() {
        bd.add("a");
        cd.add("b");
        reports.put("d", d);
       scheduler.scheduleReport(reports, 180103, Frequency.DAILY, dependencyReports);
    }

    /**
     * a->b, a->c
     */
    @Test
    public void testScheduleReport2() {
        bd.add("a");
        cd.add("a");
        scheduler.scheduleReport(reports, 180103, Frequency.DAILY, dependencyReports);
    }

    /**
     * a->c, b->c
     */
    @Test
    public void testScheduleReport3() {
        cd.add("a");
        cd.add("b");
        scheduler.scheduleReport(reports, 180103, Frequency.DAILY, dependencyReports);
    }

    @Ignore
    public void  testScheduleReport4() {
        scheduler.scheduleWeeklyReports();
    }


    @Ignore
    public void testIsLatestCalcComplete() {
        scheduler.isLatestCalcComplete(a, 18011800);
        scheduler.isLatestCalcComplete(a, 18011801);
        scheduler.isLatestCalcComplete(b, 180118);
    }

    @Ignore
    public void testIsCalcComplete() {
        scheduler.isCalcComplete(b, 180115, Frequency.WEEKLY);
        scheduler.isCalcComplete(b, 1802, Frequency.MONTHLY);
    }
}
