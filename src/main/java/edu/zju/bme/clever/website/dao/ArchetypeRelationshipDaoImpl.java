package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeRelationship;

@Repository("archetypeRelationshipDao")
public class ArchetypeRelationshipDaoImpl extends
		GenericDaoImpl<ArchetypeRelationship, Integer> implements
		ArchetypeRelationshipDao {

}
