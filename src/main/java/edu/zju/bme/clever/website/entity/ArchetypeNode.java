package edu.zju.bme.clever.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.openehr.am.archetype.ontology.ArchetypeTerm;

@Entity
@Table(name = "ARCHETYPE_NODE")
@DynamicUpdate(true)
public class ArchetypeNode {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NODE_PATH")
	private String nodePath;
	@Column(name = "CODE")
	private String code;
	@Column(name = "NODE_NAME")
	private String nodeName;
	@Column(name = "RM_TYPE")
	private String rmType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHETYPE_FILE_ID")
	private ArchetypeFile archetypeFile;
	private ArchetypeTerm archetypeTerm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getRmType() {
		return rmType;
	}

	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	public ArchetypeFile getArchetypeFile() {
		return archetypeFile;
	}

	public void setArchetypeFile(ArchetypeFile archetypeFile) {
		this.archetypeFile = archetypeFile;
	}

	public ArchetypeTerm getArchetypeTerm() {
		return archetypeTerm;
	}

	public void setArchetypeTerm(ArchetypeTerm archetypeTerm) {
		this.archetypeTerm = archetypeTerm;
	}

}
