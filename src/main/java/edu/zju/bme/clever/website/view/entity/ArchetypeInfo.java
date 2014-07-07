package edu.zju.bme.clever.website.view.entity;

import java.util.ArrayList;
import java.util.List;

public class ArchetypeInfo {

	private Integer id;
	private String name;
	private String version;
	private List<ArchetypeNodeInfo> archetypeNodeInfos = new ArrayList<ArchetypeNodeInfo>();

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<ArchetypeNodeInfo> getArchetypeNodeInfos() {
		return archetypeNodeInfos;
	}

	public void setArchetypeNodeInfos(List<ArchetypeNodeInfo> archetypeNodeInfos) {
		this.archetypeNodeInfos = archetypeNodeInfos;
	}

}
