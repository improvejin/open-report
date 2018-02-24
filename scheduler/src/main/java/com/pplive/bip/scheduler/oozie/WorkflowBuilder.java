package com.pplive.bip.scheduler.oozie;

import com.pplive.bip.report.SchedulableReport;
import com.pplive.bip.scheduler.oozie.workflow.*;
import com.pplive.bip.util.SerializeUtil;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiatingjin on 2018/1/4.
 */
@Component
public class WorkflowBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(WorkflowBuilder.class);

    private static WorkflowBuilderHelper2 helper = new WorkflowBuilderHelper2();

    private static class ActionInfo{
        Action reportAction;
        Action importerAction;

        public ActionInfo(Action reportAction, Action importerAction) {
            this.reportAction = reportAction;
            this.importerAction = importerAction;
        }

        public Action getReportAction() {
            return reportAction;
        }

        public Action getImporterAction() {
            return importerAction;
        }
    }


    public WorkflowApp buildWorkflow2(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) {

//        WorkflowApp wf = new WorkflowApp();
//        wf.setName("test");
//
//        DirectedAcyclicGraph<Object, DefaultEdge> wfDag = new DirectedAcyclicGraph(DefaultEdge.class);
//        Map<SchedulableReport, ActionInfo> report2Action = new HashMap<>();
//        //add importer action
//        dag.forEach(r->{
//            Action reportAction = convertReportToAction(wf, r, date);
//            Action importerAction = convertImporterToAction(wf, r.getResultImporter(date));
//            wfDag.addVertex(reportAction);
//            wfDag.addVertex(importerAction);
//            wfDag.addEdge(reportAction, importerAction);
//            report2Action.put(r, new ActionInfo(reportAction, importerAction));
//        });
//
//        //add edge for importer to target
//        dag.edgeSet().forEach(edge -> {
//            SchedulableReport source = dag.getEdgeSource(edge);
//            SchedulableReport target = dag.getEdgeTarget(edge);
//            wfDag.addEdge(report2Action.get(source).getImporterAction(), report2Action.get(target).getReportAction());
//        });
//
//
//
//        helper.addStart(wf);
//        helper.addEnd(wf);
//        LOG.debug("before add fork/join DAG: {}", wfDag);
//        helper.addForkJoin(wf, wfDag);
//        LOG.debug("after add fork/join DAG: {}", wfDag);
//        helper.addKill(wf);
//
//        String xml = SerializeUtil.xmlSerialize(wf);
//        LOG.debug("submit workflow DAG: {}", xml);
//
//        return wf;
        return null;

    }


    public WorkflowApp buildWorkflow(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) {
        WorkflowApp app = new WorkflowApp();
        app.setName("test");

        WorkflowBuilderHelper help = new WorkflowBuilderHelper(dag, app, date);
        help.addActionForkJoin();
        help.addImporter();
        help.build();
        String xml = SerializeUtil.xmlSerialize(app);
        LOG.debug("submit workflow DAG: {}", xml);

        return app;
    }
}
