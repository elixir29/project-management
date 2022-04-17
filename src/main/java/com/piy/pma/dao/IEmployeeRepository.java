package com.piy.pma.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.piy.pma.dto.IEmployeeProject;
import com.piy.pma.entities.Employee;

public interface IEmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	
	@Override
	public List<Employee> findAll();
	
	public Employee findByEmail(String email);
	
	@Query(nativeQuery = true, 
		   value = "SELECT e.first_name AS firstName, e.last_name AS lastName, COUNT(pe.employee_id) AS projectCount \n"
			+ "from employee e LEFT JOIN project_employee pe on e.employee_id = pe.employee_id \n"
			+ "GROUP BY e.first_name, e.last_name \n"
			+ "ORDER BY projectCount DESC;")
	public List<IEmployeeProject> employeeProjects();

	@Query(nativeQuery = true,
		   countQuery = "SELECT COUNT(*) FROM employee", //Add the count query, because sometimes spring will not write the countQuery correctly by itself when writing custom queries.
		   value = "SELECT e.first_name AS firstName, e.last_name AS lastName, COUNT(pe.employee_id) AS projectCount \n"
			+ "from employee e LEFT JOIN project_employee pe on e.employee_id = pe.employee_id \n"
			+ "GROUP BY e.first_name, e.last_name \n"
			+ "ORDER BY projectCount DESC") //Follow baeldung's website's approach.
	public Page<IEmployeeProject> employeeProjectsPaginated(Pageable pageable);
}
