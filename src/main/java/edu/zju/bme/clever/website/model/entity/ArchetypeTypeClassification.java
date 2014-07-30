package edu.zju.bme.clever.website.model.entity;

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

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ARCHETYPE_TYPE_CLASSIFICATION")
@DynamicUpdate(true)
public class ArchetypeTypeClassification {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classification")
	private Set<ArchetypeType> types;

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

	public Set<ArchetypeType> getTypes() {
		return types;
	}

	public <T> Map<T, ArchetypeType> getArchetypeTypeMap(
			Function<ArchetypeType, T> keyMapper) {
		return this.types.stream().collect(
				Collectors.toMap(keyMapper, type -> type));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArchetypeTypeClassification) {
			return ((ArchetypeTypeClassification) obj).getId() == this.id;
		}
		return false;
	}
}
