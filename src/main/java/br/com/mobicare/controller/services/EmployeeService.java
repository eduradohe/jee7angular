package br.com.mobicare.controller.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.mobicare.model.dao.Dao;
import br.com.mobicare.model.dao.DaoFactory;
import br.com.mobicare.model.entities.Employee;

@Stateless
@Path("employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeService {
	
	private static final Dao<Employee> dao = DaoFactory.getDao(Employee.class);
	
	@GET
	@Path("list")
	public List<Employee> list() {
		
		return dao.list(Employee.class);
	}
	
	@GET
	@Path("count")
	public Long count() {
		
		return dao.count(Employee.class);
	}
	
	@GET
	@Path("listWithinRange")
	public List<Employee> listWithinRange(@DefaultValue("1")
															   @QueryParam("start")
															   Integer start,
															   @DefaultValue("5")
															   @QueryParam("pageSize")
															   Integer pageSize,
															   @DefaultValue("id")
															   @QueryParam("sortFields")
															   String sortFields,
															   @DefaultValue("asc")
															   @QueryParam("sortDirections")
															   String sortDirections) {
		
		return dao.list(Employee.class, start, pageSize, sortFields, sortDirections);
	}
	
	@GET
	@Path("{id}")
	public Employee get(@PathParam("id") Long id) {
		
		final Employee employee = new Employee();
		employee.setId(id.intValue());
		
		return dao.get(employee);
	}
	
	@POST
	public Employee save(Employee employee) {
		
		if ( employee.getId() == null ) {
			
			final Employee employeeToSave = new Employee();
			employeeToSave.setName(employee.getName());
			employeeToSave.setIncome(employee.getIncome());
			employeeToSave.setDepartment(employee.getDepartment());
			dao.save(employeeToSave);
			employee = employeeToSave;
			
		} else {
			
			final Employee employeeToUpdate = get(Long.valueOf(employee.getId()));
			employeeToUpdate.setName(employee.getName());
			employeeToUpdate.setIncome(employee.getIncome());
			employeeToUpdate.setDepartment(employee.getDepartment());
			dao.update(employeeToUpdate);
			employee = employeeToUpdate;
		}
		
		return employee;
	}
	
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {

		dao.delete(Employee.class, id.intValue());
	}
}
