//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.01.05 时间 06:55:15 PM CST 
//


package com.pplove.bip.scheduler.oozie.workflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Fs complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Fs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="delete" type="{uri:oozie:workflow:0.2}Delete"/>
 *         &lt;element name="mkdir" type="{uri:oozie:workflow:0.2}Mkdir"/>
 *         &lt;element name="move" type="{uri:oozie:workflow:0.2}Move"/>
 *         &lt;element name="chmod" type="{uri:oozie:workflow:0.2}Chmod"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fs", propOrder = {
    "deleteOrMkdirOrMove"
})
public class Fs {

    @XmlElements({
        @XmlElement(name = "delete", type = Delete.class),
        @XmlElement(name = "mkdir", type = Mkdir.class),
        @XmlElement(name = "move", type = Move.class),
        @XmlElement(name = "chmod", type = Chmod.class)
    })
    protected List<Object> deleteOrMkdirOrMove;

    /**
     * Gets the value of the deleteOrMkdirOrMove property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deleteOrMkdirOrMove property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeleteOrMkdirOrMove().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Delete }
     * {@link Mkdir }
     * {@link Move }
     * {@link Chmod }
     * 
     * 
     */
    public List<Object> getDeleteOrMkdirOrMove() {
        if (deleteOrMkdirOrMove == null) {
            deleteOrMkdirOrMove = new ArrayList<Object>();
        }
        return this.deleteOrMkdirOrMove;
    }

}
