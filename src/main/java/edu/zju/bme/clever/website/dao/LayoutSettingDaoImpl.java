package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.LayoutSetting;

@Repository("layoutSettingDao")
public class LayoutSettingDaoImpl extends
		AbstractGenericDao<LayoutSetting, Integer> implements LayoutSettingDao {

}
