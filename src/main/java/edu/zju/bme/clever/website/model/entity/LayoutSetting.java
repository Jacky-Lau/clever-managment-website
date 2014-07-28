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
@Table(name = "LAYOUT_SETTINGS")
@DynamicUpdate(true)
public class LayoutSetting {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHETYPE_HOST_ID")
	private ArchetypeHost archetypeHost;
	@Column(name = "ARCHETYPE_HOST_ID", updatable = false, insertable = false)
	private Integer archetypeHostId;
	@Column(name = "POSITION_X")
	private String positionX;
	@Column(name = "POSITION_Y")
	private String positionY;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAYOUT_ID")
	private Layout layout;
	@Column(name = "LAYOUT_ID", updatable = false, insertable = false)
	private Integer layoutId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ArchetypeHost getArchetypeHost() {
		return archetypeHost;
	}

	public void setArchetypeHost(ArchetypeHost archetypeHost) {
		this.archetypeHost = archetypeHost;
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

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public Integer getLayoutId() {
		return layoutId;
	}

	public Integer getArchetypeHostId() {
		return archetypeHostId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LayoutSetting) {
			return ((LayoutSetting) obj).getId() == this.id;
		}
		return false;
	}
}
