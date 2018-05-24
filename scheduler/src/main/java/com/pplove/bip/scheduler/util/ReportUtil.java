package com.pplove.bip.scheduler.util;

import com.pplove.bip.report.Engine;
import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.report.Engine;
import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.SchedulableReport;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class ReportUtil {

    private final static Logger LOG = LoggerFactory.getLogger(ReportUtil.class);


    private static Random random = new Random();

    /**
     * 构建报表连通图, reports与dependencyReports中报表频率不同
     * @param reports
     * @return
     */
    public static DirectedAcyclicGraph<SchedulableReport, DefaultEdge> buildReportsDAG(Map<String, SchedulableReport> reports,
                                                                                       Map<String, SchedulableReport> dependencyReports) {
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = new DirectedAcyclicGraph(DefaultEdge.class);

        //add vertex
        reports.forEach((name, report)->dag.addVertex(report));

        //add edge
        reports.forEach((name, report)->{
            report.getDependencies().forEach(dependency->{
                if (reports.containsKey(dependency)) {
                    dag.addEdge(reports.get(dependency), report);
                } else if (dependencyReports.containsKey(dependency)) {
                    SchedulableReport dp = dependencyReports.get(dependency);
                    dag.addVertex(dp);
                    dag.addEdge(dp, report);
                } else {
                    LOG.warn("Report[{}] with dependency[{}] is invalid.", name, dependency);
                }
            });
        });

        GraphUtil.removeRedundantEdge(dag);
        return dag;
    }




    /**
     * 获取图中的连通分量
     * @return
     */
    public static List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge> > getConnectedGraph(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> reportsDAG) {
        ConnectivityInspector<SchedulableReport, DefaultEdge> ci = new ConnectivityInspector(reportsDAG);
        List<Set<SchedulableReport> > connectedSets = ci.connectedSets();
        List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge> > dags = new ArrayList<>();

        connectedSets.forEach(connected->{
            DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag = new DirectedAcyclicGraph(DefaultEdge.class);
            //add vertex
            connected.forEach(report->dag.addVertex(report));
            //add edge
            connected.forEach(report -> {
                Set<DefaultEdge> outgoingEdges = reportsDAG.outgoingEdgesOf(report);
                outgoingEdges.forEach(edge -> dag.addEdge(report, reportsDAG.getEdgeTarget(edge)));
            });
            //添加虚拟开始节点，方便遍历
            //SchedulableReport start = addVirtualStartVertex(dag);
            dags.add(dag);
        });

        return dags;

    }

    /**
     * 添加虚拟开始结点
     * @param dag
     * @return
     */
    public static SchedulableReport addVirtualStartVertex(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag) {
        List<SchedulableReport> noDependencyReports = GraphUtil.zeroInDegree(dag);
        SchedulableReport virtual = new SchedulableReport(-1, "_virtual_start_node_" + random.nextInt(), null, Frequency.YEARLY, Engine.HIVE, Engine.MYSQL);
        dag.addVertex(virtual);
        noDependencyReports.forEach(r->dag.addEdge(virtual, r));
        return virtual;
    }
}
