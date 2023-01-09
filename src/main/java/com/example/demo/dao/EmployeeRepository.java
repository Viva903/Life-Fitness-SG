package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByDepartment(Department department);

	List<Employee> findByGender(String gender);

	@Query(value = "SELECT e.employee_id, e.email, e.first_name, e.gender, e.last_name, e.department_id FROM employee AS e INNER JOIN salary AS s ON e.employee_id = s.employee_id WHERE e.department_id = ?1 ORDER BY s.basic_salary LIMIT 1;", nativeQuery = true)
	Employee findByDepartmentIdWithMinimumBasicSalary(int departmentId);
}
