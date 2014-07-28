package edu.zju.bme.clever.website.model.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "LAYOUTS")
@DynamicUpdate(true)
public class Layout {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Calendar modifyTime;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "layout")
	private Set<LayoutSetting> layoutSettings = new HashSet<LayoutSetting>();

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

	public Calendar getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Calendar modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Set<LayoutSetting> getLayoutSettings() {
		return layoutSettings;
	}

	public <T> Map<T, LayoutSetting> getLayoutSettingMap(
			Function<LayoutSetting, T> keyMapper) {
		return this.layoutSettings.stream().collect(
				Collectors.toMap(keyMapper, setting -> setting));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Layout) {
			return ((Layout) obj).getId() == this.id;
		}
		return false;
	}
}
