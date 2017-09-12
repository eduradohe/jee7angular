package br.com.mobicare.model.dao;

import javax.persistence.Query;

import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.model.util.PersistenceUtil;

public class EmployeeDao implements PersistableDao<Employee> {

	@Override
	public Employee get( final Employee employee ) {
	    final String queryString = "SELECT e FROM Employee e WHERE e.id = :id";
		final Query query = PersistenceUtil.getEntityManager().createQuery(queryString);
		
		query.setParameter("id", employee.getId());
		
		return (Employee) query.getSingleResult();
	}
}
