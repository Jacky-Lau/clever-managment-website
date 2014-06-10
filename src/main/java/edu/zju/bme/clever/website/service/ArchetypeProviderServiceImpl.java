package edu.zju.bme.clever.website.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.acode.openehr.parser.ADLParser;
import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.entity.ArchetypeBriefInfo;
import edu.zju.bme.clever.website.entity.ArchetypeFile;

@Service("archetypeProviderService")
@Transactional
public class ArchetypeProviderServiceImpl implements ArchetypeProviderService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "archetypeFileDao")
	private ArchetypeFileDao archetypeFileDao;

	@Override
	public List<ArchetypeBriefInfo> getArchetypeList() {
		List<ArchetypeBriefInfo> archetypeList = new ArrayList<ArchetypeBriefInfo>();
		for (ArchetypeFile file : this.archetypeFileDao.selectAll()) {
			ArchetypeBriefInfo info = new ArchetypeBriefInfo();
			info.setId(file.getId());
			info.setName(file.getName());
			info.setPurpose(file.getPurpose());
			info.setKeywords(file.getKeywords());
			info.setUse(file.getUse());
			archetypeList.add(info);
		}
		return archetypeList;
	}

	@Override
	public String getArchetypeXmlByName(String name) {
		ArchetypeFile file = this.archetypeFileDao.findUniqueByProperty("name",
				name);
		return this.serializeArchetype(this.parseArchetype(file.getContent()));
	}

	@Override
	public String getArchetypeXmlById(Integer id) {
		ArchetypeFile file = this.archetypeFileDao.findById(id);
		return this.serializeArchetype(this.parseArchetype(file.getContent()));
	}
	
	@Override
	public String getArchetypeAdlByName(String name) {
		ArchetypeFile file = this.archetypeFileDao.findUniqueByProperty("name",
				name);
		return file.getContent();
	}

	@Override
	public String getArchetypeAdlById(Integer id) {
		ArchetypeFile file = this.archetypeFileDao.findById(id);
		return file.getContent();
	}

	protected Archetype parseArchetype(String archetype) {
		ADLParser parser = new ADLParser(archetype);
		try {
			return parser.parse();
		} catch (Exception ex) {
			this.logger.info("Parse archetype failed.", ex);
		}
		return null;
	}

	protected String serializeArchetype(Archetype archetype) {
		if (archetype == null) {
			throw new IllegalArgumentException("Archetype is null.");
		}
		XMLSerializer xmlSerializer = new XMLSerializer();
		try {
			return xmlSerializer.output(archetype);
		} catch (Exception ex) {
			this.logger.info("Serialize archetype failed.", ex);
		}
		return null;
	}

	@Override
	public Archetype getArchetypeByName(String name) {
		ArchetypeFile file = this.archetypeFileDao.findUniqueByProperty("name",
				name);
		return this.parseArchetype(file.getContent());
	}

	@Override
	public Archetype getArchetypeById(Integer id) {
		ArchetypeFile file = this.archetypeFileDao.findById(id);
		return this.parseArchetype(file.getContent());
	}
}
