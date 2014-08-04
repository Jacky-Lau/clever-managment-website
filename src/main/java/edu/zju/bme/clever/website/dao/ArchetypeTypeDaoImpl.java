package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeType;

@Repository("archetypeTypeDao")
public class ArchetypeTypeDaoImpl extends
		AbstractGenericDao<ArchetypeType, Integer> implements ArchetypeTypeDao {

}
