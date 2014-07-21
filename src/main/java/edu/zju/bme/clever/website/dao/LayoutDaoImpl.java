package edu.zju.bme.clever.website.dao;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.Layout;

@Repository("layoutDao")
public class LayoutDaoImpl extends GenericDaoImpl<Layout, Integer> implements
		LayoutDao {
	@Override
	public void save(Layout entity) {
		entity.setModifyTime(Calendar.getInstance());
		super.save(entity);
	}

	@Override
	public void update(Layout entity) {
		entity.setModifyTime(Calendar.getInstance());
		super.update(entity);
	}

	@Override
	public void saveOrUpdate(Layout entity) {
		entity.setModifyTime(Calendar.getInstance());
		super.saveOrUpdate(entity);
	}
}
