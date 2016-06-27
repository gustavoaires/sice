package br.ufc.sice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDAO<T> {

	@Autowired
	protected SessionFactory factory;

	protected Session session;

	protected Transaction tx;

	protected void begin(){
		session = factory.openSession();
		tx = session.beginTransaction();
	}

	protected void end(){
		tx.commit();
	}

	protected T load(long id, Class c){
		T result = null;
		begin();
		result = (T) session.get(c, id);
		end();

		return result;
	}

	public abstract T load(long id);

	public Long save(T entity){
		begin();
		Long id = (Long) session.save(entity);
		end();

		return id;
	}

	public List loadAll(Class c){
		begin();
		List result = (List) session.createCriteria(c).list();
		end();

		return result;
	}

	public abstract List loadAll();

}
