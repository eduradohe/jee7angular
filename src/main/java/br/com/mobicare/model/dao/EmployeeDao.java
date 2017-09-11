package br.com.mobicare.model.dao;

import java.util.List;

import br.com.mobicare.model.entities.Employee;

public interface EmployeeDao {
	public void save ( final Employee employee );
	public void update ( final Employee employee );
	public void delete ( final Employee employee );
	public List<Employee> list ();
}