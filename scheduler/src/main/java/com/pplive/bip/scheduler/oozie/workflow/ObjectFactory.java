//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.01.05 时间 06:55:15 PM CST 
//


package com.pplive.bip.scheduler.oozie.workflow;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.pplive.bip package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WorkflowApp_QNAME = new QName("uri:oozie:workflow:0.2", "workflow-app");
    private final static QName _MapReduce_QNAME = new QName("uri:oozie:workflow:0.2", "map-reduce");
    private final static QName _Hive_QNAME = new QName("uri:oozie:workflow:0.2", "hive");
    private final static QName _Ssh_QNAME = new QName("uri:oozie:workflow:0.2", "ssh");
    private final static QName _Switch_QNAME = new QName("uri:oozie:workflow:0.2", "switch");
    private final static QName _Pig_QNAME = new QName("uri:oozie:workflow:0.2", "pig");
    private final static QName _Fs_QNAME = new QName("uri:oozie:workflow:0.2", "fs");
    private final static QName _Kill_QNAME = new QName("uri:oozie:workflow:0.2", "kill");
    private final static QName _Java_QNAME = new QName("uri:oozie:workflow:0.2", "java");
    private final static QName _Distcp_QNAME = new QName("uri:oozie:workflow:0.2", "distcp");
    private final static QName _Shell_QNAME = new QName("uri:oozie:workflow:0.2", "shell");
    private final static QName _Spark_QNAME = new QName("uri:oozie:workflow:0.2", "spark");
    private final static QName _Sqoop_QNAME = new QName("uri:oozie:workflow:0.2", "sqoop");
    private final static QName _SubWorkflow_QNAME = new QName("uri:oozie:workflow:0.2", "sub-workflow");
    private final static QName _Email_QNAME = new QName("uri:oozie:workflow:0.2", "email");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.pplive.bip
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Configuration }
     * 
     */
    public Configuration createConfiguration() {
        return new Configuration();
    }

    /**
     * Create an instance of {@link Hive }
     * 
     */
    public Hive createHive() {
        return new Hive();
    }

    /**
     * Create an instance of {@link MapReduce }
     * 
     */
    public MapReduce createMapReduce() {
        return new MapReduce();
    }

    /**
     * Create an instance of {@link WorkflowApp }
     * 
     */
    public WorkflowApp createWorkflowApp() {
        return new WorkflowApp();
    }

    /**
     * Create an instance of {@link Ssh }
     * 
     */
    public Ssh createSsh() {
        return new Ssh();
    }

    /**
     * Create an instance of {@link Kill }
     * 
     */
    public Kill createKill() {
        return new Kill();
    }

    /**
     * Create an instance of {@link Fs }
     * 
     */
    public Fs createFs() {
        return new Fs();
    }

    /**
     * Create an instance of {@link Switch }
     * 
     */
    public Switch createSwitch() {
        return new Switch();
    }

    /**
     * Create an instance of {@link Pig }
     * 
     */
    public Pig createPig() {
        return new Pig();
    }

    /**
     * Create an instance of {@link Distcp }
     * 
     */
    public Distcp createDistcp() {
        return new Distcp();
    }

    /**
     * Create an instance of {@link Java }
     * 
     */
    public Java createJava() {
        return new Java();
    }

    /**
     * Create an instance of {@link Shell }
     * 
     */
    public Shell createShell() {
        return new Shell();
    }

    /**
     * Create an instance of {@link Spark }
     * 
     */
    public Spark createSpark() {
        return new Spark();
    }

    /**
     * Create an instance of {@link Sqoop }
     * 
     */
    public Sqoop createSqoop() {
        return new Sqoop();
    }

    /**
     * Create an instance of {@link Email }
     * 
     */
    public Email createEmail() {
        return new Email();
    }

    /**
     * Create an instance of {@link SubWorkflow }
     * 
     */
    public SubWorkflow createSubWorkflow() {
        return new SubWorkflow();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link Action }
     * 
     */
    public Action createAction() {
        return new Action();
    }

    /**
     * Create an instance of {@link Fork }
     * 
     */
    public Fork createFork() {
        return new Fork();
    }

    /**
     * Create an instance of {@link Start }
     * 
     */
    public Start createStart() {
        return new Start();
    }

    /**
     * Create an instance of {@link Case }
     * 
     */
    public Case createCase() {
        return new Case();
    }

    /**
     * Create an instance of {@link Move }
     * 
     */
    public Move createMove() {
        return new Move();
    }

    /**
     * Create an instance of {@link Prepare }
     * 
     */
    public Prepare createPrepare() {
        return new Prepare();
    }

    /**
     * Create an instance of {@link Streaming }
     * 
     */
    public Streaming createStreaming() {
        return new Streaming();
    }

    /**
     * Create an instance of {@link Mkdir }
     * 
     */
    public Mkdir createMkdir() {
        return new Mkdir();
    }

    /**
     * Create an instance of {@link ForkTransition }
     * 
     */
    public ForkTransition createForkTransition() {
        return new ForkTransition();
    }

    /**
     * Create an instance of {@link Join }
     * 
     */
    public Join createJoin() {
        return new Join();
    }

    /**
     * Create an instance of {@link Default }
     * 
     */
    public Default createDefault() {
        return new Default();
    }

    /**
     * Create an instance of {@link Flag }
     * 
     */
    public Flag createFlag() {
        return new Flag();
    }

    /**
     * Create an instance of {@link Decision }
     * 
     */
    public Decision createDecision() {
        return new Decision();
    }

    /**
     * Create an instance of {@link ActionTransition }
     * 
     */
    public ActionTransition createActionTransition() {
        return new ActionTransition();
    }

    /**
     * Create an instance of {@link End }
     * 
     */
    public End createEnd() {
        return new End();
    }

    /**
     * Create an instance of {@link Chmod }
     * 
     */
    public Chmod createChmod() {
        return new Chmod();
    }

    /**
     * Create an instance of {@link Pipes }
     * 
     */
    public Pipes createPipes() {
        return new Pipes();
    }

    /**
     * Create an instance of {@link Configuration.Property }
     * 
     */
    public Configuration.Property createConfigurationProperty() {
        return new Configuration.Property();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkflowApp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "workflow-app")
    public JAXBElement<WorkflowApp> createWorkflowApp(WorkflowApp value) {
        return new JAXBElement<WorkflowApp>(_WorkflowApp_QNAME, WorkflowApp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MapReduce }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "map-reduce")
    public JAXBElement<MapReduce> createMapReduce(MapReduce value) {
        return new JAXBElement<MapReduce>(_MapReduce_QNAME, MapReduce.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "hive")
    public JAXBElement<Hive> createHive(Hive value) {
        return new JAXBElement<Hive>(_Hive_QNAME, Hive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ssh }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "ssh")
    public JAXBElement<Ssh> createSsh(Ssh value) {
        return new JAXBElement<Ssh>(_Ssh_QNAME, Ssh.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Switch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "switch")
    public JAXBElement<Switch> createSwitch(Switch value) {
        return new JAXBElement<Switch>(_Switch_QNAME, Switch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "pig")
    public JAXBElement<Pig> createPig(Pig value) {
        return new JAXBElement<Pig>(_Pig_QNAME, Pig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "fs")
    public JAXBElement<Fs> createFs(Fs value) {
        return new JAXBElement<Fs>(_Fs_QNAME, Fs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Kill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "kill")
    public JAXBElement<Kill> createKill(Kill value) {
        return new JAXBElement<Kill>(_Kill_QNAME, Kill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Java }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "java")
    public JAXBElement<Java> createJava(Java value) {
        return new JAXBElement<Java>(_Java_QNAME, Java.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Distcp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "distcp")
    public JAXBElement<Distcp> createDistcp(Distcp value) {
        return new JAXBElement<Distcp>(_Distcp_QNAME, Distcp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Shell }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "shell")
    public JAXBElement<Shell> createShell(Shell value) {
        return new JAXBElement<Shell>(_Shell_QNAME, Shell.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Spark }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "spark")
    public JAXBElement<Spark> createSpark(Spark value) {
        return new JAXBElement<Spark>(_Spark_QNAME, Spark.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Sqoop }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "sqoop")
    public JAXBElement<Sqoop> createSqoop(Sqoop value) {
        return new JAXBElement<Sqoop>(_Sqoop_QNAME, Sqoop.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubWorkflow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "sub-workflow")
    public JAXBElement<SubWorkflow> createSubWorkflow(SubWorkflow value) {
        return new JAXBElement<SubWorkflow>(_SubWorkflow_QNAME, SubWorkflow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Email }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "uri:oozie:workflow:0.2", name = "email")
    public JAXBElement<Email> createEmail(Email value) {
        return new JAXBElement<Email>(_Email_QNAME, Email.class, null, value);
    }

}
