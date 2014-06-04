package edu.zju.bme.clever.website.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@Column(name="ID")
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMIT_TIME")
	private Calendar commitTime;
	
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

}
