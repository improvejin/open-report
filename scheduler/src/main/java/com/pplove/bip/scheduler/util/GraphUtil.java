package com.pplove.bip.scheduler.util;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.*;

/**
 * Created by jiatingjin on 2018/1/5.
 */
public class GraphUtil {

    /**
     * dag是联通的
     * @param dag
     * @return
     */
    public static <T> Queue<T> BreadthFirstTraverse(DirectedAcyclicGraph<T, DefaultEdge> dag) {
        Queue<T> resultQ = new LinkedList<>();
        Deque<T> tmpQ = new LinkedList<>();
        Set<T> reports = dag.vertexSet();
        reports.forEach(r->{
            if(dag.inDegreeOf(r) == 0) {
                tmpQ.offer(r);
            }
        });

        while(tmpQ.size() > 0) {
            T r = tmpQ.poll();
            Set<DefaultEdge> outgoingEdges = dag.outgoingEdgesOf(r);
            outgoingEdges.forEach(e-> {
                T t= dag.getEdgeTarget(e);
                if (tmpQ.contains(t)) {
                    tmpQ.remove(t);
                }
                tmpQ.offer(t);
            });

            if(canVisitAgain(r, tmpQ, dag) == false) {
                resultQ.offer(r);
            }
        }
        return resultQ;
    }

    private static <T> boolean canVisitAgain(T v, Queue<T> q, DirectedAcyclicGraph<T, DefaultEdge> dag) {
        Set<T> descendants = new HashSet<>();
        q.forEach(e->descendants.addAll(dag.getDescendants(e)));
        if (descendants.contains(v)) {
            return  true;
        }
        return false;
    }

    public static <T> List<T> zeroInDegree(DirectedAcyclicGraph<T, DefaultEdge> dag) {
        List<T> zeroIn = new ArrayList<>();
        Set<T> reports = dag.vertexSet();
        reports.forEach(r->{
            if(dag.inDegreeOf(r) == 0) {
                zeroIn.add(r);
            }
        });
        return zeroIn;
    }

    public static <T> List<T> zeroOutDegree(DirectedAcyclicGraph<T, DefaultEdge> dag) {
        List<T> zeroOut = new ArrayList<>();
        Set<T> reports = dag.vertexSet();
        reports.forEach(r->{
            if(dag.outDegreeOf(r) == 0) {
                zeroOut.add(r);
            }
        });
        return zeroOut;
    }

    public static <T> List<T> outgoingTargets(T v, DirectedAcyclicGraph<T, DefaultEdge> dag) {
        Set<DefaultEdge> outgoingEdges = dag.outgoingEdgesOf(v);
        List<T> targets = new ArrayList<>();
        outgoingEdges.forEach(e-> targets.add(dag.getEdgeTarget(e)));
        return targets;
    }

    public static <T> List<T> incomingSources(T v, DirectedAcyclicGraph<T, DefaultEdge> dag) {
        Set<DefaultEdge> incomingEdges = dag.incomingEdgesOf(v);
        List<T> sources = new ArrayList<>();
        incomingEdges.forEach(e-> sources.add(dag.getEdgeSource(e)));
        return sources;
    }

    public static <T> T childOf(T v, DirectedAcyclicGraph<T, DefaultEdge> dag) {
        Set<DefaultEdge> outgoingEdges = dag.outgoingEdgesOf(v);
        assert outgoingEdges.size() <= 1;

        Iterator<DefaultEdge> it = outgoingEdges.iterator();

        if (it.hasNext()) {
            return dag.getEdgeTarget(it.next());
        }

        return null;
    }


    /**
     * 删除dag中多余的依赖，比如a->b->c, a->c, 此时a->c 多余
     * @param dag
     */
    public static <T> void removeRedundantEdge(DirectedAcyclicGraph<T, DefaultEdge> dag) {
        Set<DefaultEdge> toRemove = new HashSet<>();
        dag.vertexSet().forEach(v->{
            List<T> sources = incomingSources(v, dag);
            sources.forEach(s->{
                Set<T> ancesstors = dag.getAncestors(s);
                ancesstors.retainAll(sources);
                ancesstors.forEach(a->toRemove.add(dag.getEdge(a, v)));
            });
        });
        toRemove.forEach(e->dag.removeEdge(e));
    }
}
