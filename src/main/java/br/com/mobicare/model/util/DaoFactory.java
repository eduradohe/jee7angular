package br.com.mobicare.model.util;

import br.com.mobicare.model.dao.EmployeeDao;
import br.com.mobicare.model.dao.HibernateEmployeeDao;

public class DaoFactory {
	
	public static EmployeeDao getEmployeeDao() {
		return new HibernateEmployeeDao();
	}
}