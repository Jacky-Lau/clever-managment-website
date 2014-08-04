package edu.zju.bme.clever.website.view.entity;

import java.util.ArrayList;
import java.util.List;

public class ClassificationBriefInfo {
	
	private Integer id;
	private String name;
	private List<ArchetypeTypeRelationshipInfo> archetypeTypeRelationshipInfos = new ArrayList<ArchetypeTypeRelationshipInfo>();
	private List<ArchetypeTypeInfo> archetypeTypeInfos = new ArrayList<ArchetypeTypeInfo>();

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

	public List<ArchetypeTypeRelationshipInfo> getArchetypeTypeRelationshipInfos() {
		return archetypeTypeRelationshipInfos;
	}

	public void setArchetypeTypeRelationshipInfos(
			List<ArchetypeTypeRelationshipInfo> archetypeTypeRelationshipInfos) {
		this.archetypeTypeRelationshipInfos = archetypeTypeRelationshipInfos;
	}

	public List<ArchetypeTypeInfo> getArchetypeTypeInfos() {
		return archetypeTypeInfos;
	}

	public void setArchetypeTypeInfos(List<ArchetypeTypeInfo> archetypeTypeInfos) {
		this.archetypeTypeInfos = archetypeTypeInfos;
	}

}
