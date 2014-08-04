package edu.zju.bme.clever.website.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_HOST_LAYOUT_SETTING")
@DynamicUpdate(true)
public class ArchetypeHostLayoutSetting {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "POSITION_X")
	private String positionX;
	@Column(name = "POSITION_Y")
	private String positionY;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	@Column(name = "USER_ID", updatable = false, insertable = false)
	private Integer userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHETYPE_TYPE_ID")
	private ArchetypeType archetypeType;
	@Column(name = "ARCHETYPE_TYPE_ID", updatable = false, insertable = false)
	private Integer archetypeTypeId;

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

	public String getPositionX() {
		return positionX;
	}

	public void setPositionX(String positionX) {
		this.positionX = positionX;
	}

	public String getPositionY() {
		return positionY;
	}

	public void setPositionY(String positionY) {
		this.positionY = positionY;
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

	public ArchetypeHost getArchetypeHost() {
		return archetypeHost;
	}

	public void setArchetypeHost(ArchetypeHost archetypeHost) {
		this.archetypeHost = archetypeHost;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getArchetypeTypeId() {
		return archetypeTypeId;
	}

	public Integer getArchetypeHostId() {
		return archetypeHostId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArchetypeHostLayoutSetting) {
			return ((ArchetypeHostLayoutSetting) obj).getId() == this.id;
		}
		return false;
	}
}
