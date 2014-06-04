package edu.zju.bme.clever.website.service;

import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.CommitSequence;

public interface ArchetypePersistanceService {
	
	public ArchetypeFile getArchetypeByName(String name);
	
	public void saveArchetypeFile(ArchetypeFile archetypeFile);
	
	public void updateArchetypeFile(ArchetypeFile archetypeFile);

	public CommitSequence getNewCommitSequence();

	public CommitSequence getCommitSequenceById(Integer id);
}
