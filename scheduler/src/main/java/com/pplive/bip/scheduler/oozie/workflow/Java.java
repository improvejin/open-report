//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.01.05 时间 06:55:15 PM CST 
//


package com.pplive.bip.scheduler.oozie.workflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Java">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="job-tracker" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name-node" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Prepare" type="{uri:oozie:workflow:0.2}Prepare" minOccurs="0"/>
 *         &lt;element name="job-xml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Configuration" type="{uri:oozie:workflow:0.2}Configuration" minOccurs="0"/>
 *         &lt;element name="main-class" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="java-opts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="archive" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="capture-output" type="{uri:oozie:workflow:0.2}Flag" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Java", propOrder = {
    "jobTracker",
    "nameNode",
    "prepare",
    "jobXml",
    "configuration",
    "mainClass",
    "javaOpts",
    "arg",
    "file",
    "archive",
    "captureOutput"
})
public class Java {

    @XmlElement(name = "job-tracker", required = true)
    protected String jobTracker;
    @XmlElement(name = "name-node", required = true)
    protected String nameNode;
    @XmlElement(name = "Prepare")
    protected Prepare prepare;
    @XmlElement(name = "job-xml")
    protected String jobXml;
    @XmlElement(name = "Configuration")
    protected Configuration configuration;
    @XmlElement(name = "main-class", required = true)
    protected String mainClass;
    @XmlElement(name = "java-opts")
    protected String javaOpts;
    protected List<String> arg;
    protected List<String> file;
    protected List<String> archive;
    @XmlElement(name = "capture-output")
    protected Flag captureOutput;

    /**
     * 获取jobTracker属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTracker() {
        return jobTracker;
    }

    /**
     * 设置jobTracker属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTracker(String value) {
        this.jobTracker = value;
    }

    /**
     * 获取nameNode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameNode() {
        return nameNode;
    }

    /**
     * 设置nameNode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameNode(String value) {
        this.nameNode = value;
    }

    /**
     * 获取prepare属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Prepare }
     *     
     */
    public Prepare getPrepare() {
        return prepare;
    }

    /**
     * 设置prepare属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Prepare }
     *     
     */
    public void setPrepare(Prepare value) {
        this.prepare = value;
    }

    /**
     * 获取jobXml属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobXml() {
        return jobXml;
    }

    /**
     * 设置jobXml属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobXml(String value) {
        this.jobXml = value;
    }

    /**
     * 获取configuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Configuration }
     *     
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 设置configuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration }
     *     
     */
    public void setConfiguration(Configuration value) {
        this.configuration = value;
    }

    /**
     * 获取mainClass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainClass() {
        return mainClass;
    }

    /**
     * 设置mainClass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainClass(String value) {
        this.mainClass = value;
    }

    /**
     * 获取javaOpts属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaOpts() {
        return javaOpts;
    }

    /**
     * 设置javaOpts属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaOpts(String value) {
        this.javaOpts = value;
    }

    /**
     * Gets the value of the arg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getArg() {
        if (arg == null) {
            arg = new ArrayList<String>();
        }
        return this.arg;
    }

    /**
     * Gets the value of the file property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the file property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFile() {
        if (file == null) {
            file = new ArrayList<String>();
        }
        return this.file;
    }

    /**
     * Gets the value of the archive property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the archive property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArchive().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getArchive() {
        if (archive == null) {
            archive = new ArrayList<String>();
        }
        return this.archive;
    }

    /**
     * 获取captureOutput属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Flag }
     *     
     */
    public Flag getCaptureOutput() {
        return captureOutput;
    }

    /**
     * 设置captureOutput属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Flag }
     *     
     */
    public void setCaptureOutput(Flag value) {
        this.captureOutput = value;
    }

}
