package edu.zju.bme.clever.website.service;

import java.util.List;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.exception.ArchetypePersistenceException;
import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.CommitSequence;

public interface ArchetypePersistanceService {

	public void saveArchetypes(List<Archetype> archetypes, String submitter)
			throws ArchetypePersistenceException;
}
