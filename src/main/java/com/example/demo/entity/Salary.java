package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "salary")
public class Salary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060339859820899892L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private int id;

	@Column(name = "basic_salary")
	@NotNull(message = "Employee Basic Salary is required")
    @Min(value = 1500, message = "Basic Salary must be greater than 1500")
    @Max(value = 100000, message = "Basic Salary must be lesser than 100000")
	private Double basicSalary;

	@Column(name = "allowance")
	@NotNull(message = "Employee Allowance is required")
    @Min(value = 0, message = "Basic Allowance must be greater than 0")
    @Max(value = 5000, message = "Basic Allowance must be lesser than 5000")
	private Double allowance;

	public Salary() {

	}

	public Salary(Double basicSalary, Double allowance) {
		this.basicSalary = basicSalary;
		this.allowance = allowance;
	}

	public Double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(Double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public Double getAllowance() {
		return allowance;
	}

	public void setAllowance(Double allowance) {
		this.allowance = allowance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Salary [id=" + id + ", basicSalary=" + basicSalary + ", allowance="
				+ allowance + "]";
	}

}
