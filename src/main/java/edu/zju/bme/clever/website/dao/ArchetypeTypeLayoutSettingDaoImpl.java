package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeTypeLayoutSetting;

@Repository("archetypeTypeLayoutSettingDao")
public class ArchetypeTypeLayoutSettingDaoImpl extends
		AbstractGenericDao<ArchetypeTypeLayoutSetting, Integer> implements
		ArchetypeTypeLayoutSettingDao {

}
