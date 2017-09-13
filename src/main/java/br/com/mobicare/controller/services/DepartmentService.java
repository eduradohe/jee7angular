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
import br.com.mobicare.model.entities.Department;

@Stateless
@Path("departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentService {
	
	final DaoFactory<Department> factory = new DaoFactory<Department>();
	final Dao<Department> dao = factory.getDao(Department.class);
	
	@GET
	@Path("list")
	public List<Department> list() {
		
		return dao.list(Department.class);
	}
    
	@GET
	@Path("countEmpty")
	public Long countEmpty() {
		
		return dao.countEmpty();
	}
	
	@GET
	@Path("listEmptyWithinRange")
	public List<Department> listEmptyWithinRange(@DefaultValue("1")
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
		
		return dao.listEmpty( start, pageSize, sortFields, sortDirections );
	}
	
	@GET
	@Path("{id}")
	public Department get(@PathParam("id") Long id) {
		
		final Department department = new Department();
		department.setId(id.intValue());
		
		return dao.get(department);
	}
	
	@POST
	public Department save(Department department) {
		
		if ( department.getId() == null ) {
			
			final Department departmentToSave = new Department();
			departmentToSave.setName(department.getName());
			departmentToSave.setBudget(department.getBudget());
			dao.save(departmentToSave);
			department = departmentToSave;
			
		} else {
			
			final Department departmentToUpdate = get(Long.valueOf(department.getId()));
			departmentToUpdate.setName(department.getName());
			departmentToUpdate.setBudget(department.getBudget());
			dao.update(departmentToUpdate);
			department = departmentToUpdate;
		}
		
		return department;
	}
	
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {

		dao.delete(Department.class, id.intValue());
	}
}
