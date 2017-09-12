package br.com.mobicare.view.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import br.com.mobicare.model.entities.Employee;
import br.com.mobicare.view.util.PaginatedListWrapper;
import br.com.mobicare.view.util.ViewUtils;

@Stateless
@Path("view")
public class EmployeeViewService {

	@GET
	@Path("employees")
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
		
		final Client client = ClientBuilder.newClient();
		final WebTarget target = client.target("http://localhost:8080/java-pleno-eduardo-turella/api/services/employees");
		final JsonArray response = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
		
		final List<Employee> employees = ViewUtils.parseJsonToList(response);
		
		wrapper.setList(employees);
		wrapper.setTotalResults(employees.size());
		
		return wrapper;
	}
}
