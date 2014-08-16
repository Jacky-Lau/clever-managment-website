package edu.zju.bme.clever.website.view.entity;

import java.util.List;

public class ArchetypeTypeLayoutInfo {

	private List<ArchetypeHostLayoutSettingInfo> settings;
	private float scale;
	
	public ArchetypeTypeLayoutInfo(){
		
	}

	public ArchetypeTypeLayoutInfo(
			List<ArchetypeHostLayoutSettingInfo> settings, float scale) {
		super();
		this.settings = settings;
		this.scale = scale;
	}

	public List<ArchetypeHostLayoutSettingInfo> getSettings() {
		return settings;
	}

	public void setSettings(List<ArchetypeHostLayoutSettingInfo> settings) {
		this.settings = settings;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
