package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Map;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.entity.FileProcessResult;

public interface ArchetypeValidationService {
	
	public boolean validate(String archetype);

	public boolean validate(List<String> archetype);

	public void validateArchetypes(Map<Archetype, FileProcessResult> archetypes);
}
