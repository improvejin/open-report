package com.pplove.bip.report.domain;


import com.pplove.bip.report.Engine;
import com.pplove.bip.report.Frequency;
import com.pplove.bip.report.Priority;
import com.pplove.bip.report.ResultColumn;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Entity
public class Report {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;    //name不可变，有依赖关系

    @Column
    private String description;

    @Column(nullable = false)
    private String owner;

    //业务分类
    @Column(nullable = false)
    private String domain;

    //Query Language
    @Column(nullable = false)
    private String ql;

    //查询的表
    @Convert(converter = JpaConverterJson.class)
    @Column(nullable = false)
    private Set<String> queryTables;

    //依赖的报表, 用另外一个表关联存储
    @Convert(converter = JpaConverterJson.class)
    @Column(nullable = true)
    private Set<String> dependencies;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Engine engine;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    //存储目的地
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Engine storePlace;

    //存储目的地
    @Column(nullable = false)
    private String resultTable;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;

    @Convert(converter = JpaConverterJson.class)
    @Column(nullable = false)
    private List<ResultColumn> resultSchema;

    @Column(nullable = false)
    private boolean schemaCreated = false;

    @Column(nullable = false)
    private boolean view = false;

    //结果保留时间
    @Column
    private String resultRentation;

    @Column(nullable = false)
    private String  status = ReportStatus.START_USING;

    //扩展属性，自依赖、lastCalc等
    @Convert(converter = JpaConverterJson.class)
    @Column
    private Map<String, String> ext;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "create_time", updatable = false)
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "update_time")
    private Date updateTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQl() {
        return ql;
    }

    public void setQl(String ql) {
        this.ql = ql;
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

    public Engine getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(Engine storePlace) {
        this.storePlace = storePlace;
    }

    public String getResultTable() {
        return resultTable;
    }

    public void setResultTable(String resultTable) {
        this.resultTable = resultTable;
    }

    public String generateResultTable() {
        return String.format("%s_%s", domain, name);
    }

    public boolean isSchemaCreated() {
        return schemaCreated;
    }

    public void setSchemaCreated(boolean schemaCreated) {
        this.schemaCreated = schemaCreated;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getResultRentation() {
        return resultRentation;
    }

    public void setResultRentation(String resultRentation) {
        this.resultRentation = resultRentation;
    }

    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ResultColumn> getResultSchema() {
        return resultSchema;
    }

    public void setResultSchema(List<ResultColumn> resultSchema) {
        this.resultSchema = resultSchema;
    }

    public Set<String> getQueryTables() {
        return queryTables;
    }

    public void setQueryTables(Set<String> queryTables) {
        this.queryTables = queryTables;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<String> dependencies) {
        this.dependencies = dependencies;
    }

    public void edit(Report r) {
        this.description = r.description;
        this.owner = r.owner;
        this.domain = r.domain;
        this.status = r.status;
        this.resultTable = r.resultTable;
        this.resultSchema = r.resultSchema;
        this.dependencies = r.dependencies;
        this.schemaCreated = r.schemaCreated;
    }
}
