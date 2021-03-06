package com.codeforgeyt.jpapagingsortingfiltering.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.codeforgeyt.jpapagingsortingfiltering.model.Employee;
import com.codeforgeyt.jpapagingsortingfiltering.model.EmployeePage;
import com.codeforgeyt.jpapagingsortingfiltering.model.EmployeeSearchCriteria;
import com.codeforgeyt.jpapagingsortingfiltering.repository.EmployeeCriteriaRepository;
import com.codeforgeyt.jpapagingsortingfiltering.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final EmployeeCriteriaRepository employeeCriteriaRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository,
			EmployeeCriteriaRepository employeeCriteriaRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.employeeCriteriaRepository = employeeCriteriaRepository;
	}
	
	public Page<Employee> getEmployees(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria){
		return employeeCriteriaRepository.findAllWithFilters(employeePage, employeeSearchCriteria);
	}
	
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
}
