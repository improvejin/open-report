BIP基础经过5年的发展，当初伴随着业务快速发展建立起来，设计相对随意，很多代码处于堆功能状态，维护困难。BIP功能主要分两大部分，一是日志及维度相关的服务，二是报表服务，目前主要面临以下问题：

1. 无SDK，日志格式混乱，从定义到成功发送可进入Hive中的日志，缺乏清晰的说明和详细的标准，接入新日志时沟通成本大，易出错，间接也导致了解析日志代码臃肿
2. 事实表数据延迟较大，小时报表至少延时两三个小时
3. 维度同步无实时性，无法统计最新维度的实时指标
4. mysql2hive全量同步，有性能瓶颈
5. 代码臃肿，引入新组件时随意，版本冲突严重，改动困难

# Solution

针对问题5，我们采用微服务设计思想，将各个功能按业务独立划分，基于Spring Boot完全重新开发。报表功能相对通用，与业务关联不大，因此选择从报表开始重构。

自定义报表提供用户自助进行数据探查的功能，方便用户自定义各种频率报表，支持各种数据源，查看报表结果，从业务和便于实现的角度，可将其分成三大部分：

1. 报表元信息管理
2. 引擎管理
3. 报表调度执行

# Report

## Report Metadata

报表元信息包括报表定义时固有属性以及方便调度解析出的依赖信息。

固有属性：

|属性|说明|
|--|--|
|id|唯一标识|
|create_time|创建时间|
|update_time|更新时间
|owner|报表所有者
|name|报表名
|domain|业务归类
|description|报表描述
|engine|执行引擎
|ql|执行逻辑
|frequency|执行频率
|status|状态
|priority|优先级
|store_place|存储引擎
|view|是否是视图
|result_schema|结果schema
|result_table|结果目的地表名
|result_rentation|结果保留时间
|schema_created|结果schema是否已创建，方便审核时创建结果目的地
|ext|报表属性


依赖信息：

|属性|说明|
|--|--|
|dependencies|依赖的报表
|query_tables|依赖的表


## 报表服务接口

实现报表的增删改查：

	//获取报表详细信息
	@RequestMapping(value = "/list")
    RestResponse<List<Report> > getReportsDetail();

	//添加报表
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    RestResponse<Report> addNewReport(@RequestBody Report r);

	//按id查询报表
    @GetMapping(path="/id/{id}")
    RestResponse<Report> getReportById(@PathVariable Long id)；
	
	//按名字查询报表
	@GetMapping(path="/name/{name}")
    RestResponse<Report> getReportByName(@PathVariable String name)；

	//按频率查询报表
	@GetMapping(path="/frequency/{f}")
    Map<String, SchedulableReport> getReportsByFrequery(@PathVariable String f)；

	//删除报表
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    RestResponse deleteReportById(@PathVariable Long id)；

    //更新报表
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    Report updateReport(@RequestBody Report r)；

	//清空报表缓存
	@RequestMapping(value = "/reload")
    RestResponse reloadReport();

	//获取报表schema
    @RequestMapping(value = "/schema", method = RequestMethod.GET)
    RestResponse getSchema(@RequestBody Report r);

	//审核报表
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
    Report auditReport(@RequestBody Report r);
	
	//获取报表结果
	@RequestMapping(value = "/result/{name}/{startDate}/{endDate}/{callback}")
    RestResponse getReportResult(@PathVariable String name, @PathVariable Integer startDate,
                                        @PathVariable Integer endDate, @PathVariable(required = false) String callback)
	
	//获取name依赖的报表
	@RequestMapping("/depend/{name}")
    Set<String> getDependReports(@PathVariable String name)

	//获取依赖于name的报表
    @RequestMapping("/depended/{name}")
    public List<Report> getDependedReports(@PathVariable String name)
	

# Interpreter

报表服务支持各种不同的数据源和结果存储目的地，每个执行和存储引擎提供的功能相同，因此将每种引擎独立成单独的服务，减少耦合，既方便添加新引擎，也能减少包冲突。

Interpreter服务抽象出以下接口，添加一个具体Interpreter实现以下接口即可：

    //根据sql生成结果列信息
    @RequestMapping(value = "/schema")
    List<ResultColumn> getResultSchema(@RequestParam String ql) throws SQLException;

	//获取sql查询的表
    @RequestMapping(value = "/query_table")
    Set<String> getQueryTable(@RequestParam String ql);

    //创建存储报表结果表
    @RequestMapping(path = "/create/{table}")
    boolean createResultTable(@PathVariable("table") String table, @RequestBody List<ResultColumn> resultColumns) throws SQLException;

    //更改报表结果表schema
    @RequestMapping(path = "/alter/{table}", method = RequestMethod.POST)
    boolean alterResultTable(@PathVariable("table") String table, @RequestBody AlterColumn alter) throws SQLException;

    //获取报表结果接口
    @RequestMapping(value = "/query")
    List<Map<String, Object>> getResult(@RequestParam String ql) throws SQLException;

由于所有引擎的数据类型并不完全一致，在报表层面需要统一的类型屏蔽各引擎之间的差别，目前直接借用了java.sql.JDBCType中的类型，因此具体的引擎实现需要提供引擎数据类型与JDBCType间的转换。

# Scheduler

scheduler从report service处获取报表就依赖信息，构建报表依赖关系图，检查报表是否具有可执行条件，然后将报表提交至类Oozie的调度系统调度执行。

我们将报表按频率分小时、天、星期、月、年分开进行调度，同一种频率的报表形成依赖关系图，检查相同的调度时刻是否具有可调度条件，对于可调度的报表，添加导入结果到目的表的逻辑，形成符合调度引擎语法规则的workflow，提交执行，在提交和执行完成过程中，记录报表状态，方便对报表状态进行跟踪。

使用oozie作为调度工具需要将workflow对象转换成xml,使用java自带的工具xjc可以将xsd schema转换成Java对象，方便对workflow中Action/Fork/Join进行操作。


# UI

报表平台只专注于报表相关的功能，包括报表管理、引擎管理，报表管理又可以划分普通用户视图和管理员视图，因此UI可分为三个Tab:

1. 普通用户报表视图，对报表进行CRUD，对应现在bip上的数据集页面
2. 管理员报表视图，方便管理员对报表管理，可查看报表基本属性以及依赖关系，运行状态，报表访问频率、报表重跑等高级功能，相当于给报表画像，相当于DiLu上的报表管理页面，所有报表管理相关的功能都应该在这个页面增加服务入口
3. 引擎管理，参照zepplin中的interpreter管理，将所有interpreter的参数进行页面化配置，可实现多种引擎、多个实例的支持。
