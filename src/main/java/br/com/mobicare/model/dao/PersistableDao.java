package br.com.mobicare.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.com.mobicare.model.entities.Persistable;
import br.com.mobicare.model.util.PersistenceUtil;

public interface PersistableDao<P extends Persistable> {
	
	public default void save ( final P p ) {
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction();
			em.persist(p);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while creating object.", t);
			
		} finally {
			em.close();
		}
	}
	
	public default void update ( final P p ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction();
			em.merge(p);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {

			throw new RuntimeException("An error occurred while updating object.", t);
			
		} finally {
			em.close();
		}
	}
	
	
	public default void delete ( final P p ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction();
			em.remove(p);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while removing object.", t);
			
		} finally {
			em.close();
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
			
		} finally {
			em.close();
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
			
		} finally {
			em.close();
		}
		
		return count;
	}
	
	public P get ( final P p );
}
