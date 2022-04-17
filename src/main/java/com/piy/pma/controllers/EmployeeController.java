package com.piy.pma.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.piy.pma.entities.Employee;
import com.piy.pma.helper.Message;
import com.piy.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Value("${employeePageSize}")
	int pageSize;
	
	@GetMapping
	public String displayEmployees(Model model) {
		return displayPaginatedEmployees(1, model);
	}
	
	@GetMapping("/page")
	public String displayPaginatedEmployees(@RequestParam("pageNo") int pageNo, Model model) {
			
		Page<Employee> page = employeeService.getPaginatedEmployees(pageNo, pageSize);
		List<Employee> employees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalItemsInPage", page.getSize());
		
		model.addAttribute("employees", employees);
		
		return "employees/employee-list";
	}
	
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		Employee anEmployee = new Employee();
		model.addAttribute("employee", anEmployee);
		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String createEmployee(@Valid Employee employee, BindingResult result, Model model, HttpSession session) {
		
		//When creating a new employee
		if(employee.getEmployeeId() == 0 && !result.hasErrors() ) {
			//Check if entered email already exists in DB.
			Employee employeeFromRepo = employeeService.getEmployeeByEmail(employee.getEmail());
			if(employeeFromRepo != null && employeeFromRepo.getEmail().equals(employee.getEmail())) { //need to check if employee is null, because if u do comparison on null objects code will break.
				model.addAttribute("errorMessage", "Employee creation failed. This email address already belongs to an other employee.");
				return "employees/employee-creation-error";
			}
		}
		//When updating an existing employee
		else if(employee.getEmployeeId() != 0 && !result.hasErrors()) {
			//If email is being changed while updation, we need to check if this changed email already exists in DB for some other employee.
			Employee employeeFromRepo = employeeService.getEmployeeByEmail(employee.getEmail());
			if(employeeFromRepo != null && employeeFromRepo.getEmployeeId() != employee.getEmployeeId()) {
				model.addAttribute("errorMessage", "Employee updation failed. This email address already belongs to an other employee.");
				return "employees/employee-creation-error";
			}
		}
		else if(result.hasErrors()) {
			return "employees/employee-creation-error";
		}
		
		session.setAttribute("message", new Message("success", "Employee entered successfully"));
		employeeService.save(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("/update")
	public String updateEmployee(@RequestParam("id") long id, Model model) {
		Employee emp = employeeService.getEmployeeById(id);
		model.addAttribute("employee", emp);
		return "employees/new-employee";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") long id, HttpSession session) {
		employeeService.deleteEmployee(employeeService.getEmployeeById(id));
		session.setAttribute("message", new Message("warning", "Employee deleted"));
		return "redirect:/employees";
	}
	
}
