package edu.zju.bme.clever.website.model.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMMIT_SEQUENCE_ID")
	private CommitSequence commitSequence;
	@Column(name = "COMMIT_SEQUENCE_ID", updatable = false, insertable = false)
	private Integer commitSequenceId;

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

	public CommitSequence getCommitSequence() {
		return commitSequence;
	}

	public void setCommitSequence(CommitSequence commitSequence) {
		this.commitSequence = commitSequence;
	}

	public Integer getCommitSequenceId() {
		return commitSequenceId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HistoriedArchetypeFile) {
			return ((HistoriedArchetypeFile) obj).getId() == this.id;
		}
		return false;
	}
}
