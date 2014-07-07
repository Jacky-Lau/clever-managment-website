package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeNodeChangeLog;

@Repository("archetypeNodeChangeLogDao")
public class ArchetypeNodeChangeLogDaoImpl extends
		GenericDaoImpl<ArchetypeNodeChangeLog, Integer> implements
		ArchetypeNodeChangeLogDao {

}
