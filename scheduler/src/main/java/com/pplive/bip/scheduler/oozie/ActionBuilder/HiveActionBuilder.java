package com.pplive.bip.scheduler.oozie.ActionBuilder;

import com.pplive.bip.report.SchedulableReport;
import com.pplive.bip.scheduler.oozie.workflow.*;
import com.pplive.bip.scheduler.oozie.workflow.Configuration;
import com.pplive.bip.scheduler.util.HdfsUtil;
import com.pplive.bip.util.PathUtil;
import com.pplive.bip.util.SystemConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by jiatingjin on 2018/1/9.
 */
public class HiveActionBuilder{

    protected String jobTracker="${jobTracker}";
    protected String nameNode="${nameNode}";

    private SchedulableReport report;
    private int date;
    private String wf;

    public HiveActionBuilder(SchedulableReport report, int date, WorkflowApp wf) {
        this.report = report;
        this.date = date;
        this.wf = wf.getName();
    }

    public HiveActionBuilder jobTracker(String jobTracker) {
        this.jobTracker = jobTracker;
        return this;
    }

    public HiveActionBuilder nameNode(String nameNode) {
        this.nameNode = nameNode;
        return this;
    }

    private String  generateScript() throws IOException {
        String fileName = String.format("h-%s-%d.hql", report.getName(), date);
        String appDir = SystemConfig.getWorkflowAppDir(wf);
        String hqlDir =  PathUtil.combine(appDir, "hql");
        String scriptFile = PathUtil.combine(hqlDir, fileName);
        String script = report.getExecuteScript(date);
        HdfsUtil.save(script, scriptFile);
        return scriptFile;
    }




    public Hive build() throws IOException{
        Hive action = new Hive();
        action.setJobTracker(jobTracker);
        action.setNameNode(nameNode);

        String scriptPath = this.generateScript();
        action.setScript(scriptPath);

        String dir = report.getHdfsResultDir();
        String out = report.getHdfsResultDir(date);
        Delete d = new Delete();
        d.setPath(out);
        Mkdir mkdir = new Mkdir();
        mkdir.setPath(dir);
        Prepare p = new Prepare();
        p.getDelete().add(d);
        p.getMkdir().add(mkdir);
        action.setPrepare(p);

        action.getParam().add(String.format("OUTPUT=%s", out));

        Map<String, String> reportConf = report.getConfiguration();
        if (!reportConf.isEmpty()) {
            Configuration conf = new Configuration();
            List<Configuration.Property> props =  conf.getProperty();
            reportConf.forEach((k,v)->{
                Configuration.Property prop = new Configuration.Property();
                prop.setName(k);
                prop.setValue(v);
                props.add(prop);
            });
            action.setConfiguration(conf);
        }
        return action;
    }
}
