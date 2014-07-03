package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.entity.ArchetypeHost;

@Repository("archetypeHostDao")
public class ArchetypeHostDaoImpl extends
		GenericDaoImpl<ArchetypeHost, Integer> implements ArchetypeHostDao {

}
