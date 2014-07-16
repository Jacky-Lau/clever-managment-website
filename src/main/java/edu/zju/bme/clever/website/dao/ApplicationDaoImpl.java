package edu.zju.bme.clever.website.dao;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.Application;

@Repository("applicationDao")
public class ApplicationDaoImpl extends GenericDaoImpl<Application, Integer>
		implements ApplicationDao {

	@Override
	public void save(Application entity) {
		entity.setModifyTime(Calendar.getInstance());
		super.save(entity);
	}
	
	@Override
	public void update(Application entity){
		entity.setModifyTime(Calendar.getInstance());
		super.update(entity);
	}
	
	@Override
	public void saveOrUpdate(Application entity){
		entity.setModifyTime(Calendar.getInstance());
		super.saveOrUpdate(entity);
	}
}
