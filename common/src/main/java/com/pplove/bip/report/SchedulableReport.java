package com.pplove.bip.report;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pplove.bip.util.DateUtil;
import com.pplove.bip.util.PathUtil;
import com.pplove.bip.util.SystemConfig;
import com.pplove.bip.util.DateUtil;
import com.pplove.bip.util.PathUtil;
import com.pplove.bip.util.SystemConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SchedulableReport {
    private long id;
    private String name;
    private String sql;
    private String resultTable;
    private Engine storePlace;
    private Engine engine;

    private Frequency frequency;

    //依赖报表
    private Set<String> dependencies;

    //依赖表
    private Set<String> queryTables;

    private Map<String, String> conf = new HashMap<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<String> dependencies) {
        this.dependencies = dependencies;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public String getResultTable() {
        return resultTable;
    }

    public void setResultTable(String resultTable) {
        this.resultTable = resultTable;
    }

    public Set<String> getQueryTables() {
        return queryTables;
    }

    public void setQueryTables(Set<String> queryTables) {
        this.queryTables = queryTables;
    }

    public Map<String, String> getConf() {
        return conf;
    }

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }

    public Engine getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(Engine storePlace) {
        this.storePlace = storePlace;
    }

    public boolean selfDependency() {
        return  dependencies.contains(name);
    }

    public ResultImporter getResultImporter(int date) {
        ResultImporter importer = new ResultImporter(this, date);
        return importer;
    }


    /**
     * 每个报表的特殊配置
     * @return
     */
    @JsonIgnore
    public Map<String, String> getConfiguration() {
        return conf;
    }

    /**
     * 获取可执行的sql
     * @param date
     * @return
     */
    public String getExecuteScript(int date) {
        return String.format("insert overwrite directory '%s' %s", getHdfsResultDir(date), getSqlScript(date));
    }

    private String getSqlScript(int date) {
        String hdt, dt, hour, startMonth, endMonth;
        String sqlWithDate = sql;
        switch (this.frequency) {
            case HOURLY:
                hdt = String.valueOf(date);
                dt = String.valueOf(date / 100);
                hour = String.valueOf(date % 100);
                sqlWithDate = sql.replaceAll("@hdt", hdt).replaceAll("@dt", dt).replaceAll("@hour", hour);
                break;
            case DAILY:
                dt = String.valueOf(date);
                sqlWithDate = sql.replaceAll("@dt", dt);
                break;
            case WEEKLY:
                String startWeek = String.valueOf(date);
                String endWeek = String.valueOf(DateUtil.addDays(date, 6));
                sqlWithDate = sql.replaceAll("@startWeek", startWeek).replaceAll("@endWeek", endWeek);
                break;
            case MONTHLY:
                startMonth = String.valueOf(DateUtil.startDayOfMonth(date));
                endMonth =  String.valueOf(DateUtil.endDayOfMonth(date));
                String month = String.valueOf(date);
                sqlWithDate = sql.replaceAll("@month", month).replaceAll("@startMonth", startMonth).replaceAll("@endMonth", endMonth);
                break;
            case YEARLY:
                String startDay = String.valueOf(DateUtil.startDayOfYear(date));
                String endDay = String.valueOf(DateUtil.endDayOfYear(date));
                startMonth = String.valueOf(date*100+1);
                endMonth = String.valueOf(date*100+12);
                String year = String.valueOf(date);
                sqlWithDate = sql.replaceAll("@year", year).replaceAll("@startMonth", startMonth).replaceAll("@endMonth", endMonth);
                sqlWithDate = sqlWithDate.replaceAll("@startDay", startDay).replaceAll("@endDay", endDay);
                break;
            default:
                break;
        }

        return sqlWithDate;
    }

    public String getExecuteScript(LocalDateTime dateTime) {
        int dt = DateUtil.formatDateTime(dateTime, this.frequency);
        return getSqlScript(dt);
    }

    @JsonIgnore
    public String getHdfsResultDir(){
        return PathUtil.combine(SystemConfig.getReportResultDir(), name);
    }


    public String getHdfsResultDir(int date) {
        String dir = PathUtil.combine(SystemConfig.getReportResultDir(), String.format("%s/%d/", name, date));
        return dir;
    }

    // TODO: 2018/1/10
    public SchedulableReport(long id, String name, String sql, Frequency f, Engine e, Engine s) {
        this.id = id;
        this.name = name;
        this.sql = sql;
        this.frequency = f;
        this.engine = e;
        this.storePlace = s;
        this.resultTable = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchedulableReport report = (SchedulableReport) o;

        if (id != report.id) return false;
        return name.equals(report.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SchedulableReport-" + name;
    }
}
