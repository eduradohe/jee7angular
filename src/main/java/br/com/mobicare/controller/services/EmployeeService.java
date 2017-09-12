package br.com.mobicare.controller.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import br.com.mobicare.model.dao.DaoFactory;
import br.com.mobicare.model.dao.PersistableDao;
import br.com.mobicare.model.entities.Employee;

@Stateless
@ApplicationPath("/services")
@Path("employees")
public class EmployeeService extends Application {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> listEmployees() {
		
		final DaoFactory<Employee> factory = new DaoFactory<Employee>();
		final PersistableDao<Employee> dao = factory.getDao(Employee.class);
		
		return dao.list(Employee.class);
	}
}
