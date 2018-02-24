package com.pplive.bip.scheduler;

import com.pplive.bip.exception.EngineNotSupportException;
import com.pplive.bip.report.Frequency;
import com.pplive.bip.report.SchedulableReport;
import com.pplive.bip.scheduler.oozie.WorkflowSubmitter;
import com.pplive.bip.scheduler.status.ScheduleStatus;
import com.pplive.bip.scheduler.status.StatusService;
import com.pplive.bip.scheduler.util.GraphUtil;
import com.pplive.bip.scheduler.util.ReportUtil;
import com.pplive.bip.util.DateUtil;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static com.pplive.bip.util.DateUtil.*;
import java.util.*;

@Component
public class ReportScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(ReportScheduler.class);

    @Autowired
    private ReportRestClient client;

    @Autowired
    private StatusService statusService;

    private static final Map<String, SchedulableReport> empty = new HashMap<>();

    /**
     * 调度最近两天的小时报表，以及需要重新计算的小时报表
     */
    @Value("${schedule.report.hour.before:48}")
    private int hourBefore;
    @Scheduled(fixedRate = 1000*60*10)
    public void scheduleHourlyReports(){
        LOG.info("start to schedule hourly report");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusHours(hourBefore);
        Map<String, SchedulableReport> hourlyReports = client.getHourlyReports();
        while (start.isBefore(now)) {
            Integer date = DateUtil.formatDateTime(start, Frequency.HOURLY);
            LOG.info("start to schedule hourly report with date {}", date);
            scheduleReport(hourlyReports, date, Frequency.HOURLY, empty);
            start = start.plusHours(1);
        }
    }

    @Value("${schedule.report.day.before:2}")
    private int dayBefore;
    @Scheduled(fixedRate = 1000*60*20)
    public void scheduleDailyReports(){
        LOG.info("start to schedule daily report");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(dayBefore);
        Map<String, SchedulableReport> hourlyReports = client.getHourlyReports();
        Map<String, SchedulableReport> dailyReports = client.getDailyReports();
        while (start.isBefore(now)) {
            //可计算的星期一
            Integer date = DateUtil.formatDateTime(start, Frequency.DAILY);
            LOG.info("start to schedule daily report with date {}", date);
            scheduleReport(dailyReports, date, Frequency.DAILY, hourlyReports);
            start = start.plusDays(1);
        }
    }

    @Value("${schedule.report.week.before:1}")
    private int weekBefore;
    @Scheduled(fixedRate = 1000*60*60*2)
    public void scheduleWeeklyReports(){
        LOG.info("start to schedule weekly report");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.minusDays(now.getDayOfWeek().getValue()-1);
        LocalDateTime start = end.minusWeeks(weekBefore);
        Map<String, SchedulableReport> weeklyReports = client.getWeeklyReports();
        Map<String, SchedulableReport> dailyReports = client.getDailyReports();
        while (start.isBefore(end)) {
            //date为星期一
            Integer date = DateUtil.formatDateTime(start, Frequency.DAILY);
            LOG.info("start to schedule weekly report with date {}", date);
            scheduleReport(weeklyReports, date, Frequency.WEEKLY, dailyReports);
            start = start.plusWeeks(1);
        }
    }

    @Value("${schedule.report.month.before:1}")
    private int monthBefore;
    @Scheduled(fixedRate = 1000*60*60*3)
    public void scheduleMonthlyReports(){
        LOG.info("start to schedule monthly report");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMonths(monthBefore);
        Map<String, SchedulableReport> monthlyReports = client.getMonthlyReports();
        Map<String, SchedulableReport> dailyReports = client.getDailyReports();
        while (start.isBefore(now)) {
            Integer date = DateUtil.formatDateTime(start, Frequency.MONTHLY);
            LOG.info("start to schedule monthly report with date {}", date);
            scheduleReport(monthlyReports, date, Frequency.MONTHLY, dailyReports);
            start = start.plusMonths(1);
        }
    }

    @Value("${schedule.report.year.before:1}")
    private int yearBefore;
    @Scheduled(fixedRate = 1000*60*60*6)
    public void scheduleYearlyReports(){
        LOG.info("start to schedule yearly report");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusYears(yearBefore);
        Map<String, SchedulableReport> yearlyReports = client.getYearlyReports();
        Map<String, SchedulableReport> monthlyReports = client.getMonthlyReports();
        while (start.isBefore(now)) {
            Integer date = DateUtil.formatDateTime(start, Frequency.YEARLY);
            LOG.info("start to schedule yearly report with date {}", date);
            scheduleReport(yearlyReports, date, Frequency.YEARLY, monthlyReports);
            start = start.plusYears(1);
        }
    }

    /**
     * 重跑报表
     */
//    @Scheduled(fixedRate = 5000)
    public void scheduleRerunReports(){
        LOG.info("start to schedule rerun reports");
        //todo 从DB获取需重跑报表的日期进行调度
    }

    /**
     * 调度报表，形成DAG, 添加导入节点并提交
     * @param reports 调度提交的报表
     * @param date 调度日期
     * @param frequency 调度报表频率
     * @param dependencyReports 比frequency小的频率的报表，reports可能会依赖dependencyReports，比如天报表依赖小时报表
     */
    public void scheduleReport(Map<String, SchedulableReport> reports, Integer date, Frequency frequency,
                               Map<String, SchedulableReport> dependencyReports){
        assert dependencyReports != null;

        if (reports.isEmpty()) {
            LOG.info("There is no {} reports.", frequency);
            return;
        }

        LOG.info("start to schedule {} reports with date {}", frequency, date);
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> reportsDAG = ReportUtil.buildReportsDAG(reports, dependencyReports);
        LOG.debug("{} reports DAG: {}", frequency, reportsDAG);
        List<DirectedAcyclicGraph<SchedulableReport, DefaultEdge>> connectedDAGs = ReportUtil.getConnectedGraph(reportsDAG);
        connectedDAGs.forEach(dag->{
            LOG.debug("connected {} reports DAG: {}", frequency, dag);
            Queue<SchedulableReport> toSubmit = getRunnableReports(dag, date, frequency);
            //形成提交DAG
            DirectedAcyclicGraph<SchedulableReport, DefaultEdge> toSubmitDAG = this.getSubmitDAG(toSubmit, dag);
            LOG.debug("to submit {} reports DAG: {}", frequency, toSubmitDAG);
            submit(toSubmitDAG, date);
        });
        LOG.info("end to schedule {} reports with date {}", frequency, date);
    }

    /**
     * 创建提交dag
     * @param toSubmit
     * @param reportsDag
     * @return
     */
    private DirectedAcyclicGraph<SchedulableReport, DefaultEdge> getSubmitDAG(Queue<SchedulableReport> toSubmit,
                                                DirectedAcyclicGraph<SchedulableReport, DefaultEdge> reportsDag) {
        DirectedAcyclicGraph<SchedulableReport, DefaultEdge> toSubmitDAG = new DirectedAcyclicGraph(DefaultEdge.class);
        toSubmit.forEach(r -> toSubmitDAG.addVertex(r));
        while (toSubmit.size() > 0) {
            SchedulableReport report = toSubmit.poll(); //也可用从后向前
            List<SchedulableReport> targets = GraphUtil.outgoingTargets(report, reportsDag);
            targets.retainAll(toSubmit);
            targets.forEach(t->toSubmitDAG.addEdge(report, t));
        }

        return toSubmitDAG;
    }

    /**
     * 获取可运行报表
     * @param dag
     * @param date
     * @param frequency
     * @return
     */
    private Queue<SchedulableReport> getRunnableReports(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag,
                                                      int date, Frequency frequency) {
        Queue<SchedulableReport> runnable = new LinkedList<>();
        Queue<SchedulableReport> q = GraphUtil.BreadthFirstTraverse(dag);
        while (q.size()>0) {
            SchedulableReport report = q.poll();
            if (allowSubmit(report, date, frequency, runnable, dag)) {
                runnable.add(report);
                if (tooMuch(runnable)) {
                    break;
                }
            }
        }
        return runnable;
    }



    /**
     * 判断报表是否可提交
     * @param report
     * @param date
     * @return
     */
    public boolean  allowSubmit(SchedulableReport report, int date, Frequency frequency, Queue<SchedulableReport> toSubmit,
                                 DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag) {
        //频率不同
        if (isSameFrequency(report, frequency) == false) {
            LOG.debug("the frequency of report[{}] is not {}.", report.getName(), frequency);
            return  false;
        }

        //自依赖，检查前一次是否计算完成
        if (report.selfDependency()) {
            if (isLatestCalcComplete(report, date) == false) {
                LOG.debug("the self dependency report[{}] before schedule date {} is not calc complete.", report.getName(), date);
                return  false;
            }
        }

        //已计算成功或已提交正在运行中
        if(isCalcComplete(report, date) || isCalcRunning(report,date)) {
            LOG.debug("report[{}] on schedule date {} is calc complete or running.", report.getName(), date);
            return false;
        }

        //依赖的fact/dimension table数据没有ready
        if (isDomainComplete(report, date) == false) {
            LOG.debug("the fact or dimension data for report[{}] on schedule date {} is not ready.", report.getName(), date);
            return  false;
        }

        List<SchedulableReport> sources = GraphUtil.incomingSources(report, dag);
        int size = sources.size();
        if (size > 1) {
            //防止一个workflow中有一个报表依赖多个报表，增加了oozie添加fork/join复杂读
            //将图变成了森林
            for(SchedulableReport s :sources) {
                if(isCalcComplete(s, date, frequency) == false) {
                    LOG.debug("the dependency[{}] of report[{}] with more than two dependencies on schedule date {} is not calc complete.", s.getName(), report.getName(), date);
                    return false;
                }
            }
        } else if(size == 1) {
            SchedulableReport s = sources.get(0);
            //依赖的报表没有计算完成，也没有同当前报表一起提交
            if (toSubmit.contains(s) == false && isCalcComplete(s, date, frequency) == false) {
                LOG.debug("the dependency[{}] of report[{}] on schedule date {} is not calc complete.", s.getName(), report.getName(), date);
                return  false;
            }
        }

        return true;
    }

    /**
     * 判断report的frequency是否与调度周期相同
     * @param report
     * @param frequency
     * @return
     */
    public boolean  isSameFrequency(SchedulableReport report, Frequency frequency) {
        return report.getFrequency() == frequency;
    }

    /**
     * 判断date前一次是否计算完成
     * @param report
     * @param date
     * @return
     */
    public boolean  isLatestCalcComplete(SchedulableReport report, int date) {
        Frequency f = report.getFrequency();
        assert  f == Frequency.DAILY || f == Frequency.HOURLY;
        if (f == Frequency.DAILY) {
            //天报表判断前一天状态
            LocalDate dt = LocalDate.parse(String.valueOf(date), DAILY_FORMATTER);
            int yesterday = Integer.valueOf(dt.minusDays(1).format(DAILY_FORMATTER));
            return isCalcComplete(report, yesterday);
        } else if(f == Frequency.HOURLY) {
            //小时报表0点默认计算完成，其他小时判断前一小时, 一般用于计算当天累计到当前小时
            if (date % 100 == 0) {
                return true;
            } else {
                LocalDateTime dateTime = LocalDateTime.parse(String.valueOf(date), HOURLY_FORMATTER);
                int last = Integer.valueOf(dateTime.minusHours(1).format(HOURLY_FORMATTER));
                return isCalcComplete(report, last);
            }
        }

        return  true;
    }

    /**
     * 判断报表是否计算完成, 从DB中获取状态判断
     * @param dependency
     * @param date
     * @return
     */
    public boolean  isCalcComplete(SchedulableReport dependency, int date, Frequency frequency) {
        if(isSameFrequency(dependency, frequency)) {
            //直接判断报表date时间是否计算完成
            return isCalcComplete(dependency, date);
        }

        Frequency f = dependency.getFrequency();
        switch (frequency) {
            case HOURLY:
                assert f == Frequency.HOURLY;
                break;
            case DAILY:
                //天报表依赖小时报表，判断这一天24小时是否都已计算完成
                assert f == Frequency.HOURLY;
                int startHour = date*100;
                for(int i = 0; i < 24; ++i) {
                    if (isCalcComplete(dependency, startHour+i) == false) {
                        return false;
                    }
                }
                break;
            case WEEKLY:
                //周报表依赖天报表，判断这一周每一天否都已计算完成
                assert f == Frequency.DAILY;
                LocalDate dt = LocalDate.parse(String.valueOf(date), DAILY_FORMATTER);
                for(int i = 0; i < 7; ++i) {
                    int day = Integer.valueOf(dt.plusDays(i).format(DAILY_FORMATTER));
                    if (isCalcComplete(dependency, day) == false) {
                        return false;
                    }
                }
                break;
            case MONTHLY:
                //月报表依赖天报表，判断这一月每一天否都已计算完成
                assert f == Frequency.DAILY;
                LocalDate m = LocalDate.parse(String.valueOf(date*100+1), DAILY_FORMATTER);
                LocalDate nextMonth =  m.plusMonths(1);
                while (m.isBefore(nextMonth)){
                    int day = Integer.valueOf(m.format(DAILY_FORMATTER));
                    if (isCalcComplete(dependency, day) == false) {
                        return false;
                    }
                    m = m.plusDays(1);
                }
                break;
            case YEARLY:
                //年报表依赖月报表，判断这一年每月否都已计算完成
                assert f == Frequency.MONTHLY;
                int startMonth = date*100;
                for(int i = 1; i < 13; ++i) {
                    if (isCalcComplete(dependency, startMonth+i) == false) {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 判断report在date时间是否计算完成
     * @param report
     * @param date
     * @return
     */
    public boolean  isCalcComplete(SchedulableReport report, int date) {
        ScheduleStatus status = statusService.getScheduleStatus(report.getName(), date);
        return status == ScheduleStatus.SUCCESS;
    }

    /**
     * 已提交计算中, 从DB中查询状态
     * @param report
     * @param date
     * @return
     */
    public boolean  isCalcRunning(SchedulableReport report, int date) {
        ScheduleStatus status = statusService.getScheduleStatus(report.getName(), date);
        return status == ScheduleStatus.SUBMITTED
                || status == ScheduleStatus.RUNNING || status == ScheduleStatus.IMPORTING;
    }


    @Value("${schedule.report.domain.ignore:false}")
    private boolean ignoreDomain;
    /**
     * 依赖的fact/dimension表数据是否ready
     * @param report
     * @param date
     * @return
     */
    public boolean  isDomainComplete(SchedulableReport report, int date) {
        if(ignoreDomain) {
            return true;
        }
        // TODO: 2018/1/18  
        return false;
    }


    @Value("${schedule.report.num.max:6}")
    private int maxScheduleReportsOneTime;
    /**
     * 对提交的报表个数进行限制，防止依赖过深，DAG过大
     * @param toSubmit
     * @return
     */
    public boolean tooMuch(Queue<SchedulableReport> toSubmit) {
        return toSubmit.size() > maxScheduleReportsOneTime;
    }

    @Value("${schedule.report.engine:oozie}")
    private String engine;
    public void submit(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) {
        LOG.info("engine:{}, date:{}, DAG: {}", engine, dag, date);
        if (engine.equalsIgnoreCase("oozie")) {
            submitToOozie(dag, date);
        } else if( engine.equalsIgnoreCase("azkaban")) {
            submitToAzkaban(dag, date);
        } else if (engine.equalsIgnoreCase("airflow")) {
            submitToAirflow(dag, date);
        }
    }

    @Autowired
    private WorkflowSubmitter oozieSubmitter;
    public void submitToOozie(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) {
        try {
            String jobId = oozieSubmitter.submit(dag, date);
            dag.vertexSet().forEach(s->{
                statusService.updateScheduleStatus(s.getName(), date, ScheduleStatus.SUBMITTED, jobId);
            });
        } catch (Exception e) {
            LOG.error("submit oozie workflow error",e);
        }
    }

    public void submitToAzkaban(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) {
        throw new EngineNotSupportException("azkaban");
    }

    public void submitToAirflow(DirectedAcyclicGraph<SchedulableReport, DefaultEdge> dag, int date) {
        throw new EngineNotSupportException("airflow");
    }

}
