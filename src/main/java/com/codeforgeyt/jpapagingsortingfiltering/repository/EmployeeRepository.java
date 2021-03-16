package com.codeforgeyt.jpapagingsortingfiltering.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codeforgeyt.jpapagingsortingfiltering.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	
}
