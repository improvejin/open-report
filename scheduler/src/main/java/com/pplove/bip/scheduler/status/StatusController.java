package com.pplove.bip.scheduler.status;

import com.pplove.bip.scheduler.oozie.Constants;
import com.pplove.bip.scheduler.oozie.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/status")
public class StatusController {

    private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

    private static enum ActionStatus {
        PREP,
        RUNNING,
        OK,
        ERROR,
        USER_RETRY,
        START_RETRY,
        START_MANUAL,
        DONE,
        END_RETRY,
        END_MANUAL,
        KILLED,
        FAILED, }





    private static final String RUNNING_FLAG = "S";

    private static final String SUCCESS_FLAG = "T";



    @Autowired
    private StatusService service;

    /**
     * 处理oozie action变更通知
     * @param jobId
     * @param nodeName
     * @param status
     */
    @RequestMapping(value = "/{jobId}/{nodeName}/{status}", method = RequestMethod.GET)
    public void oozieNotification(@PathVariable String jobId, @PathVariable String nodeName, @PathVariable String status) {

        LOG.trace("oozie notification jobId:{}, nodeName:{}, status:{}", jobId, nodeName, status);

        if (ignore(nodeName)) {
            return;
        }


        String[] reportsInfo = nodeName.split("-");
        String[] statusInfo = status.split(":");
        int last = reportsInfo.length - 1;
        assert last == 2 || last == 3;
        int date;
        String report;
        if(reportsInfo[last].equals(Constants.IMPORTER_SUFFIX)) {
            date = Integer.valueOf(reportsInfo[last-1]);
            report = reportsInfo[last-2];
            switch (statusInfo[0]) {
                case RUNNING_FLAG:
                    switch (ActionStatus.valueOf(statusInfo[1])) {
                        case RUNNING:
                            service.updateScheduleStatus(report, date, ScheduleStatus.IMPORTING, jobId);
                            break;
                        case KILLED:
                        case ERROR:
                        case FAILED:
                            service.updateScheduleStatus(report, date, ScheduleStatus.IMPORT_ERR, jobId);
                            break;
                        default:
                            LOG.debug("ignore oozie notification jobId:{}, nodeName:{}, status:{}", jobId, nodeName, status);
                    }
                    break;
                case SUCCESS_FLAG:
                    service.updateScheduleStatus(report, date, ScheduleStatus.SUCCESS, jobId);
                    break;
            }
        } else {
            date = Integer.valueOf(reportsInfo[last]);
            report = reportsInfo[last-1];
            switch (statusInfo[0]) {
                case RUNNING_FLAG:
                    switch (ActionStatus.valueOf(statusInfo[1])) {
                        case RUNNING:
                            service.updateScheduleStatus(report, date, ScheduleStatus.RUNNING, jobId);
                            break;
                        case KILLED:
                        case ERROR:
                        case FAILED:
                            service.updateScheduleStatus(report, date, ScheduleStatus.ERROR, jobId);
                            break;
                        default:
                            LOG.debug("ignore oozie notification jobId:{}, nodeName:{}, status:{}", jobId, nodeName, status);
                    }
                    break;
                case SUCCESS_FLAG:
                    service.updateScheduleStatus(report, date, ScheduleStatus.IMPORTING, jobId);
                    break;
            }
        }
    }

    private boolean ignore(String name) {
        return name.equals(Constants.START_FORK) || name.equals(Constants.END_JOIN) || name.equals(Constants.END)
                || name.equals(Constants.START) || name.startsWith(Constants.FORK_PREFIX) || name.startsWith(Constants.JOIN_PREFIX);
    }

    @RequestMapping(value = "/{report}/{date}")
    public ScheduleInfo getScheduleInfo(@PathVariable String report, @PathVariable int date) {
        ScheduleId id  = new ScheduleId(report, date);
        return service.getScheduleInfo(id);
    }


    @RequestMapping(value = "/all")
    public List<ScheduleInfo> getAllScheduleInfo() {
        return service.getAllScheduleInfo();
    }


}
