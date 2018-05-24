package com.pplove.bip.scheduler.oozie;

import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.BaseUnitTest;
import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.BaseUnitTest;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by jiatingjin on 2018/1/8.
 */
public class WorkflowBuilderTest extends BaseUnitTest {

//    @Autowired
    private WorkflowBuilder wb = new WorkflowBuilder();

    @Before
    public void init(){
        a.setFrequency(Frequency.DAILY);
        super.init();
    }

    /**
     * a->b->c
     */
    @Test
    public void testWorkflowBuild1() {
        bd.add("a");
        cd.add("b");
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = this.getSingleDag();
        wb.buildWorkflow(dag, 180108);
    }

    /**
     * a->b, a->c
     */
    @Test
    public void testWorkflowBuild2() {
        bd.add("a");
        cd.add("a");
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = this.getSingleDag();
        wb.buildWorkflow(dag, 180108);
    }

    /**
     * a->c, b->c
     */
    @Ignore
    public void testWorkflowBuild3() {
        cd.add("a");
        cd.add("b");
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = this.getSingleDag();
        wb.buildWorkflow(dag, 180108);
    }
}
