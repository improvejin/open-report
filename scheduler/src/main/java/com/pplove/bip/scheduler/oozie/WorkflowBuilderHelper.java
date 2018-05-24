package com.pplove.bip.scheduler.oozie;


import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.scheduler.oozie.ActionBuilder.ActionFactory;
import com.pplove.bip.scheduler.oozie.workflow.*;
import com.pplove.bip.scheduler.util.GraphUtil;
import com.pplove.bip.report.SchedulableReport;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.*;

/**
 * Created by jiatingjin on 2018/1/8.
 */
public class WorkflowBuilderHelper {




    private DirectedAcyclicGraph<SchedulableReport, DefaultEdge> reportsDag;

    private DirectedAcyclicGraph<Object, DefaultEdge> wfDag;

    private ActionFactory factory;

    private  Map<Action, Object> actions;

    private WorkflowApp app;

    private int date;

    public WorkflowBuilderHelper(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> reportsDag,
                                 WorkflowApp app, int date) {
        this.reportsDag = reportsDag;
        wfDag = new DirectedAcyclicGraph(DefaultEdge.class);
        factory = new ActionFactory(app, date);
        actions = new HashMap<>();
        this.app = app;
        this.date = date;
    }


    public void addActionForkJoin() {
        List<SchedulableReport> starters = GraphUtil.zeroInDegree(reportsDag);
        starters.forEach(report -> {
            addForkJoin(report);
        });
    }


    private void addForkJoin(SchedulableReport r) {
        List<SchedulableReport> children = GraphUtil.outgoingTargets(r, reportsDag);
        int size = children.size();
        Action a = factory.getReportAction(r);
        wfDag.addVertex(a);
        if (size == 0) {
            actions.put(a, a);
        } else if (size == 1) {
            SchedulableReport child = children.get(0);
            addForkJoin(child);
            Action childAction = factory.getReportAction(child); //应该缓存
            //wfDag.addVertex(childAction);
            wfDag.addEdge(a, childAction);
            actions.put(a, actions.get(childAction));
        } else {
            children.forEach(c->{
                addForkJoin(c);
            });
            //fork/join成对出现
            List<Action> childrenActions = new ArrayList<>();
            children.forEach(c->{
                Action childAction = factory.getReportAction(c); //应该缓存
                childrenActions.add(childAction);
            });

            //add fork
            Fork fork = addFork(a);
            wfDag.addVertex(fork);
            wfDag.addEdge(a, fork);
            childrenActions.forEach(c->{
                wfDag.addEdge(fork, c);
            });

            //add join
            Join join = addJoin(a);
            wfDag.addVertex(join);
            childrenActions.forEach(c->{
                wfDag.addEdge(actions.get(c), join);
            });
            actions.put(a, join);
        }
    }

    private Fork addFork(Action parent) {
        Fork fork = new Fork();
        fork.setName(Constants.FORK_PREFIX + parent.getName());
        return fork;
    }

    private Join addJoin(Action parent) {
        Join join  = new Join();
        join.setName(Constants.JOIN_PREFIX + parent.getName());
        return join;
    }


    public void addImporter() {
        Set<DefaultEdge> toRemove = new HashSet<>();
        reportsDag.forEach(report -> {
            Action importerAction = factory.getImporterAction(report);
            Action reportAction = factory.getReportAction(report);
            Set<DefaultEdge> edges =  wfDag.outgoingEdgesOf(reportAction);
            toRemove.addAll(edges);
            wfDag.addVertex(importerAction);
            edges.forEach(e->wfDag.addEdge(importerAction, wfDag.getEdgeTarget(e)));
            wfDag.addEdge(reportAction, importerAction);

        });
        toRemove.forEach(e->wfDag.removeEdge(e));
    }

    public void build() {
        String endName = endName();
        wfDag.vertexSet().forEach(v->{
            List<Object> children = GraphUtil.outgoingTargets(v, wfDag);
            int size = children.size();
            if(v instanceof Action) {
                assert size == 0 || size == 1;

                //add transition
                if (size == 1) {
                    Action a = (Action)v;
                    ActionTransition okTransit = new ActionTransition();
                    okTransit.setTo(nameOf(children.get(0)));
                    a.setOk(okTransit);
                    //add  transition for  action
                    if (actions.containsKey(a)) {
                        Action importer = (Action)children.get(0);
                        ActionTransition errTransit = new ActionTransition();
                        //a.setError(errTransit);
                        Object v1 = actions.get(a);



                        if (v1 instanceof Join) {
                            //currentJoin = (Join) v1;
                            Object child = GraphUtil.childOf(v1 ,wfDag);
                            if (child != null) {
                                errTransit.setTo(((Join)child).getName());
                            } else {
                                errTransit.setTo(endName);
                            }
                        } else {
                            Object importers = GraphUtil.childOf(v1 ,wfDag);
                            Object join = GraphUtil.childOf(importers, wfDag);
                            if(join != null){
                                errTransit.setTo(((Join)join).getName());
                            } else {
                                errTransit.setTo(endName);
                                importer.setOk(errTransit);
                            }
                        }
                        a.setError(errTransit);
                        importer.setError(errTransit);
                    }
                }
            } else if(v instanceof Fork) {
                assert size > 1;
                Fork fork = (Fork)v;
                children.forEach(c->{
                    ForkTransition ft = new ForkTransition();
                    ft.setStart(nameOf(c));
                    fork.getPath().add(ft);
                });
            } else if(v instanceof Join) {
                assert size == 0 || size == 1;
                Join join = (Join)v;
                if (size == 1) {
                    join.setTo(nameOf(children.get(0)));
                } else {
                    join.setTo(endName);
                }
            }
        });

        addStart();

        addEnd();

        Queue<Object> queue =  GraphUtil.BreadthFirstTraverse(wfDag);
        app.getDecisionOrForkOrJoin().addAll(queue);

    }

    private String nameOf(Object v) {
        if(v instanceof Action) {
            return ((Action) v).getName();
        }else if (v instanceof Fork){
            return ((Fork) v).getName();
        } else if (v instanceof Join) {
            return ((Join) v).getName();
        } else {
            throw new RuntimeException("workflow build error");
        }
    }

    private String endName() {
       if(GraphUtil.zeroOutDegree(wfDag).size() > 1) {
           return Constants.END_JOIN;
       } else {
           return Constants.END;
       }
    }

    private void addStart() {
        Start s = new Start();
        app.setStart(s);

        List<Object> starters = GraphUtil.zeroInDegree(wfDag);
        if (starters.size() == 1) {
            Action start = (Action)starters.get(0);
            s.setTo(start.getName());
        } else {
            Fork startFork = new Fork();
            startFork.setName(Constants.START_FORK);
            wfDag.addVertex(startFork);
            starters.forEach(v->{
                Action a = (Action)v;
                ForkTransition ft = new ForkTransition();
                ft.setStart(a.getName());
                startFork.getPath().add(ft);
                wfDag.addEdge(startFork, a);
            });
            s.setTo(startFork.getName());
        }
    }

    private void addEnd() {
        End e  = new End();
        e.setName(Constants.END);
        app.setEnd(e);

        List<Object> enders = GraphUtil.zeroOutDegree(wfDag);
        if (enders.size() > 1) {
            Join join = new Join();
            join.setName(Constants.END_JOIN);
            join.setTo(Constants.END);
            wfDag.addVertex(join);
            enders.forEach(v->{
                wfDag.addEdge(v, join);
            });
        }

//        String endName = endName();
//        enders.forEach(v->{
//            addEndTransition(v, endName);
//        });
    }

    private void addEndTransition(Object v, String endName) {
        if (v instanceof Join) {
            ((Join)v).setTo(endName);
        } else if (v instanceof Action){
            Action a = (Action)v;
            ActionTransition okTransit = new ActionTransition();
            okTransit.setTo(endName);
            a.setOk(okTransit);
            ActionTransition errTransit = new ActionTransition();
            errTransit.setTo(endName);
            a.setError(errTransit);
        } else {
            throw new RuntimeException("workflow build error");
        }
    }
}
