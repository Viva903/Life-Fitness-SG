package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private int id;

	@NotNull(message = "First Name of Employee is required.")
	@Column(name = "first_name")
	@NotEmpty(message = "First Name of Employee must not be empty")
	@Size(min = 2, message = "first name should have at least 2 characters")
	private String firstName;

	@NotNull(message = "Last Name of Employee is required.")
	@Column(name = "last_name")
	@NotEmpty(message = "Last Name of Employee must not be empty")
	@Size(min = 2, message = "last name should have at least 2 characters")
	private String lastName;

	@Email(message = "Valid email is required")
	@NotNull(message = "Employee Email is required")
	private String email;

	@NotNull(message = "Employee gender is required")
	@NotEmpty(message = "Gender of Employee must not be empty")
	private String gender;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "department_id")
	private Department department;

	public Employee() {

	}

	public Employee(String firstName, String lastName, String email, String gender,
			Department department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", gender=" + gender + "]";
	}

}
