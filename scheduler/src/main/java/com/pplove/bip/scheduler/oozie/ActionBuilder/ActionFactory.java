package com.pplove.bip.scheduler.oozie.ActionBuilder;

import com.pplove.bip.scheduler.oozie.WorkflowException;
import com.pplove.bip.report.ResultImporter;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.oozie.workflow.Action;
import com.pplove.bip.scheduler.oozie.workflow.Hive;
import com.pplove.bip.scheduler.oozie.workflow.Sqoop;
import com.pplove.bip.scheduler.oozie.workflow.WorkflowApp;
import com.pplove.bip.report.ResultImporter;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.oozie.WorkflowException;
import com.pplove.bip.scheduler.oozie.workflow.Action;
import com.pplove.bip.scheduler.oozie.workflow.Hive;
import com.pplove.bip.scheduler.oozie.workflow.Sqoop;
import com.pplove.bip.scheduler.oozie.workflow.WorkflowApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiatingjin on 2018/1/15.
 */
public class ActionFactory {

    private Map<SchedulableReport, Action> cache;

    private WorkflowApp app;

    private int date;

    public ActionFactory(WorkflowApp app, int date) {
        this.app = app;
        this.date = date;
        this.cache = new HashMap<>();
    }

    public Action getReportAction(SchedulableReport report) {
        if (cache.containsKey(report)) {
            return cache.get(report);
        }

        char type = ' ';
        Action a = new Action();
        try {
            // TODO: 2018/1/11  其他类型Action
            switch (report.getEngine()) {
                case HIVE:
                    HiveActionBuilder builder = new HiveActionBuilder(report, date, app);
                    Hive h =builder.build();
                    a.setHive(h);
                    type = 'h';
                    break;
                case MYSQL:
                    break;
                case JAVA:
                    break;
            }
        } catch (IOException e) {
            throw new WorkflowException(String .format("convert report[%s-%d] acton error", report.getName(), date) , e);
        }
        a.setName(String.format("%s-%s-%d", type, report.getName(), date));
        cache.put(report, a);
        return a;
    }

    // TODO: 2018/1/11 其他导入方式
    public Action getImporterAction(SchedulableReport report) {
        ResultImporter importer = report.getResultImporter(date);
        Action a = new Action();
        a.setName(importer.getName());
        SqoopActionBuilder builder = new SqoopActionBuilder(importer);
        Sqoop s =  builder.build();
        a.setSqoop(s);
        return a;
    }
}
