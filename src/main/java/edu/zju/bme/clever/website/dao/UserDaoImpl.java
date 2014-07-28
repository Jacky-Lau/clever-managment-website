package edu.zju.bme.clever.website.dao;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.User;

@Repository(value = "userDao")
public class UserDaoImpl extends AbstractGenericDao<User,Integer> implements UserDao{
	@Override
	public void save(User user){
		user.setUpdateTime(Calendar.getInstance());
		super.save(user);
	}
	
	@Override
	public void update(User user){
		user.setUpdateTime(Calendar.getInstance());
		super.update(user);
	}		
	
	@Override
	public void saveOrUpdate(User user){
		user.setUpdateTime(Calendar.getInstance());
		super.saveOrUpdate(user);
	}
}
