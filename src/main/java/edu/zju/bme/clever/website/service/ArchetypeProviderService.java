package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Set;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.model.entity.ArchetypeBriefInfo;

public interface ArchetypeProviderService {

	public List<ArchetypeBriefInfo> getArchetypeList();

	public String getArchetypeXmlByName(String name);

	public String getArchetypeXmlById(Integer id);
	
	public Archetype getArchetypeByName(String name);

	public Archetype getArchetypeById(Integer id);
	
	public String getArchetypeAdlByName(String name);

	public String getArchetypeAdlById(Integer id);
	
	public List<String> getAllArchetypeAdls();
}
