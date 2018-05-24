package com.pplove.bip.scheduler.oozie;

import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.oozie.workflow.WorkflowApp;
import com.pplove.bip.scheduler.util.HdfsUtil;
import com.pplove.bip.util.PathUtil;
import com.pplove.bip.util.SerializeUtil;
import com.pplove.bip.util.SystemConfig;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.util.PathUtil;
import com.pplove.bip.util.SerializeUtil;
import com.pplove.bip.util.SystemConfig;
import org.apache.hadoop.fs.Path;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by jiatingjin on 2018/1/9.
 */
@Component
public class WorkflowSubmitter {

    private static final Logger LOG = LoggerFactory.getLogger(WorkflowSubmitter.class);

    public Path save(WorkflowApp workflow) throws IOException {
        String appDir = SystemConfig.getWorkflowAppDir(workflow.getName());
        String wfFile = PathUtil.combine(appDir, "workflow.xml");
        String xml = SerializeUtil.xmlSerialize(workflow);
        return HdfsUtil.save(xml, wfFile);
    }

    private WorkflowBuilder builder;

    private OozieClient wc;

    @PostConstruct
    public void init(){
        String oozieUrl = SystemConfig.getProperty("oozie.server.url");
        wc  = new OozieClient(oozieUrl);
        builder = new WorkflowBuilder();
    }

    public String submit(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) throws OozieClientException, IOException {
        WorkflowApp app  = builder.buildWorkflow(dag, date);
        Properties conf = wc.createConfiguration();
        Path p = save(app);
        String s = p.toString();
        wc.validateXML(s);
        conf.setProperty(OozieClient.APP_PATH, s);
        ClassPathResource resource = new ClassPathResource("/job.properties");
        conf.load(resource.getInputStream());
        String jobId = wc.run(conf);
        LOG.info("submit workflow {} with path {}", jobId, s);
        return jobId;
    }
}
