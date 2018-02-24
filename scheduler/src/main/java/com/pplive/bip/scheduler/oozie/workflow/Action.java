//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.01.05 时间 06:55:15 PM CST 
//


package com.pplive.bip.scheduler.oozie.workflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Action complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Action">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="map-reduce" type="{uri:oozie:workflow:0.2}MapReduce"/>
 *           &lt;element name="pig" type="{uri:oozie:workflow:0.2}Pig"/>
 *           &lt;element name="sub-workflow" type="{uri:oozie:workflow:0.2}SubWorkflow"/>
 *           &lt;element name="fs" type="{uri:oozie:workflow:0.2}Fs"/>
 *           &lt;element name="java" type="{uri:oozie:workflow:0.2}Java"/>
 *           &lt;element name="hive" type="{uri:oozie:workflow:0.2}Hive"/>
 *           &lt;element name="distcp" type="{uri:oozie:workflow:0.2}Distcp"/>
 *           &lt;element name="email" type="{uri:oozie:workflow:0.2}Email"/>
 *           &lt;element name="shell" type="{uri:oozie:workflow:0.2}Shell"/>
 *           &lt;element name="spark" type="{uri:oozie:workflow:0.2}Spark"/>
 *           &lt;element name="sqoop" type="{uri:oozie:workflow:0.2}Sqoop"/>
 *           &lt;element name="ssh" type="{uri:oozie:workflow:0.2}Ssh"/>
 *           &lt;any namespace='##other'/>
 *         &lt;/choice>
 *         &lt;element name="ok" type="{uri:oozie:workflow:0.2}ActionTransition"/>
 *         &lt;element name="error" type="{uri:oozie:workflow:0.2}ActionTransition"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{uri:oozie:workflow:0.2}Identifier" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Action", propOrder = {
    "mapReduce",
    "pig",
    "subWorkflow",
    "fs",
    "java",
    "hive",
    "distcp",
    "email",
    "shell",
    "spark",
    "sqoop",
    "ssh",
    "any",
    "ok",
    "error"
})
public class Action {

    @XmlElement(name = "map-reduce")
    protected MapReduce mapReduce;
    protected Pig pig;
    @XmlElement(name = "sub-workflow")
    protected SubWorkflow subWorkflow;
    protected Fs fs;
    protected Java java;
    protected Hive hive;
    protected Distcp distcp;
    protected Email email;
    protected Shell shell;
    protected Spark spark;
    protected Sqoop sqoop;
    protected Ssh ssh;
    @XmlAnyElement(lax = true)
    protected Object any;
    @XmlElement(required = true)
    protected ActionTransition ok;
    @XmlElement(required = true)
    protected ActionTransition error;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * 获取mapReduce属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MapReduce }
     *     
     */
    public MapReduce getMapReduce() {
        return mapReduce;
    }

    /**
     * 设置mapReduce属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MapReduce }
     *     
     */
    public void setMapReduce(MapReduce value) {
        this.mapReduce = value;
    }

    /**
     * 获取pig属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Pig }
     *     
     */
    public Pig getPig() {
        return pig;
    }

    /**
     * 设置pig属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Pig }
     *     
     */
    public void setPig(Pig value) {
        this.pig = value;
    }

    /**
     * 获取subWorkflow属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SubWorkflow }
     *     
     */
    public SubWorkflow getSubWorkflow() {
        return subWorkflow;
    }

    /**
     * 设置subWorkflow属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SubWorkflow }
     *     
     */
    public void setSubWorkflow(SubWorkflow value) {
        this.subWorkflow = value;
    }

    /**
     * 获取fs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Fs }
     *     
     */
    public Fs getFs() {
        return fs;
    }

    /**
     * 设置fs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Fs }
     *     
     */
    public void setFs(Fs value) {
        this.fs = value;
    }

    /**
     * 获取java属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Java }
     *     
     */
    public Java getJava() {
        return java;
    }

    /**
     * 设置java属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Java }
     *     
     */
    public void setJava(Java value) {
        this.java = value;
    }

    /**
     * 获取hive属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Hive }
     *     
     */
    public Hive getHive() {
        return hive;
    }

    /**
     * 设置hive属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Hive }
     *     
     */
    public void setHive(Hive value) {
        this.hive = value;
    }

    /**
     * 获取distcp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Distcp }
     *     
     */
    public Distcp getDistcp() {
        return distcp;
    }

    /**
     * 设置distcp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Distcp }
     *     
     */
    public void setDistcp(Distcp value) {
        this.distcp = value;
    }

    /**
     * 获取email属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Email }
     *     
     */
    public Email getEmail() {
        return email;
    }

    /**
     * 设置email属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Email }
     *     
     */
    public void setEmail(Email value) {
        this.email = value;
    }

    /**
     * 获取shell属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Shell }
     *     
     */
    public Shell getShell() {
        return shell;
    }

    /**
     * 设置shell属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Shell }
     *     
     */
    public void setShell(Shell value) {
        this.shell = value;
    }

    /**
     * 获取spark属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Spark }
     *     
     */
    public Spark getSpark() {
        return spark;
    }

    /**
     * 设置spark属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Spark }
     *     
     */
    public void setSpark(Spark value) {
        this.spark = value;
    }

    /**
     * 获取sqoop属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Sqoop }
     *     
     */
    public Sqoop getSqoop() {
        return sqoop;
    }

    /**
     * 设置sqoop属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Sqoop }
     *     
     */
    public void setSqoop(Sqoop value) {
        this.sqoop = value;
    }

    /**
     * 获取ssh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Ssh }
     *     
     */
    public Ssh getSsh() {
        return ssh;
    }

    /**
     * 设置ssh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Ssh }
     *     
     */
    public void setSsh(Ssh value) {
        this.ssh = value;
    }

    /**
     * 获取any属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * 设置any属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAny(Object value) {
        this.any = value;
    }

    /**
     * 获取ok属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ActionTransition }
     *     
     */
    public ActionTransition getOk() {
        return ok;
    }

    /**
     * 设置ok属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ActionTransition }
     *     
     */
    public void setOk(ActionTransition value) {
        this.ok = value;
    }

    /**
     * 获取error属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ActionTransition }
     *     
     */
    public ActionTransition getError() {
        return error;
    }

    /**
     * 设置error属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ActionTransition }
     *     
     */
    public void setError(ActionTransition value) {
        this.error = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public String toString() {
        return this.name;
    }

}
