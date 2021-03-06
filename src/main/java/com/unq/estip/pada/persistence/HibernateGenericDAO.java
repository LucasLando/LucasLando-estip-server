package com.unq.estip.pada.persistence;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Generic hibernate DAO
 * 
 * @param <T>
 */
public abstract class HibernateGenericDAO<T> extends HibernateDaoSupport
		implements GenericRepository<T>, Serializable {

	private static final long serialVersionUID = 5058950102420892922L;
	protected Class<T> persistentClass = this.getDomainClass();

	@Override
	public void delete(final T entity) {
	    throw new RuntimeException("Can't call delete, use update instead");
	}
	
	@Override
	public void deleteById(final Serializable id) {
	    throw new RuntimeException("Can't call delete, use update instead");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return this.getHibernateTemplate().find(
				"from " + this.persistentClass.getName() + " o where deleted = false or deleted = null"); // where not (deleted = true) 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T exampleObject) {
		return this.getHibernateTemplate().findByExample(exampleObject);
	}

	@Override
	public T findById(final Serializable id) {
	    List<T> l = this.getHibernateTemplate().find(
                "from " + this.persistentClass.getName() + " o where  id = " + id); //(deleted = false or deleted = null) and
	    if(l.size() > 0) return l.get(0);
	    return null;
	}

	protected abstract Class<T> getDomainClass();

	@Override
	public void save(final T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void update(final T entity) {
		this.getHibernateTemplate().update(entity);
	}

}