package edu.zju.bme.clever.website.model.entity;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
	private List<LayoutSetting> layoutSettings;

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

	public List<LayoutSetting> getLayoutSettings() {
		return layoutSettings;
	}

	public void setLayoutSettings(List<LayoutSetting> layoutSettings) {
		this.layoutSettings = layoutSettings;
	}

	public <T> Map<T, LayoutSetting> getLayoutSettingMap(
			Function<LayoutSetting, T> keyMapper) {
		return this.layoutSettings.stream().collect(
				Collectors.toMap(keyMapper, setting -> setting));
	}
}
