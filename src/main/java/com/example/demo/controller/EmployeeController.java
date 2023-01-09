package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.SalaryRepository;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Salary;
import com.example.demo.exception.ResourceNotFoundException;

// TODO: can seperate the business logic to Service class for improvement.
@RestController
@Validated
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeRepository empRepository;
	@Autowired
	private DepartmentRepository deptRepository;
	@Autowired
	private SalaryRepository salaryRepository;

	@PostMapping
	public ResponseEntity<String> saveEmployee(
			@RequestBody @Valid Employee employee) {

		Department dept = deptRepository
				.findById(employee.getDepartment().getDepartmentId()).orElse(null);

		if (dept != null) {
			System.out.println("Existing Department id");
			employee.setDepartment(dept);
		}
		empRepository.save(employee);
		return ResponseEntity.ok("Data Saved");
	}

	@PostMapping("/income")
	public ResponseEntity<String> saveIncome(@RequestBody @Valid Salary salary) {

		salaryRepository.save(salary);
		return ResponseEntity.ok("Salary Saved");
	}

	@GetMapping
	public List<Employee> getAll() {
		return empRepository.findAll();
	}

	@GetMapping("/dept/{department_id}")
	public List<Employee> getEmployeesByDept(@PathVariable @Valid int department_id) {
		Department dept = deptRepository.findById(department_id).orElse(null);

		if (dept == null) {
			throw new ResourceNotFoundException(
					"Employee id not found in department id: " + department_id);
		}

		return empRepository.findByDepartment(dept);
	}

	@GetMapping("/{emp_id}")
	public Employee getEmployeesById(@PathVariable @Positive @Valid int emp_id) {

		Optional<Employee> foundEmployee = empRepository.findById(emp_id);
		if (!foundEmployee.isPresent()) {
			throw new ResourceNotFoundException("Employee id not found : " + emp_id);
		}

		return foundEmployee.get();
	}

	@GetMapping("/gender/{gender}")
	public List<Employee> getEmployeesByGender(@PathVariable @Pattern(regexp = "[A-Za-z]+") @Valid String gender) {

		return empRepository.findByGender(gender);
	}

	@PutMapping("/{emp_id}")
	public ResponseEntity<String> editEmployee(@PathVariable @Positive int emp_id,
			@RequestBody @Valid Employee employee) {

		Optional<Employee> foundOptionalEmployee = empRepository.findById(emp_id);

		if (!foundOptionalEmployee.isPresent()) {
			throw new ResourceNotFoundException("Employee id not found : " + emp_id);
		}
		Employee foundEmployee = foundOptionalEmployee.get();

		if (employee.getDepartment() != null) {
			Department dept = deptRepository
					.findById(employee.getDepartment().getDepartmentId())
					.orElse(null);
			if (dept != null) {
				foundEmployee.setDepartment(dept);
			}
		}

		foundEmployee.setEmail(employee.getEmail());
		foundEmployee.setFirstName(employee.getFirstName());
		foundEmployee.setLastName(employee.getLastName());
		foundEmployee.setGender(employee.getGender());

		empRepository.save(foundEmployee);
		return ResponseEntity.ok("Employee Updated .. ");
	}

	@DeleteMapping("/{emp_id}")
	public String deleteEmployee(@PathVariable @Positive @Valid int emp_id) {
		Optional<Employee> employee = empRepository.findById(emp_id);

		if (!employee.isPresent()) {
			throw new ResourceNotFoundException("Employee id not found : " + emp_id);
		}

		empRepository.deleteById(emp_id);
		return "Deleted employee id - " + emp_id;
	}

	@GetMapping("/minbasic/{department_id}")
	public Employee getEmployeeByDeptWithMinSalary(
			@PathVariable @Positive @Valid int department_id) {
		Department dept = deptRepository.findById(department_id).orElse(null);

		if (dept == null) {
			throw new ResourceNotFoundException(
					"Employee id not found in department id: " + department_id);
		}

		Employee employee = empRepository
				.findByDepartmentIdWithMinimumBasicSalary(department_id);
		return employee;
	}

	@GetMapping("/tax/{emp_id}")
	public ResponseEntity<Map<String, Double>> getEmployeeYearlyMaxTax(
			@PathVariable @Positive @Valid int emp_id) {

		Map<String, Double> SalaryTaxInfo = new HashMap<>();

		Salary salary = salaryRepository.findById(emp_id)
				.orElseThrow(() -> new ResourceNotFoundException("Salary information not found, please insert"));
		Double taxableAmout = (salary.getBasicSalary() + salary.getAllowance()) * 12;
		SalaryTaxInfo.put("Annual Salary", taxableAmout);

		Double maximumTax = 0.0;
		int counter = 0;
		while (taxableAmout > 0.0 && counter < 4) {

			if (counter == 0) {
				taxableAmout -= 15000.0;
			} else if (counter == 1) {
				maximumTax += 200.0;
				taxableAmout -= 15000.0;
			} else if (counter == 2) {
				maximumTax += 0.0475 * Math.min(45000.0, taxableAmout);
				taxableAmout -= 45000.0;
			} else if (counter == 3) {
				maximumTax += 0.07 * taxableAmout;
			}
			counter++;
		}

		SalaryTaxInfo.put("Maximum Tax", maximumTax);
		return ResponseEntity.ok(SalaryTaxInfo);
	}
}
