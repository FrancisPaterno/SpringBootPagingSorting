package com.codeforgeyt.jpapagingsortingfiltering.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeforgeyt.jpapagingsortingfiltering.model.Employee;
import com.codeforgeyt.jpapagingsortingfiltering.model.EmployeePage;
import com.codeforgeyt.jpapagingsortingfiltering.model.EmployeeSearchCriteria;
import com.codeforgeyt.jpapagingsortingfiltering.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Employee>> getEmployees(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria){
		return new ResponseEntity<Page<Employee>>(employeeService.getEmployees(employeePage, employeeSearchCriteria), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
		return new ResponseEntity<Employee>(employeeService.addEmployee(employee), HttpStatus.OK);
	}
}
