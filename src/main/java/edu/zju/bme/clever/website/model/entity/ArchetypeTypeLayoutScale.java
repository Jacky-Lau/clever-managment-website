package edu.zju.bme.clever.website.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_TYPE_VIEW_SCALE")
@DynamicUpdate(true)
public class ArchetypeTypeLayoutScale {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	@Column(name = "USER_ID", insertable = false, updatable = false)
	private Integer userId;
	@ManyToOne
	@JoinColumn(name = "ARCHETYPE_TYPE_ID")
	private ArchetypeType archetypeType;
	@Column(name = "ARCHETYPE_TYPE_ID", insertable = false, updatable = false)
	private Integer archetypeTypeId;
	@Column(name = "SCALE")
	private float scale;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArchetypeType getArchetypeType() {
		return archetypeType;
	}

	public void setArchetypeType(ArchetypeType archetypeType) {
		this.archetypeType = archetypeType;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getArchetypeTypeId() {
		return archetypeTypeId;
	}

}
