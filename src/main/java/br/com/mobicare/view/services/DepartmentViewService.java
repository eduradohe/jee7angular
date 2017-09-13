package br.com.mobicare.view.services;

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

import br.com.mobicare.controller.services.DepartmentService;
import br.com.mobicare.model.entities.Department;
import br.com.mobicare.view.util.PaginatedListWrapper;

@Stateless
@Path("departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentViewService {

	@GET
	public PaginatedListWrapper<Department> listDepartments(@DefaultValue("1")
													    @QueryParam("page")
													    Integer page,
													    @DefaultValue("id")
													    @QueryParam("sortFields")
													    String sortFields,
													    @DefaultValue("asc")
													    @QueryParam("sortDirections")
													    String sortDirections) {
		
		final PaginatedListWrapper<Department> wrapper = new PaginatedListWrapper<>();
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
		
		wrapper.setList(new DepartmentService().listEmptyWithinRange(start, wrapper.getPageSize(), sortFields, sortDirections));
		wrapper.setTotalResults(new DepartmentService().countEmpty().intValue());
		
		return wrapper;
	}
}
