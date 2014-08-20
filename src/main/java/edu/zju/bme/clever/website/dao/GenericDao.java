package edu.zju.bme.clever.website.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes" })
public interface GenericDao<T, ID extends Serializable> {
	
	public Long getTotalCount();

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

	public List<T> selectAll(String[] fetchFields);

	public List<T> selectAll(boolean fetchAll);

	public List<T> findByProperty(Object... params);
}
