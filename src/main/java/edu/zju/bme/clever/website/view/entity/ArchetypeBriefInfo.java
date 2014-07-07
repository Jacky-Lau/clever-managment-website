package edu.zju.bme.clever.website.view.entity;

import java.util.ArrayList;
import java.util.List;

public class ArchetypeBriefInfo {
	private List<ArchetypeHostInfo> archetypeHostInfos = new ArrayList<ArchetypeHostInfo>();
	private List<ArchetypeRelationshipInfo> archetypeRelationshipInfos = new ArrayList<ArchetypeRelationshipInfo>();

	public List<ArchetypeHostInfo> getArchetypeHostInfos() {
		return archetypeHostInfos;
	}

	public void setArchetypeHostInfos(List<ArchetypeHostInfo> archetypeHostInfos) {
		this.archetypeHostInfos = archetypeHostInfos;
	}

	public List<ArchetypeRelationshipInfo> getArchetypeRelationshipInfos() {
		return archetypeRelationshipInfos;
	}

	public void setArchetypeRelationshipInfos(
			List<ArchetypeRelationshipInfo> archetypeRelationshipInfos) {
		this.archetypeRelationshipInfos = archetypeRelationshipInfos;
	}

}
