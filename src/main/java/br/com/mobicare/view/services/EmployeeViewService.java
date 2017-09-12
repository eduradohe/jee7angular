package br.com.mobicare.view.services;

import java.util.HashMap;
import java.util.Map;

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
		
		final Integer start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
		
		final Map<String,Object[]> parameters = new HashMap<String,Object[]>();
		parameters.put("start", new Object[] { start });
		parameters.put("pageSize", new Object[] { wrapper.getPageSize() });
		parameters.put("sortFields", new Object[] { wrapper.getSortFields() });
		parameters.put("sortDirections", new Object[] { wrapper.getSortDirections() });
		
		wrapper.setList(ApiUtils.callService("api/employees/listWithinRange", parameters));
		wrapper.setTotalResults(ApiUtils.callService("api/employees/count"));
		
		return wrapper;
	}
}
