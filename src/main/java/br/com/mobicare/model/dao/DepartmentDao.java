package br.com.mobicare.model.dao;

import javax.persistence.Query;

import br.com.mobicare.model.entities.Department;
import br.com.mobicare.model.util.PersistenceUtil;

public class DepartmentDao implements PersistableDao<Department> {

	@Override
	public Department get( final Department department ) {
	    final String queryString = "SELECT d FROM Department d WHERE d.id = :id";
		final Query query = PersistenceUtil.getEntityManager().createQuery(queryString);
		
		query.setParameter("id", department.getId());
		
		return (Department) query.getSingleResult();
	}
}
