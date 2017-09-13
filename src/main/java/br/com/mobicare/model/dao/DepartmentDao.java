package br.com.mobicare.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.mobicare.model.entities.Department;
import br.com.mobicare.model.util.PersistenceUtil;

@SuppressWarnings("unchecked")
public class DepartmentDao implements Dao<Department> {

	@Override
	public Department get( final Department department ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
	    final String queryString = "SELECT d FROM Department d WHERE d.id = :id";
		final Query query = em.createQuery(queryString);
		
		query.setParameter("id", department.getId());
		
		return (Department) query.getSingleResult();
	}
	
	public List<Department> listEmpty( final Integer offset, final Integer limit, final String sortFields, final String sortDirections ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		String queryString = ""
				+ "SELECT d "
				+ "FROM Department as d "
				+ "WHERE d.employees IS EMPTY";
		
		if ( sortFields != null && sortDirections != null ) {
			queryString += " ORDER BY d." + sortFields + " " + sortDirections;
		}
		
		final Query query = em.createQuery(queryString);
		
		if ( offset == null || limit == null ) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		
		return (List<Department>) query.getResultList();
	}
	
	public List<Department> listEmpty () {
		return listEmpty(null, null, null, null);
	}

	@Override
	public Long countEmpty() {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
		String queryString = ""
				+ "SELECT COUNT(d.id) "
				+ "FROM Department as d "
				+ "WHERE d.employees IS EMPTY";
		
		final Query query = em.createQuery(queryString);
		
		return (Long) query.getSingleResult();
	}
}
