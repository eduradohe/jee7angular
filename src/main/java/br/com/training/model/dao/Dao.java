package br.com.training.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.com.training.model.entities.Persistable;
import br.com.training.model.util.PersistenceUtil;

public interface Dao<P extends Persistable> {
	
	public default void save ( final P p ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while creating object.", t);
		}
	}
	
	public default void update ( final P p ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.merge(p);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {

			throw new RuntimeException("An error occurred while updating object.", t);
		}
	}
	
	
	public default void delete ( final Class<P> clazz, final Integer id ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			
			final P p = em.find(clazz, id);
			em.remove(p);
			
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while removing object.", t);
		}
	}
	
	public default List<P> list ( final Class<P> clazz ) {
		
		return this.list( clazz, null, null, null, null );
	}
	
	public default List<P> list ( final Class<P> clazz, final Integer offset, final Integer limit, final String sortFields, final String sortDirections ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		List<P> ps = null;
		
		try {
			
			CriteriaQuery<P> query = em.getCriteriaBuilder().createQuery(clazz);
			Root<P> root = query.from(clazz);
			query.select(root);
			
			if ( offset == null || limit == null ) {
				ps = em.createQuery( query ).getResultList();
			} else {
				if ( sortFields != null && sortDirections != null ) {
					final List<Order> orderList = new ArrayList<Order>();
					
					final String[] fields = sortFields.contains(",") ? sortFields.split(",") : sortFields.split(";");
					final String[] directions = sortDirections.contains(",") ? sortDirections.split(",") : sortDirections.split(";");
					
					for ( int i = 0; i < fields.length; i++ ) {
						
						if ( "asc".equals(directions[i]) ) {
							orderList.add(em.getCriteriaBuilder().asc(root.get(fields[i])));
						} else {
							orderList.add(em.getCriteriaBuilder().desc(root.get(fields[i])));
						}
						
						query.orderBy(orderList);
					}
				}
				
				ps = em.createQuery( query ).setFirstResult(offset).setMaxResults(limit).getResultList();
			}
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while listing objects.", t);
		}
		
		return ps;
	}
	
	public default Long count ( final Class<P> clazz ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		Long count = null;
		
		try {
			
			CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
			Root<P> root = query.from(clazz);
			query.select(em.getCriteriaBuilder().count(root));
			
			count = em.createQuery( query ).getSingleResult();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while getting count.", t);
		}
		
		return count;
	}
	
	public P get ( final P p );
	public Long countEmpty ();
	public List<P> listEmpty ();
	public List<P> listEmpty ( final Integer offset, final Integer limit, final String sortFields, final String sortDirections );
}
