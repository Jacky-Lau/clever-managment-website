package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Map;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.model.entity.FileProcessResult;

public interface ArchetypeValidationService {

	public boolean validateFeasibility(List<String> archetypes);

	public void validateConsistency(Map<String, Archetype> archetypes, Map<String,FileProcessResult> results);
}
