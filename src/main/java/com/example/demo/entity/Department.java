package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "department")
public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8896611225781418178L;

	@Id
	@Column(name = "department_id")
	@NotNull(message = "Department id is required")
	private int departmentId;

	@Size(min = 2, message = "department name should have at least 2 characters")
	private String name;

	public Department() {

	}

	public Department(int department_id, String name) {
		this.departmentId = department_id;
		this.name = name;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Department [department_id=" + departmentId + ", name=" + name + "]";
	}

}
