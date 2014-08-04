package edu.zju.bme.clever.website.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_TYPE")
@DynamicUpdate(true)
public class ArchetypeType {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ARCHETYPE_TYPE_CLISSIFICATION_ID", updatable = false, insertable = false)
	private Integer classificationId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHETYPE_TYPE_CLISSIFICATION_ID")
	private ArchetypeTypeClassification classification;
	@ManyToMany(targetEntity = ArchetypeHost.class, fetch = FetchType.LAZY, mappedBy = "types")
	private Set<ArchetypeHost> archetypeHosts = new HashSet<ArchetypeHost>();

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

	public ArchetypeTypeClassification getClassification() {
		return classification;
	}

	public void setClassification(ArchetypeTypeClassification classification) {
		this.classification = classification;
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public Set<ArchetypeHost> getArchetypeHosts() {
		return archetypeHosts;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArchetypeType) {
			return ((ArchetypeType) obj).getId() == this.id;
		}
		return false;
	}

}
