package com.pplive.bip.scheduler.oozie;

import com.pplive.bip.report.Frequency;
import com.pplive.bip.report.SchedulableReport;
import com.pplive.bip.scheduler.BaseUnitTest;
import com.pplive.bip.scheduler.util.ReportUtil;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.Test;

public class WorkflowSubmiterTest extends BaseUnitTest {

    //@Autowired
    private WorkflowSubmitter submitter = new WorkflowSubmitter();

    @Before
    public void init(){
        a.setFrequency(Frequency.DAILY);
        super.init();
        submitter.init();
    }

    /**
     * a->b->c
     */
    @Test
    public void testWorkflowBuild1() throws Exception{
        bd.add("a");
        cd.add("b");
        //reports.put("d", d);
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag =  ReportUtil.buildReportsDAG(reports, dependencyReports);
        submitter.submit(dag, 180108);
    }

    /**
     * a->b, a->c, b->d,
     */
    @Test
    public void testWorkflowBuild2() throws Exception{
        bd.add("a");
        cd.add("a");
        reports.put("d", d);
        reports.put("e", e);
        dd.add("b");
        ed.add("b");
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag =  ReportUtil.buildReportsDAG(reports, dependencyReports);
        submitter.submit(dag, 180108);
    }

}
