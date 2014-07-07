package edu.zju.bme.clever.website.view.entity;

import edu.zju.bme.clever.website.model.entity.ArchetypeRelationship;

public class ArchetypeRelationshipInfo {

	private ArchetypeRelationship.RelationType relationType;
	private Integer sourceArchetypeHostId;
	private Integer destinationArchetypeHostId;

	public ArchetypeRelationship.RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(ArchetypeRelationship.RelationType relationType) {
		this.relationType = relationType;
	}

	public Integer getSourceArchetypeHostId() {
		return sourceArchetypeHostId;
	}

	public void setSourceArchetypeHostId(Integer sourceArchetypeHostId) {
		this.sourceArchetypeHostId = sourceArchetypeHostId;
	}

	public Integer getDestinationArchetypeHostId() {
		return destinationArchetypeHostId;
	}

	public void setDestinationArchetypeHostId(Integer destinationArchetypeHostId) {
		this.destinationArchetypeHostId = destinationArchetypeHostId;
	}

}
