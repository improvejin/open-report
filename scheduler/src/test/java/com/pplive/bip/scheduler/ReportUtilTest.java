package com.pplive.bip.scheduler;

import com.pplive.bip.report.SchedulableReport;
import com.pplive.bip.scheduler.util.GraphUtil;
import com.pplive.bip.scheduler.util.ReportUtil;
import org.junit.Assert;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Test;

import java.util.*;

public class ReportUtilTest extends BaseUnitTest{

    /**
     * a->b->c
     */
    @Test
    public void testBuildReportsDAG1() {
        bd.add("a");
        cd.add("b");
        reports.put("d", d);

        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag1 = ReportUtil.buildReportsDAG(reports, dependencyReports);
        Assert.assertTrue(dag1.containsEdge(a, b));
        Assert.assertTrue(dag1.containsEdge(b, c));
        Assert.assertFalse(dag1.containsEdge(a, c));
        Assert.assertFalse(dag1.containsEdge(c, b));
        List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge>> dag2 = ReportUtil.getConnectedGraph(dag1);
        Assert.assertEquals(2, dag2.size());
    }

    /**
     * a->b, a->c
     */
    @Test
    public void testBuildReportsDAG2() {
        bd.add("a");
        cd.add("a");

        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag1 = ReportUtil.buildReportsDAG(reports, dependencyReports);
        List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge>> dag2 = ReportUtil.getConnectedGraph(dag1);
        Assert.assertEquals(1, dag2.size());
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag3 = dag2.get(0);
//        Assert.assertEquals(dag1, dag3); why?
        Assert.assertTrue(dag3.containsEdge(a, b));
        Assert.assertTrue(dag3.containsEdge(a, c));
        Assert.assertFalse(dag3.containsEdge(b, c));
        Assert.assertFalse(dag3.containsEdge(c, a));


    }

    /**
     * a->c, b->c
     */
    @Test
    public void testBuildReportsDAG3() {
        cd.add("a");
        cd.add("b");

        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag1 = ReportUtil.buildReportsDAG(reports, dependencyReports);
        List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge>> dag2 = ReportUtil.getConnectedGraph(dag1);
        Assert.assertEquals(1, dag2.size());
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag3 = dag2.get(0);
        Assert.assertTrue(dag3.containsEdge(a, c));
        Assert.assertTrue(dag3.containsEdge(b, c));
        Assert.assertFalse(dag3.containsEdge(a, b));
        Assert.assertFalse(dag3.containsEdge(b, a));
    }

    /**
     * a->c, b->c, c->d
     */
    @Test
    public void testBreadthFirstTraverse1() {
        cd.add("a");
        cd.add("b");
        dd.add("c");
        reports.put("d", d);
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag1 = ReportUtil.buildReportsDAG(reports, dependencyReports);
        Queue<SchedulableReport> q = GraphUtil.BreadthFirstTraverse(dag1);
        String s = queueSeq(q);
        Assert.assertTrue("abcd".equals(s) || "bacd".equals(s));
    }

    /**
     * a->b, b->c, a->c
     */
    @Test
    public void testBreadthFirstTraverse2() {
        bd.add("a");
        cd.add("b");
        cd.add("a");
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = ReportUtil.buildReportsDAG(reports, dependencyReports);
        Queue<SchedulableReport> q = GraphUtil.BreadthFirstTraverse(dag);
        String s = queueSeq(q);
        Assert.assertEquals("abc", s);
    }

    /**
     * a->d, b->d, c->d, a->b
     */
    @Test
    public void testBreadthFirstTraverse3() {
        bd.add("a");
        dd.add("a");
        dd.add("b");
        dd.add("c");
        reports.put("d", d);
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = ReportUtil.buildReportsDAG(reports, dependencyReports);
        Queue<SchedulableReport> q = GraphUtil.BreadthFirstTraverse(dag);
        String s = queueSeq(q);
        Assert.assertTrue("abcd".equals(s) || "acbd".equals(s) || "cabd".equals(s));
    }


    private String queueSeq(Queue<SchedulableReport> q) {
        StringBuilder sb = new StringBuilder();
        while (q.size() > 0) {
            sb.append(q.poll().getName());
        }

        return sb.toString();
    }
}
