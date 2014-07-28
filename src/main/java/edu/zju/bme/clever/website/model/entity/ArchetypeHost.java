package edu.zju.bme.clever.website.model.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	private Set<ArchetypeFile> archetypeFiles = new HashSet<ArchetypeFile>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "archetypeHost")
	private Set<ArchetypeNode> archetypeNodes = new HashSet<ArchetypeNode>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "archetypeHost")
	private Set<ArchetypeNodeChangeLog> archetypeNodeChangeLogs = new HashSet<ArchetypeNodeChangeLog>();

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

	public Set<ArchetypeFile> getArchetypeFiles() {
		return archetypeFiles;
	}

	public Set<ArchetypeNode> getArchetypeNodes() {
		return archetypeNodes;
	}

	public Set<ArchetypeNodeChangeLog> getArchetypeNodeChangeLogs() {
		return archetypeNodeChangeLogs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArchetypeHost) {
			return ((ArchetypeHost) obj).getId() == this.id;
		}
		return false;
	}

}
