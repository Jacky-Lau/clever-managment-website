package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeTypeClassification;

@Repository("archetypeTypeClassificationDao")
public class ArchetypeTypeClassificationDaoImpl extends
		AbstractGenericDao<ArchetypeTypeClassification, Integer> implements
		ArchetypeTypeClassificationDao {

}
