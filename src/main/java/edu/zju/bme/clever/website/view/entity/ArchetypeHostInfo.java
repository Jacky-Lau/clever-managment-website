package edu.zju.bme.clever.website.view.entity;

import java.util.ArrayList;
import java.util.List;

public class ArchetypeHostInfo {

	private Integer id;
	private String name;
	private String rmOriginator;
	private String rmName;
	private String rmEntity;
	private String conceptName;
	private List<ArchetypeInfo> archetypeInfos = new ArrayList<ArchetypeInfo>();

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

	public String getRmOriginator() {
		return rmOriginator;
	}

	public void setRmOriginator(String rmOriginator) {
		this.rmOriginator = rmOriginator;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getRmEntity() {
		return rmEntity;
	}

	public void setRmEntity(String rmEntity) {
		this.rmEntity = rmEntity;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public List<ArchetypeInfo> getArchetypeInfos() {
		return archetypeInfos;
	}

	public void setArchetypeInfos(List<ArchetypeInfo> archetypeInfos) {
		this.archetypeInfos = archetypeInfos;
	}

}
