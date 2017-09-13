package br.com.mobicare.model.dao;

import br.com.mobicare.model.entities.Department;
import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.model.entities.Persistable;

public class DaoFactory {

	@SuppressWarnings({ "unchecked" })
	public static <P extends Persistable> Dao<P> getDao( Class<P> persistableClass ) {
		
		Dao<P> dao = null;
		
		if ( Employee.class.equals(persistableClass) ) {
			dao = (Dao<P>) new EmployeeDao();
		} else if ( Department.class.equals(persistableClass) ) {
			dao = (Dao<P>) new DepartmentDao();
		}
		
		return dao;
	}
}