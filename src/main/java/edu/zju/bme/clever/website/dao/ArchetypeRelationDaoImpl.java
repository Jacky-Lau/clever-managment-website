package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.entity.ArchetypeRelation;

@Repository("archetypeRelationDao")
public class ArchetypeRelationDaoImpl extends
		GenericDaoImpl<ArchetypeRelation, Integer> implements
		ArchetypeRelationDao {

}
