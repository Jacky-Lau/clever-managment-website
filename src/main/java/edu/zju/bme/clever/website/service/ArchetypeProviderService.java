package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Set;

import edu.zju.bme.clever.website.entity.ArchetypeBriefInfo;

public interface ArchetypeProviderService {

	public List<ArchetypeBriefInfo> getArchetypeList();

	public String getArchetypeXmlByName(String name);

	public String getArchetypeXmlById(Integer id);
}
