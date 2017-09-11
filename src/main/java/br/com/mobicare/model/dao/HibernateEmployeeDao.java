package br.com.mobicare.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.model.util.PersistenceUtil;

public class HibernateEmployeeDao implements EmployeeDao {

	public void save( final Employee employee ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction();
			em.persist(employee);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while creating object.", t);
			
		} finally {
			em.close();
		}
	}
	
	public void update( final Employee employee ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction();
			em.merge(employee);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {

			throw new RuntimeException("An error occurred while updating object.", t);
			
		} finally {
			em.close();
		}
	}

	public void delete( final Employee employee ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		try {
			em.getTransaction();
			em.remove(employee);
			em.getTransaction().commit();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while removing object.", t);
			
		} finally {
			em.close();
		}
	}

	public List<Employee> list() {

		final EntityManager em = PersistenceUtil.getEntityManager();
		List<Employee> employees = null;
		
		try {
			
			CriteriaQuery<Employee> query = em.getCriteriaBuilder().createQuery(Employee.class);
			Root<Employee> root = query.from(Employee.class);
			query.select(root);
			
			employees = em.createQuery( query ).getResultList();
			
		} catch ( final Throwable t ) {
			
			throw new RuntimeException("An error occurred while listing objects.", t);
			
		} finally {
			em.close();
		}
		
		return employees;
	}
}