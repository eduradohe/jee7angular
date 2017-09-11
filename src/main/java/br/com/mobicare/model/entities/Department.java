package br.com.mobicare.model.entities;

import javax.persistence.*;

@Entity
@Table
public class Department {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
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
}
