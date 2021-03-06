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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "COMMIT_SEQUENCE")
@DynamicUpdate(true)
public class CommitSequence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4405523932507069688L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMIT_TIME")
	private Calendar commitTime;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBMITTER_ID")
	private User submitter;
	@Column(name = "SUBMITTER_ID", updatable = false, insertable = false)
	private Integer submitterId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Calendar commitTime) {
		this.commitTime = commitTime;
	}

	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	public Integer getSubmitterId() {
		return submitterId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommitSequence) {
			return ((CommitSequence) obj).getId() == this.id;
		}
		return false;
	}
}
