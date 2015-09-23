package ar.fiuba.fallasII.motorInferencia.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class GenericDao implements IGenericDao {

	@Resource(name = "hibernateSessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public <T> T save(T o) {
		return (T) sessionFactory.getCurrentSession().save(o);
	}

	public void delete(Object object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type, Long id) {
		return (T) sessionFactory.getCurrentSession().get(type, id);
	}

	public <T> void saveOrUpdate(T o) {
		sessionFactory.getCurrentSession().saveOrUpdate(o);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> type) {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(type);
		return crit.list();
	}
}
