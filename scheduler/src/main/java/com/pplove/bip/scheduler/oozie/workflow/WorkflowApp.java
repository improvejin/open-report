//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.01.05 时间 06:55:15 PM CST 
//


package com.pplove.bip.scheduler.oozie.workflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>WorkflowApp complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="WorkflowApp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="start" type="{uri:oozie:workflow:0.2}Start"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="decision" type="{uri:oozie:workflow:0.2}Decision"/>
 *           &lt;element name="fork" type="{uri:oozie:workflow:0.2}Fork"/>
 *           &lt;element name="join" type="{uri:oozie:workflow:0.2}Join"/>
 *           &lt;element name="kill" type="{uri:oozie:workflow:0.2}Kill"/>
 *           &lt;element name="action" type="{uri:oozie:workflow:0.2}Action"/>
 *         &lt;/choice>
 *         &lt;element name="end" type="{uri:oozie:workflow:0.2}End"/>
 *         &lt;any namespace='uri:oozie:sla:0.1' minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkflowApp", propOrder = {
    "start",
    "decisionOrForkOrJoin",
    "end",
    "any"
})
@XmlRootElement(name="workflow-app", namespace="uri:oozie:workflow:0.2")
public class WorkflowApp {

    @XmlElement(required = true)
    protected Start start;
    @XmlElements({
        @XmlElement(name = "decision", type = Decision.class),
        @XmlElement(name = "fork", type = Fork.class),
        @XmlElement(name = "join", type = Join.class),
        @XmlElement(name = "kill", type = Kill.class),
        @XmlElement(name = "action", type = Action.class)
    })
    protected List<Object> decisionOrForkOrJoin;
    @XmlElement(required = true)
    protected End end;
    @XmlAnyElement(lax = true)
    protected Object any;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * 获取start属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Start }
     *     
     */
    public Start getStart() {
        return start;
    }

    /**
     * 设置start属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Start }
     *     
     */
    public void setStart(Start value) {
        this.start = value;
    }

    /**
     * Gets the value of the decisionOrForkOrJoin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the decisionOrForkOrJoin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDecisionOrForkOrJoin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Decision }
     * {@link Fork }
     * {@link Join }
     * {@link Kill }
     * {@link Action }
     * 
     * 
     */
    public List<Object> getDecisionOrForkOrJoin() {
        if (decisionOrForkOrJoin == null) {
            decisionOrForkOrJoin = new ArrayList<Object>();
        }
        return this.decisionOrForkOrJoin;
    }

    /**
     * 获取end属性的值。
     * 
     * @return
     *     possible object is
     *     {@link End }
     *     
     */
    public End getEnd() {
        return end;
    }

    /**
     * 设置end属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link End }
     *     
     */
    public void setEnd(End value) {
        this.end = value;
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

}
