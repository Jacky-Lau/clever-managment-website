package edu.zju.bme.clever.website.view.entity;

import edu.zju.bme.clever.website.model.entity.ArchetypeRelationship;

public class ArchetypeTypeRelationshipInfo {

	private ArchetypeRelationship.RelationType relationType;
	private Integer sourceArchetypeTypeId;
	private Integer destinationArchetypeTypeId;
	private Integer weight = 0;

	public ArchetypeRelationship.RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(ArchetypeRelationship.RelationType relationType) {
		this.relationType = relationType;
	}

	public Integer getSourceArchetypeTypeId() {
		return sourceArchetypeTypeId;
	}

	public void setSourceArchetypeTypeId(Integer sourceArchetypeTypeId) {
		this.sourceArchetypeTypeId = sourceArchetypeTypeId;
	}

	public Integer getDestinationArchetypeTypeId() {
		return destinationArchetypeTypeId;
	}

	public void setDestinationArchetypeTypeId(Integer destinationArchetypeTypeId) {
		this.destinationArchetypeTypeId = destinationArchetypeTypeId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
