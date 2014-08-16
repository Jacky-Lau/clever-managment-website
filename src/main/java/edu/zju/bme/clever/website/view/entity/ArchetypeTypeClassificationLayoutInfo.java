package edu.zju.bme.clever.website.view.entity;

import java.util.List;

public class ArchetypeTypeClassificationLayoutInfo {
	private List<ArchetypeTypeLayoutSettingInfo> settings;
	private float scale;

	public ArchetypeTypeClassificationLayoutInfo() {

	}

	public ArchetypeTypeClassificationLayoutInfo(
			List<ArchetypeTypeLayoutSettingInfo> settings, float scale) {
		super();
		this.settings = settings;
		this.scale = scale;
	}

	public List<ArchetypeTypeLayoutSettingInfo> getSettings() {
		return settings;
	}

	public void setSettings(List<ArchetypeTypeLayoutSettingInfo> settings) {
		this.settings = settings;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
