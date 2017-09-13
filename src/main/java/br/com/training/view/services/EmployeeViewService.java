package br.com.training.view.services;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.training.controller.services.EmployeeService;
import br.com.training.model.entities.Employee;
import br.com.training.view.util.PaginatedListWrapper;

@Stateless
@Path("employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeViewService {
	
	@GET
	public PaginatedListWrapper<Employee> listEmployees(@DefaultValue("1")
													    @QueryParam("page")
													    Integer page,
													    @DefaultValue("id")
													    @QueryParam("sortFields")
													    String sortFields,
													    @DefaultValue("asc")
													    @QueryParam("sortDirections")
													    String sortDirections) {
		
		final PaginatedListWrapper<Employee> wrapper = new PaginatedListWrapper<>();
		wrapper.setCurrentPage(page);
		wrapper.setSortFields(sortFields);
		wrapper.setSortDirections(sortDirections);
		wrapper.setPageSize(5);
		
		final Integer start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
		
		final Map<String,Object[]> parameters = new HashMap<String,Object[]>();
		parameters.put("start", new Object[] { start });
		parameters.put("pageSize", new Object[] { wrapper.getPageSize() });
		parameters.put("sortFields", new Object[] { wrapper.getSortFields() });
		parameters.put("sortDirections", new Object[] { wrapper.getSortDirections() });
		
		wrapper.setList(new EmployeeService().listWithinRange(start, wrapper.getPageSize(), sortFields, sortDirections));
		wrapper.setTotalResults(new EmployeeService().count().intValue());
		
		return wrapper;
	}
}
