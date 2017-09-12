package br.com.mobicare.controller.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.mobicare.model.dao.DaoFactory;
import br.com.mobicare.model.dao.PersistableDao;
import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.view.services.MobicareApiResponse;
import br.com.mobicare.view.util.ApiUtils;

@Stateless
@Path("employees")
public class EmployeeService {
	
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public MobicareApiResponse<List<Employee>> list() {
		
		final DaoFactory<Employee> factory = new DaoFactory<Employee>();
		final PersistableDao<Employee> dao = factory.getDao(Employee.class);
		
		return ApiUtils.wrappResponse(dao.list(Employee.class));
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	public MobicareApiResponse<Long> count() {
		
		final DaoFactory<Employee> factory = new DaoFactory<Employee>();
		final PersistableDao<Employee> dao = factory.getDao(Employee.class);
		
		return ApiUtils.wrappResponse(dao.count(Employee.class));
	}
	
	@GET
	@Path("listWithinRange")
	@Produces(MediaType.APPLICATION_JSON)
	public MobicareApiResponse<List<Employee>> listWithinRange(@DefaultValue("1")
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
		
		final DaoFactory<Employee> factory = new DaoFactory<Employee>();
		final PersistableDao<Employee> dao = factory.getDao(Employee.class);
		
		return ApiUtils.wrappResponse(dao.list(Employee.class, start, pageSize, sortFields, sortDirections));
	}
}
