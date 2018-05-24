//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.01.05 时间 06:55:15 PM CST 
//


package com.pplove.bip.scheduler.oozie.workflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SubWorkflow complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SubWorkflow">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="app-path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="propagate-Configuration" type="{uri:oozie:workflow:0.2}Flag" minOccurs="0"/>
 *         &lt;element name="Configuration" type="{uri:oozie:workflow:0.2}Configuration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubWorkflow", propOrder = {
    "appPath",
    "propagateConfiguration",
    "configuration"
})
public class SubWorkflow {

    @XmlElement(name = "app-path", required = true)
    protected String appPath;
    @XmlElement(name = "propagate-Configuration")
    protected Flag propagateConfiguration;
    @XmlElement(name = "Configuration")
    protected Configuration configuration;

    /**
     * 获取appPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppPath() {
        return appPath;
    }

    /**
     * 设置appPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppPath(String value) {
        this.appPath = value;
    }

    /**
     * 获取propagateConfiguration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Flag }
     *     
     */
    public Flag getPropagateConfiguration() {
        return propagateConfiguration;
    }

    /**
     * 设置propagateConfiguration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Flag }
     *     
     */
    public void setPropagateConfiguration(Flag value) {
        this.propagateConfiguration = value;
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

}
