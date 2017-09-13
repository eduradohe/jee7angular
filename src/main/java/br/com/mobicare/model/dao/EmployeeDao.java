package br.com.mobicare.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.model.util.PersistenceUtil;

public class EmployeeDao implements Dao<Employee> {

	@Override
	public Employee get( final Employee employee ) {
		
		final EntityManager em = PersistenceUtil.getEntityManager();
		
	    final String queryString = "SELECT e FROM Employee e WHERE e.id = :id";
		final Query query = em.createQuery(queryString);
		
		query.setParameter("id", employee.getId());
		
		return (Employee) query.getSingleResult();
	}

	@Override
	public Long countEmpty() {
		// not used
		return null;
	}

	@Override
	public List<Employee> listEmpty() {
		// not used
		return null;
	}

	@Override
	public List<Employee> listEmpty(Integer offset, Integer limit, String sortFields, String sortDirections) {
		// not used
		return null;
	}
}
