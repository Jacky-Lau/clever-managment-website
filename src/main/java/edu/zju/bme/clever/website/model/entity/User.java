package edu.zju.bme.clever.website.model.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "USERS")
@DynamicUpdate(true)
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4791462313378825505L;
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue
	private Integer userID;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "ENABLED")
	private boolean isEnabled;
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	private Calendar updateTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGOUT_TIME")
	private Calendar lastLogoutTime;

	public User() {

	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public Calendar getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Calendar lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return ((User) obj).getUserID() == this.userID;
		}
		return false;
	}

}
