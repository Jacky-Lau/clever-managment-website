package edu.zju.bme.clever.website.model.entity;

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
	@Column(name = "ORIGINAL_NODE_NAME")
	private String originalNodeName;
	@Column(name = "CURRENT_NODE_NAME")
	private String currentNodeName;
	@Column(name = "ORIGINAL_VERSION")
	private String originalVersion;
	@Column(name = "CURRENT_VERSION")
	private String currentVersion;
	@Column(name = "RM_TYPE")
	private String rmType;
	@Column(name = "ALIAS_NAME")
	private String aliasName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHETYPE_HOST_ID")
	private ArchetypeHost archetypeHost;
	@Column(name = "ARCHETYPE_HOST_ID", updatable = false, insertable = false)
	private Integer archetypeHostId;

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

	public String getOriginalNodeName() {
		return originalNodeName;
	}

	public void setOriginalNodeName(String originalNodeName) {
		this.originalNodeName = originalNodeName;
	}

	public String getCurrentNodeName() {
		return currentNodeName;
	}

	public void setCurrentNodeName(String currentNodeName) {
		this.currentNodeName = currentNodeName;
	}

	public String getRmType() {
		return rmType;
	}

	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	public ArchetypeHost getArchetypeHost() {
		return archetypeHost;
	}

	public void setArchetypeHost(ArchetypeHost archetypeHost) {
		this.archetypeHost = archetypeHost;
	}

	public String getOriginalVersion() {
		return originalVersion;
	}

	public void setOriginalVersion(String originalVersion) {
		this.originalVersion = originalVersion;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Integer getArchetypeHostId() {
		return archetypeHostId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArchetypeNode) {
			return ((ArchetypeNode) obj).getId() == this.id;
		}
		return false;
	}
}
