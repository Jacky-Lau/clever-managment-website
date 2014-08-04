package edu.zju.bme.clever.website.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
@Transactional
public class AbstractGenericDaoTest{

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Test
	public void findByHQL() {
		String hql = "from ArchetypeFile as af, ArchetypeNode as an where af.id = an.id";
		Query query = this.getSession().createQuery(hql);
		List<?> l = query.list();
		System.out.println(l.size());
		l.forEach(o -> {
			System.out.println(o.getClass().getSimpleName());
		});
	}

}
