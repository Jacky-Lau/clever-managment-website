package edu.zju.bme.clever.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_RELATION")
@DynamicUpdate(true)
public class ArchetypeRelation {

	public enum RelationType {
		OneToMany("OneToMany"),
		ManyToOne("ManyToOne"), 
		ManyToMany("ManyToMany");
		
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
	@Column(name = "SOURCE_ARCHETYPE")
	private String sourceArchtype;
	@Column(name = "SOURCE_ARCHETYPE_ID")
	private Integer sourceArchtypeId;
	@Column(name = "DESTINATION_ARCHETYPE")
	private String destinationArchtype;
	@Column(name = "DESTINATION_ARCHETYPE_ID")
	private Integer destinationArchtypeId;
	@Column(name = "SOURCE_ARCHETYPE_NODE")
	private String sourceArchtypeNode;
	@Column(name = "SOURCE_ARCHETYPE_NODE_ID")
	private Integer sourceArchtypeNodeId;
	@Column(name = "DESTINATION_ARCHETYPE_NODE")
	private String destinationArchtypeNode;
	@Column(name = "DESTINATION_ARCHETYPE_NODE_ID")
	private Integer destinationArchtypeNodeId;

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

	public String getSourceArchtype() {
		return sourceArchtype;
	}

	public void setSourceArchtype(String sourceArchtype) {
		this.sourceArchtype = sourceArchtype;
	}

	public Integer getSourceArchtypeId() {
		return sourceArchtypeId;
	}

	public void setSourceArchtypeId(Integer sourceArchtypeId) {
		this.sourceArchtypeId = sourceArchtypeId;
	}

	public String getDestinationArchtype() {
		return destinationArchtype;
	}

	public void setDestinationArchtype(String destinationArchtype) {
		this.destinationArchtype = destinationArchtype;
	}

	public Integer getDestinationArchtypeId() {
		return destinationArchtypeId;
	}

	public void setDestinationArchtypeId(Integer destinationArchtypeId) {
		this.destinationArchtypeId = destinationArchtypeId;
	}

	public String getSourceArchtypeNode() {
		return sourceArchtypeNode;
	}

	public void setSourceArchtypeNode(String sourceArchtypeNode) {
		this.sourceArchtypeNode = sourceArchtypeNode;
	}

	public Integer getSourceArchtypeNodeId() {
		return sourceArchtypeNodeId;
	}

	public void setSourceArchtypeNodeId(Integer sourceArchtypeNodeId) {
		this.sourceArchtypeNodeId = sourceArchtypeNodeId;
	}

	public String getDestinationArchtypeNode() {
		return destinationArchtypeNode;
	}

	public void setDestinationArchtypeNode(String destinationArchtypeNode) {
		this.destinationArchtypeNode = destinationArchtypeNode;
	}

	public Integer getDestinationArchtypeNodeId() {
		return destinationArchtypeNodeId;
	}

	public void setDestinationArchtypeNodeId(Integer destinationArchtypeNodeId) {
		this.destinationArchtypeNodeId = destinationArchtypeNodeId;
	}
	
	public void setSourceArchetypeFile(ArchetypeFile sourceArchtype){
		this.sourceArchtype = sourceArchtype.getName();
		this.sourceArchtypeId = sourceArchtype.getId();
	}
	
	public void setDestinationArchetypeFile(ArchetypeFile destinationArchtype){
		this.destinationArchtype = destinationArchtype.getName();
		this.destinationArchtypeId = destinationArchtype.getId();
	}
	
	public void setSourceArchetypeNode(ArchetypeNode sourceNode){
		this.sourceArchtypeNode = sourceNode.getNodeName();
		this.sourceArchtypeNodeId = sourceNode.getId();
	}
	
	public void setDestinationArchetypeNode(ArchetypeNode destinationNode){
		this.destinationArchtypeNode = destinationNode.getNodeName();
		this.destinationArchtypeNodeId = destinationNode.getId();
	}

}
