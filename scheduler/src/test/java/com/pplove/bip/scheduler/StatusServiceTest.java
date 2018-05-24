package com.pplove.bip.scheduler;

import com.pplove.bip.scheduler.status.ScheduleStatus;
import com.pplove.bip.scheduler.status.StatusService;

import com.pplove.bip.scheduler.status.ScheduleStatus;
import com.pplove.bip.scheduler.status.StatusService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatusServiceTest {

    @Autowired
    private StatusService service;

    @Test
    public void testUpdateStatus() {
        service.updateScheduleStatus("a", 180118, ScheduleStatus.RUNNING, "joba");
    }

    @Ignore
    public void testGeScheduleStatus() {
        Assert.assertEquals(ScheduleStatus.RUNNING, service.getScheduleStatus("a", 180118));
    }

}
