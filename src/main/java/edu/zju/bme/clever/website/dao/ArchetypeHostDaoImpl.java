package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeHost;

@Repository("archetypeHostDao")
public class ArchetypeHostDaoImpl extends
		AbstractGenericDao<ArchetypeHost, Integer> implements ArchetypeHostDao {

}
