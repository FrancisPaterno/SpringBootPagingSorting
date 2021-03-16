package com.codeforgeyt.jpapagingsortingfiltering.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.codeforgeyt.jpapagingsortingfiltering.model.Employee;
import com.codeforgeyt.jpapagingsortingfiltering.model.EmployeePage;
import com.codeforgeyt.jpapagingsortingfiltering.model.EmployeeSearchCriteria;

@Repository
public class EmployeeCriteriaRepository {

	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;
	
	public EmployeeCriteriaRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
		this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
	}
	
	public Page<Employee> findAllWithFilters(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria){
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
		Predicate predicate = getPredicate(employeeSearchCriteria, employeeRoot);
		criteriaQuery.where(predicate);
		setOrder(employeePage, criteriaQuery, employeeRoot);
		
		TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
		typedQuery.setMaxResults(employeePage.getPageSize());
		
		Pageable pageable = getPageable(employeePage);
		long employeeCount = getEmployeeCount(predicate);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, employeeCount);
	}

	private long getEmployeeCount(Predicate predicate) {
		// TODO Auto-generated method stub
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Employee> countRoot = countQuery.from(Employee.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}

	private Pageable getPageable(EmployeePage employeePage) {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
		return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
	}

	private void setOrder(EmployeePage employeePage, CriteriaQuery<Employee> criteriaQuery,
			Root<Employee> employeeRoot) {
		// TODO Auto-generated method stub
		if(employeePage.getSortDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(employeePage.getSortBy())));
		}
		else {
			criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(employeePage.getSortBy())));
		}
		
	}

	private Predicate getPredicate(EmployeeSearchCriteria employeeSearchCriteria, Root<Employee> employeeRoot) {
		// TODO Auto-generated method stub
		List<Predicate> predicate  = new ArrayList<>();
		
		if(Objects.nonNull(employeeSearchCriteria.getFirstName())) {
			predicate.add(criteriaBuilder.like(employeeRoot.get("firstName"), "%" + employeeSearchCriteria.getFirstName() + "%"));
		}
		
		if(Objects.nonNull(employeeSearchCriteria.getLastname())) {
			predicate.add(criteriaBuilder.like(employeeRoot.get("lastname"), "%" + employeeSearchCriteria.getLastname() + "%"));
		}
		
		return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
	}
	
}
