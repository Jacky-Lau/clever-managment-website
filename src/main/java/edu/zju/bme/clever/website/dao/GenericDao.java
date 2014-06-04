package edu.zju.bme.clever.website.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, ID extends Serializable> {

	public void save(T entity);

	public void update(T entity);

	public void saveOrUpdate(T entity);

	public void delete(ID id);

	public void delete(T entity);

	public T findById(ID id);

	public List findByHQL(String hql, Object... params);

	public List findByHQL(String hql, Map<String, ?> map);
	
	public List<T> selectAll();

	public T findUniqueByProperty(String propertyName, Object value);
}
