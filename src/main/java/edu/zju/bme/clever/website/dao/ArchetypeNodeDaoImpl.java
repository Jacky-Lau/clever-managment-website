package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeNode;

@Repository("archetypeNodeDao")
public class ArchetypeNodeDaoImpl extends
		GenericDaoImpl<ArchetypeNode, Integer> implements ArchetypeNodeDao {

}
