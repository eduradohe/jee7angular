package br.com.mobicare.model.entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class Department {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	private Double budget;
	
	private List<Employee> employees;
	
	public Department() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Department)) return false;
		
		Department other = (Department) obj;
		
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
}
