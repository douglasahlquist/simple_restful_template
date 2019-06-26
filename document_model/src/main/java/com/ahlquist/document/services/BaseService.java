package com.ahlquist.document.services;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.ahlquist.document.model.Document;

//import com.ahlquist.document.builder.IEntityBuilder;

/**
 * Base Services
 * 
 * @author Douglas Ahlquist
 *
 * @param <R> - The Repository Class
 * @param <T> - The Entity Type
 * @param <K> - The Key into the database for this Entity Service
 */

public abstract class BaseService<R extends CrudRepository<T, K>, T, K> implements IBaseService<T, K> {

	final static Logger logger = Logger.getLogger(BaseService.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	@Qualifier("transactionManager")
	PlatformTransactionManager transactionManager;

	protected final String ID_PRESENT_ERROR = "Error Cannot create instance of %s with id=%d present";

	public BaseService(final R repository) {
		this.repository = repository;

	}

	// The Repository used in accessing the database
	private R repository;

	public R getRepository() {
		return repository;
	}

	public Optional<T> findById(K id) {
		return repository.findById(id);
	}

	// @SuppressWarnings("unchecked")
	public T create(T t) throws IllegalArgumentException {
		logger.info("creating entity: " + t.toString());
		Session s = sessionFactory.openSession();
		Transaction tr = s.beginTransaction();
		s.save(t);
		tr.commit();
		s.close();
		return t;
	}

	public void delete(T t) throws IllegalArgumentException {
		Session s = sessionFactory.openSession();
		Transaction tr = s.beginTransaction();
		s.delete(t);
		tr.commit();
		s.close();
	}

	public void deleteById(T l) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public T save(T t) {
		Session s = sessionFactory.openSession();
		Transaction tr = s.beginTransaction();
		t = (T) s.save(t);
		tr.commit();
		s.close();
		return t;
	}

	@SuppressWarnings("unchecked")
	public T merge(T t) {
		Session s = sessionFactory.openSession();
		Transaction tr = s.beginTransaction();
		t = (T) s.merge(t);
		tr.commit();
		s.close();
		return t;
	}

}
