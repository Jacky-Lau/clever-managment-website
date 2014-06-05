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
@Table(name = "HISTORIED_ARCHETYPE_FILE")
@DynamicUpdate(true)
public class HistoriedArchetypeFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418395783754963084L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	// @Column(name="NAME", unique = true) the historied version need not to be
	// unique
	@Column(name = "NAME")
	private String name;
	@Lob
	@Column(name = "CONTENT")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HISTORIED_TIME")
	private Calendar historiedTime;
	@ManyToOne
	private CommitSequence commitSequence;

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

	public Calendar getHistoriedTime() {
		return historiedTime;
	}

	public void setHistoriedTime(Calendar historiedTime) {
		this.historiedTime = historiedTime;
	}

	public CommitSequence getCommitSequence() {
		return commitSequence;
	}

	public void setCommitSequence(CommitSequence commitSequence) {
		this.commitSequence = commitSequence;
	}
}
