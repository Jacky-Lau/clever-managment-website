package edu.zju.bme.clever.website.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_HOST")
@DynamicUpdate(true)
public class ArchetypeHost {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "RM_ORINGATOR")
	private String rmOriginator;
	@Column(name = "RM_NAME")
	private String rmName;
	@Column(name = "RM_ENTITY")
	private String rmEntity;
	@Column(name = "CONCEPT_NAME")
	private String conceptName;
	@Column(name = "LATEST_VERSION")
	private String latestVersion;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Calendar modifyTime;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "archetypeHost")
	private List<ArchetypeFile> archetypeFiles = new ArrayList<ArchetypeFile>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "archetypeHost")
	private List<ArchetypeNode> archetypeNodes = new ArrayList<ArchetypeNode>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "archetypeHost")
	private List<ArchetypeNodeChangeLog> archetypeNodeChangeLogs = new ArrayList<ArchetypeNodeChangeLog>();

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

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public Calendar getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Calendar modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<ArchetypeFile> getArchetypeFiles() {
		return archetypeFiles;
	}

	public void setArchetypeFiles(List<ArchetypeFile> archetypeFiles) {
		this.archetypeFiles = archetypeFiles;
	}

	public List<ArchetypeNode> getArchetypeNodes() {
		return archetypeNodes;
	}

	public void setArchetypeNodes(List<ArchetypeNode> archetypeNodes) {
		this.archetypeNodes = archetypeNodes;
	}

	public List<ArchetypeNodeChangeLog> getArchetypeNodeChangeLogs() {
		return archetypeNodeChangeLogs;
	}

	public void setArchetypeNodeChangeLogs(
			List<ArchetypeNodeChangeLog> archetypeNodeChangeLogs) {
		this.archetypeNodeChangeLogs = archetypeNodeChangeLogs;
	}

	public <T> Map<T, ArchetypeNode> getArchetypeNodeMap(
			Function<ArchetypeNode, T> keyMapper) {
		return this.getArchetypeNodes().stream()
				.collect(Collectors.toMap(keyMapper, node -> node));
	}

	public <T> Map<T, ArchetypeFile> getArchetypeFileMap(
			Function<ArchetypeFile, T> keyMapper) {
		return this.getArchetypeFiles().stream()
				.collect(Collectors.toMap(keyMapper, file -> file));
	}

	public <T> Map<T, ArchetypeNodeChangeLog> getArchetypeNodeChangeLogMap(
			Function<ArchetypeNodeChangeLog, T> keyMapper) {
		return this.getArchetypeNodeChangeLogs().stream()
				.collect(Collectors.toMap(keyMapper, log -> log));
	}
}
