package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Map;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.ArchetypeRelation;

public interface ArchetypeExtractService {

	public ArchetypeFile extractArchetype(Archetype archetype);

	public List<ArchetypeRelation> extractArchetypeRelations(
			Map<String, Archetype> archetypes,
			Map<String, ArchetypeFile> archetypeFiles);

}