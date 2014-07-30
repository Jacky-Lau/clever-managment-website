package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeHostLayoutSetting;

@Repository("archetypeHostLayoutSettingDao")
public class ArchetypeHostLayoutSettingDaoImpl extends
		AbstractGenericDao<ArchetypeHostLayoutSetting, Integer> implements
		ArchetypeHostLayoutSettingDao {

}
