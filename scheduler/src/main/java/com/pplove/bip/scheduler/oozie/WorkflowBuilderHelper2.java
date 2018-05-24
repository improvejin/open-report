package com.pplove.bip.scheduler.oozie;

import com.pplove.bip.scheduler.util.GraphUtil;
import com.pplove.bip.scheduler.oozie.workflow.*;
import com.pplove.bip.scheduler.oozie.workflow.Fork;
import com.pplove.bip.scheduler.oozie.workflow.Join;
import com.pplove.bip.scheduler.util.GraphUtil;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.*;

/**
 * Created by jiatingjin on 2018/1/8.
 * 无用类
 */
@Deprecated
public class WorkflowBuilderHelper2 {

    private static final String START_FORK = "start_fork";
    private static final String END_JOIN = "end_join";
    private static final String END = "end";

    public void addStart(WorkflowApp wf) {
        Start s = new Start();
        wf.setStart(s);
    }

    public void addEnd(WorkflowApp wf) {
        End e  = new End();
        e.setName(END);
        wf.setEnd(e);
    }

    public void addKill(WorkflowApp wf) {
        Kill k = new Kill();
        k.setName("kill");
        k.setMessage("[${wf:errorMessage(wf:lastErrorNode())}]");
        wf.getDecisionOrForkOrJoin().add(k);
    }

    //oozie中fork/join须成对出现，此处有bug
    public void addForkJoin(WorkflowApp wf, DirectedAcyclicGraph<Object, DefaultEdge> wfDag){

        Map<Action, Fork> action2fork = new HashMap();
        Map<Action, Join> action2Join = new HashMap();
        wfDag.vertexSet().forEach(a -> {
            // add fork
            Action action = (Action) a;
            Fork fork = addFork(action, wfDag);
            if (fork != null) {
                action2fork.put(action, fork);
            }
            // add join
            Join join = addJoin(action, wfDag);
            if (join != null) {
                action2Join.put(action, join);
            }
            ActionTransition transit = new ActionTransition();
            transit.setTo(END);
            action.setError(transit);
        });

        Set<DefaultEdge> toRemove = new HashSet<>();
        //add action edge
        Set<DefaultEdge> edges = new HashSet<>();
        edges.addAll(wfDag.edgeSet());
        edges.forEach(e->{
            Action source = (Action)wfDag.getEdgeSource(e);
            Action target = (Action)wfDag.getEdgeTarget(e);
            Fork f = null;
            Join j = null;
            if (action2fork.containsKey(source)) {
                f = action2fork.get(source);
            }
            if (action2Join.containsKey(target)) {
                j = action2Join.get(target);
            }

            if (f != null) {
                wfDag.addVertex(f);
                if(j != null) {
                    wfDag.addVertex(j);
                    wfDag.addEdge(f, j);
                } else {
                    wfDag.addEdge(f, target);
                }
                toRemove.add(e);
            } else {
                if (j != null) {
                    wfDag.addVertex(j);
                    wfDag.addEdge(source, j);
                    toRemove.add(e);
                }
            }
        });
        toRemove.forEach(e->wfDag.removeEdge(e));

        //add fork edge
        action2fork.forEach((k,v)->{
            wfDag.addEdge(k, v);
        });

        //add join edge
        action2Join.forEach((k,v)->{
            wfDag.addEdge(v, k);
        });

        //add start fork
        List<Object> zeroInDegree = GraphUtil.zeroInDegree(wfDag);
        int s = zeroInDegree.size();
        assert s >= 1;
        if (s == 1) {
            Action a = (Action)zeroInDegree.get(0);
            wf.getStart().setTo(a.getName());
        } else {
            Fork startFork = new Fork();
            startFork.setName(START_FORK);
            zeroInDegree.forEach(v->{
                Action a = (Action)v;
                ForkTransition ft = new ForkTransition();
                ft.setStart(a.getName());
                startFork.getPath().add(ft);
            });

            wfDag.addVertex(startFork);
            //add start fork edge
            zeroInDegree.forEach(a -> wfDag.addEdge(startFork, a));
            wf.getStart().setTo(startFork.getName());
        }


        List<Object> zeroOutDegree = GraphUtil.zeroOutDegree(wfDag);
        int ot = zeroOutDegree.size();
        assert ot >= 1;
        if (ot == 1) {
            Action a = (Action)zeroOutDegree.get(0);
            ActionTransition transit = new ActionTransition();
            transit.setTo(wf.getEnd().getName());
            a.setOk(transit);
        } else {
            //add end join
            Join endJoin = new Join();
            endJoin.setName(END_JOIN);
            endJoin.setTo(wf.getEnd().getName());
            wfDag.addVertex(endJoin);
            zeroOutDegree.forEach(a -> {
                wfDag.addEdge(a, endJoin);
            });
        }

        Queue<Object> queue =  GraphUtil.BreadthFirstTraverse(wfDag);
        wf.getDecisionOrForkOrJoin().addAll(queue);
    }

    private Fork addFork(Action action, DirectedAcyclicGraph<Object, DefaultEdge> wfDag) {
        List<Object> outgoingTargets = GraphUtil.outgoingTargets(action, wfDag);
        int s = outgoingTargets.size();
        if (s == 0) {
            //无fork
            ActionTransition transit = new ActionTransition();
            transit.setTo(END);
            action.setOk(transit);
            //add edge
            return null;
        } else if (s == 1) {
            //无fork
            Action t = (Action)outgoingTargets.get(0);
            ActionTransition transit = new ActionTransition();
            transit.setTo(actionJoinName(t, wfDag));
            action.setOk(transit);
            return null;
        } else {
            //有fork
            final Fork fork = new Fork();
            fork.setName("fork_" + action.getName());
            outgoingTargets.forEach(t-> {
                ForkTransition ft = new ForkTransition();
                ft.setStart(actionJoinName((Action)t, wfDag));
                fork.getPath().add(ft);
            });
            ActionTransition transit = new ActionTransition();
            transit.setTo(fork.getName());
            action.setOk(transit);
            return fork;
        }
    }

    private String actionJoinName(Action a, DirectedAcyclicGraph<Object, DefaultEdge> wfDag) {
        Set<DefaultEdge> incomingEdges = wfDag.incomingEdgesOf(a);
        int s = incomingEdges.size();
        assert s > 0;
        if (s == 1) {
            return a.getName();
        } else {
            return "join_" + a.getName();
        }
    }

    private Join addJoin(Action a, DirectedAcyclicGraph<Object, DefaultEdge> wfDag) {
        Set<DefaultEdge> incomingEdges = wfDag.incomingEdgesOf(a);
        Join join = null;
        if (incomingEdges.size() > 1) {
            join  = new Join();
            join.setName(actionJoinName(a, wfDag));
            join.setTo(a.getName());
        }
        return join;
    }

    //oozie中fork/join须成对出现
    public void addForkJoin2(WorkflowApp wf, DirectedAcyclicGraph<Object, DefaultEdge> wfDag){

        List<Object> forests = GraphUtil.zeroInDegree(wfDag);
        forests.forEach(f->{

        });

        //return null;
    }


}
