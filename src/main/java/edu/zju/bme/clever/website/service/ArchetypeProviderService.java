package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Set;

import org.openehr.am.archetype.Archetype;

import edu.zju.bme.clever.website.model.entity.ArchetypeTypeClassification;
import edu.zju.bme.clever.website.view.entity.ArchetypeBriefInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeInfo;
import edu.zju.bme.clever.website.view.entity.ClassificationBriefInfo;

public interface ArchetypeProviderService {

	public String getArchetypeXmlByName(String name);

	public String getArchetypeXmlById(Integer id);

	public Archetype getArchetypeByName(String name);

	public Archetype getArchetypeById(Integer id);

	public String getArchetypeAdlByName(String name);

	public String getArchetypeAdlById(Integer id);

	public List<String> getAllArchetypeAdls();

	public List<String> getAllLatestVersionArchetypeAdls();

	public List<String> getAllArchetypeIds();

	public List<String> getAllLatestVersionArchetypeIds();

	public List<String> getDeployedArchetypeIds();

	public ArchetypeBriefInfo getArchetypeBriefInfoByTypeId(Integer typeId);

	public ArchetypeBriefInfo getArchetypeBriefInfo();

	public List<String> getAllPublishedLatestVersionArchetypeAdls();

	public List<ArchetypeHostInfo> getAllArchetypeHosts();

}
