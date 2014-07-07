package edu.zju.bme.clever.website.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ARCHETYPE_FILE")
@DynamicUpdate(true)
public class ArchetypeFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5901980017608693553L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME", unique = true)
	private String name;
	@Lob
	@Column(name = "PURPOSE")
	private String purpose;
	@Lob
	@Column(name = "[USE]")
	private String use;
	@Column(name = "KEYWORDS")
	private String keywords;
	@Column(name = "ORIGINAL_LANGUAGE")
	private String originalLanguage;
	@Lob
	@Column(name = "CONTENT")
	private String content;
	@Column(name = "RM_ORINGATOR")
	private String rmOriginator;
	@Column(name = "RM_NAME")
	private String rmName;
	@Column(name = "RM_ENTITY")
	private String rmEntity;
	@Column(name = "CONCEPT_NAME")
	private String conceptName;
	@Column(name = "VERSION")
	private String version;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMMIT_SEQUENCE_ID")
	private CommitSequence commitSequence;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHETYPE_HOST_ID")
	private ArchetypeHost archetypeHost;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRmOriginator() {
		return rmOriginator;
	}

	public void setRmOriginator(String rmOriginator) {
		this.rmOriginator = rmOriginator;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getRmEntity() {
		return rmEntity;
	}

	public void setRmEntity(String rmEntity) {
		this.rmEntity = rmEntity;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public CommitSequence getCommitSequence() {
		return commitSequence;
	}

	public void setCommitSequence(CommitSequence commitSequence) {
		this.commitSequence = commitSequence;
	}

	public ArchetypeHost getArchetypeHost() {
		return archetypeHost;
	}

	public void setArchetypeHost(ArchetypeHost archetypeHost) {
		this.archetypeHost = archetypeHost;
	}

}
