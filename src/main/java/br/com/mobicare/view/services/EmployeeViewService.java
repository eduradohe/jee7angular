package br.com.mobicare.view.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.view.util.ApiUtils;
import br.com.mobicare.view.util.PaginatedListWrapper;

@Stateless
@Path("view/employees")
public class EmployeeViewService {

	@GET
	@Path("listPaginated")
	@Produces(MediaType.APPLICATION_JSON)
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
		
		final List<Employee> employees = ApiUtils.callService("api/employees/list");
		final Integer total = ApiUtils.callService("api/employees/count");
		
		wrapper.setList(employees);
		wrapper.setTotalResults(total.intValue());
		
		return wrapper;
	}
}
