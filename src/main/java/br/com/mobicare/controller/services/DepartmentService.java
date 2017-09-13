package br.com.mobicare.controller.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.mobicare.model.dao.DaoFactory;
import br.com.mobicare.model.dao.PersistableDao;
import br.com.mobicare.model.entities.Department;

@Stateless
@Path("departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentService {
	
	@GET
	@Path("list")
	public List<Department> list() {
		
		final DaoFactory<Department> factory = new DaoFactory<Department>();
		final PersistableDao<Department> dao = factory.getDao(Department.class);
		
		return dao.list(Department.class);
	}

}
