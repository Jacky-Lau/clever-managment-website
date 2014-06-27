package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Map;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.ArchetypeRelation;
import edu.zju.bme.clever.website.entity.FileProcessResult;

public interface ArchetypeExtractService {

	public ArchetypeFile extractArchetype(Archetype archetype);

	public ArchetypeFile extractArchetype(Archetype archetype,
			FileProcessResult result);

	public List<ArchetypeRelation> extractArchetypeRelations(
			Map<String, Archetype> archetypes,
			Map<String, ArchetypeFile> archetypeFiles);

	public List<ArchetypeRelation> extractArchetypeRelations(
			Map<String, Archetype> archetypes,
			Map<String, ArchetypeFile> archetypeFiles,
			Map<String, FileProcessResult> results);

}