package edu.zju.bme.clever.website.view.entity;

import java.util.ArrayList;
import java.util.List;

public class ArchetypeTypeInfo {

	private String name;
	private Integer id;
	private List<ArchetypeHostInfo> archetypeHostInfos = new ArrayList<ArchetypeHostInfo>();

	public String getName() {
		return name;
	}

	public void setName(String typeName) {
		this.name = typeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ArchetypeHostInfo> getArchetypeHostInfos() {
		return archetypeHostInfos;
	}

	public void setArchetypeHostInfos(List<ArchetypeHostInfo> archetypeHostInfos) {
		this.archetypeHostInfos = archetypeHostInfos;
	}

}
