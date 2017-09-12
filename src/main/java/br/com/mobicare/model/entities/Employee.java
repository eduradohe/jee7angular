package br.com.mobicare.model.entities;

import javax.persistence.*;

@Entity
@Table
public class Employee implements Persistable {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private Double income;
	
	@ManyToOne
	private Department department;
	
	public Employee() {
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
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
		if (getClass() != obj.getClass()) return false;
		
		Employee other = (Employee) obj;
		
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
}
