package edu.zju.bme.clever.website.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1484866953524565883L;
	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue
	private int roleID;
	@Column(name = "ROLE_NAME")
	private String roleName;
	@ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY, mappedBy="roles")
	private Set<User> users;

	public Role() {

	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role) {
			return ((Role) obj).getRoleID() == this.roleID;
		}
		return false;
	}

}
