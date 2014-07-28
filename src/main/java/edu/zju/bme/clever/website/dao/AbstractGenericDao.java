package edu.zju.bme.clever.website.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.OneToMany;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * @author Liu
 * 
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractGenericDao<T, ID extends Serializable> implements
		GenericDao<T, ID> {
	private Class<T> clazz;
	private final List<Field> oneToManyFields = new ArrayList<Field>();

	/**
	 * 通过构造方法指定DAO的具体实现类
	 */
	public AbstractGenericDao() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		// System.out.println("DAO的真实实现类是：" + this.clazz.getName());

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(OneToMany.class)) {
				oneToManyFields.add(field);
			}
		}
	}

	/**
	 * 向DAO层注入SessionFactory
	 */
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * 获取当前工作的Session
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) {
		this.getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		this.getSession().update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(ID id) {
		this.getSession().delete(this.findById(id));
	}

	@Override
	public void delete(T entity) {
		this.getSession().delete(entity);
	}

	@Override
	public T findById(ID id) {
		return (T) this.getSession().get(this.clazz, id);
	}

	@Override
	public List findByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		return query.list();
	}

	@Override
	public List findByHQL(String hql, Map<String, ?> map) {
		Query query = this.getSession().createQuery(hql);
		query.setProperties(map);
		return query.list();
	}

	@Override
	public List<T> selectAll() {
		return this.selectAll(false);
	}

	@Override
	public List<T> selectAll(boolean fetchAll) {
		if (fetchAll) {
			Criteria criteria = this.getSession().createCriteria(clazz);
			for (Field field : this.oneToManyFields) {
				criteria.setFetchMode(field.getName(), FetchMode.JOIN);
			}
			return criteria.list();
		} else {
			String hql = "from " + clazz.getName();
			return this.findByHQL(hql);
		}
	}

	@Override
	public List<T> selectAll(String[] fetchFields) {
		Criteria criteria = this.getSession().createCriteria(clazz);
		for (String fetchFiled : fetchFields) {
			criteria.setFetchMode(fetchFiled, FetchMode.JOIN);
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public T findUniqueByProperty(String propertyName, Object value) {
		Criteria criteria = this.getSession().createCriteria(clazz)
				.add(Restrictions.eq(propertyName, value)); // 增加属性相等约束
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (T) criteria.uniqueResult();
	}
}
