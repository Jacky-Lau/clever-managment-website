package edu.zju.bme.clever.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_RELATIONSHIP")
@DynamicUpdate(true)
public class ArchetypeRelationship {

	public enum RelationType {
		OneToMany("OneToMany"), ManyToOne("ManyToOne"), ManyToMany("ManyToMany");

		private final String name;

		public String getName() {
			return name;
		}

		public String toString() {
			return name;
		}

		RelationType(String name) {
			this.name = name;
		}
	}

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "RELATION_TYPE")
	private RelationType relationType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCE_ARCHETYPE_HOST_ID")
	private ArchetypeHost sourceArchetypeHost;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DESTINATION_ARCHETYPE_HOST_ID")
	private ArchetypeHost destinationArchetypeHost;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public ArchetypeHost getSourceArchetypeHost() {
		return sourceArchetypeHost;
	}

	public void setSourceArchetypeHost(ArchetypeHost sourceArchetypeHost) {
		this.sourceArchetypeHost = sourceArchetypeHost;
	}

	public ArchetypeHost getDestinationArchetypeHost() {
		return destinationArchetypeHost;
	}

	public void setDestinationArchetypeHost(
			ArchetypeHost destinationArchetypeHost) {
		this.destinationArchetypeHost = destinationArchetypeHost;
	}

}
