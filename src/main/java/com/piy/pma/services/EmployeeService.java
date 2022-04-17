package com.piy.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.piy.pma.dao.IEmployeeRepository;
import com.piy.pma.dto.IEmployeeProject;
import com.piy.pma.entities.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	IEmployeeRepository employeeRepository;
	
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public List<IEmployeeProject> employeeProjects() {
		return employeeRepository.employeeProjects();
	}
	
	public Employee getEmployeeById(long id) {
		return employeeRepository.findById(id).get();
	}
	
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}
	
	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);
	}
	
	public Page<Employee> getPaginatedEmployees(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return employeeRepository.findAll(pageable);
	}
	
	public Page<IEmployeeProject> getEmployeeProjectsPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return employeeRepository.employeeProjectsPaginated(pageable);
	}

}