package com.pplive.bip.scheduler.oozie.workflow2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ACTION complex type Java class。
 * 
 * 
 * <pre>
 * &lt;complexType name="ACTION">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="job-tracker" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name-node" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prepare" type="{uri:oozie:shell-action:0.1}PREPARE" minOccurs="0"/>
 *         &lt;element name="job-xml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="configuration" type="{uri:oozie:shell-action:0.1}CONFIGURATION" minOccurs="0"/>
 *         &lt;element name="exec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="argument" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="env-var" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="archive" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="capture-output" type="{uri:oozie:shell-action:0.1}FLAG" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SHELL", propOrder = {
		"jobTracker", //
		"nameNode", //
		"prepare",//
		"jobXml", //
		"configuration",// 
		"exec", //
		"argument",// 
		"envVar", //
		"file",//
		"archive"})
public class ShellNode {

	@XmlElement(name = "job-tracker", required = true, namespace = "uri:oozie:shell-action:0.1")
	protected String jobTracker;
	@XmlElement(name = "name-node", required = true, namespace = "uri:oozie:shell-action:0.1")
	protected String nameNode;
	@XmlElement(name = "prepare", namespace = "uri:oozie:shell-action:0.1")
	protected PrepareNode prepare;
	@XmlElement(name = "job-xml", namespace = "uri:oozie:shell-action:0.1")
	protected String jobXml;
	protected ConfigurationNode configuration;
	@XmlElement(required = true, namespace = "uri:oozie:shell-action:0.1")
	protected String exec;
	protected List<String> argument;
	@XmlElement(name = "env-var", namespace = "uri:oozie:shell-action:0.1")
	protected List<String> envVar;
	@XmlElement(namespace = "uri:oozie:shell-action:0.1")
	protected List<String> file;
	@XmlElement(namespace = "uri:oozie:shell-action:0.1")
	protected List<String> archive;

	/**
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJobTracker() {
		return jobTracker;
	}

	/**
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJobTracker(String value) {
		this.jobTracker = value;
	}

	/**
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNameNode() {
		return nameNode;
	}

	/**
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNameNode(String value) {
		this.nameNode = value;
	}

	/**
	 * 
	 * @return possible object is {@link PREPARE }
	 * 
	 */
	public PrepareNode getPrepare() {
		return prepare;
	}

	/**
	 * 
	 * @param value
	 *            allowed object is {@link PREPARE }
	 * 
	 */
	public void setPrepare(PrepareNode value) {
		this.prepare = value;
	}

	/**
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJobXml() {
		return jobXml;
	}

	/**
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJobXml(String value) {
		this.jobXml = value;
	}

	/**
	 * 
	 * @return possible object is {@link CONFIGURATION }
	 * 
	 */
	public ConfigurationNode getConfiguration() {
		return configuration;
	}

	/**
	 * 
	 * @param value
	 *            allowed object is {@link CONFIGURATION }
	 * 
	 */
	public void setConfiguration(ConfigurationNode value) {
		this.configuration = value;
	}

	/**
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getExec() {
		return exec;
	}

	/**
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setExec(String value) {
		this.exec = value;
	}

	/**
	 * Gets the value of the argument property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the argument property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getArgument().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getArgument() {
		if (argument == null) {
			argument = new ArrayList<String>();
		}
		return this.argument;
	}

	public void setArgument(List<String> argument) {
		this.argument = argument;
	}

	/**
	 * Gets the value of the envVar property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the envVar property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getEnvVar().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getEnvVar() {
		if (envVar == null) {
			envVar = new ArrayList<String>();
		}
		return this.envVar;
	}

	/**
	 * Gets the value of the file property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the file property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getFile().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getFile() {
		if (file == null) {
			file = new ArrayList<String>();
		}
		return this.file;
	}

	public void addFile(String fileContent) {
		if (file == null) {
			file = new ArrayList<String>();
		}
		file.add(fileContent);
	}

	/**
	 * Gets the value of the archive property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the archive property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getArchive().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getArchive() {
		if (archive == null) {
			archive = new ArrayList<String>();
		}
		return this.archive;
	}

}
