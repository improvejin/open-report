//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.28 at 10:31:24 AM CST 
//


package com.pplive.bip.scheduler.oozie.workflow2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for FS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delete" type="{uri:oozie:workflow:0.1}DELETE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mkdir" type="{uri:oozie:workflow:0.1}MKDIR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="move" type="{uri:oozie:workflow:0.1}MOVE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="chmod" type="{uri:oozie:workflow:0.1}CHMOD" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FS", propOrder = {
    "delete",
    "mkdir",
    "move",
    "chmod"
})
public class FSNode {

    protected List<DeleteCmd> delete;
    protected List<MkdirCmd> mkdir;
    protected List<MoveCmd> move;
    protected List<ChmodCmd> chmod;

    /**
     * Gets the value of the delete property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delete property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelete().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeleteCmd }
     * 
     * 
     */
    public List<DeleteCmd> getDelete() {
        if (delete == null) {
            delete = new ArrayList<DeleteCmd>();
        }
        return this.delete;
    }

    /**
     * Gets the value of the mkdir property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mkdir property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMkdir().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MkdirCmd }
     * 
     * 
     */
    public List<MkdirCmd> getMkdir() {
        if (mkdir == null) {
            mkdir = new ArrayList<MkdirCmd>();
        }
        return this.mkdir;
    }

    /**
     * Gets the value of the move property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the move property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMove().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MoveCmd }
     * 
     * 
     */
    public List<MoveCmd> getMove() {
        if (move == null) {
            move = new ArrayList<MoveCmd>();
        }
        return this.move;
    }

    /**
     * Gets the value of the chmod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chmod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChmod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChmodCmd }
     * 
     * 
     */
    public List<ChmodCmd> getChmod() {
        if (chmod == null) {
            chmod = new ArrayList<ChmodCmd>();
        }
        return this.chmod;
    }

}
