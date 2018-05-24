package com.pplove.bip.scheduler;

import com.pplove.bip.report.Engine;
import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.util.ReportUtil;
import com.pplove.bip.report.Engine;
import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.util.ReportUtil;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Assert;
import org.junit.Before;

import java.util.*;

/**
 * Created by jiatingjin on 2018/1/3.
 */
public class BaseUnitTest {
    protected Map<String, SchedulableReport> reports = new HashMap<>();
    protected Map<String, SchedulableReport> dependencyReports = new HashMap<>();

    private String sql = "select dt,ipvalue from sm_play where dt=180108 limit 5";

    protected SchedulableReport a = new SchedulableReport(1, "a", sql, Frequency.HOURLY, Engine.HIVE, Engine.SQLSERVER);

    protected SchedulableReport b = new SchedulableReport(2, "b", sql, Frequency.DAILY, Engine.HIVE, Engine.SQLSERVER);
    protected SchedulableReport c = new SchedulableReport(3, "c", sql, Frequency.DAILY, Engine.HIVE, Engine.SQLSERVER);
    protected SchedulableReport d = new SchedulableReport(4, "d", sql,Frequency.DAILY, Engine.HIVE, Engine.SQLSERVER);
    protected SchedulableReport e = new SchedulableReport(5, "e", sql,Frequency.DAILY, Engine.HIVE, Engine.SQLSERVER);

    protected Set<String> ad = new HashSet();
    protected Set<String> bd = new HashSet();
    protected Set<String> cd = new HashSet();
    protected Set<String> dd = new HashSet();
    protected Set<String> ed = new HashSet();

    @Before
    public void init(){
        a.setDependencies(ad);
        b.setDependencies(bd);
        c.setDependencies(cd);
        d.setDependencies(dd);
        e.setDependencies(ed);
        dependencyReports.put("a", a);
        reports.put("b", b);
        reports.put("c", c);
    }

    protected DirectedAcyclicGraph<SchedulableReport, DefaultEdge> getSingleDag() {
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag1 = ReportUtil.buildReportsDAG(reports, dependencyReports);
        List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge>> dag2 = ReportUtil.getConnectedGraph(dag1);
        Assert.assertEquals(1, dag2.size());
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag3 = dag2.get(0);
        return dag3;
    }

}
