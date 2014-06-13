package edu.zju.bme.clever.website.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Calendar modifyTime;
	@ManyToOne
	private CommitSequence commitSequence;

	public ArchetypeFile() {

	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Calendar getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Calendar modifyTime) {
		this.modifyTime = modifyTime;
	}

	public CommitSequence getCommitSequence() {
		return commitSequence;
	}

	public void setCommitSequence(CommitSequence commitSequence) {
		this.commitSequence = commitSequence;
	}
}
