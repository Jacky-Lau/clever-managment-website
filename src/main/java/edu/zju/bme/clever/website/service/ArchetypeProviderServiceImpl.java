package edu.zju.bme.clever.website.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.zju.bme.clever.website.dao.ArchetypeFileDao;

public class ArchetypeProviderServiceImpl implements ArchetypeProviderService{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="archetypeFileDao")
	private ArchetypeFileDao archetypeFileDao;

	@Override
	public Set<String> getAllArechetypeNames() {
		Set<String> archetypeNames = new HashSet<String>();
		return archetypeNames;
	}
	
}
