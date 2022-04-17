package com.piy.pma.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.piy.pma.dao.IEmployeeRepository;
import com.piy.pma.entities.Employee;

public class UniqueValidator implements ConstraintValidator<UniqueValue, String> {

	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		System.out.println("Entered validation method for email");
		
		Employee emp = employeeRepository.findByEmail(value);
		
		if(emp != null) return false; //meaning that the employee already exits in DB, so you can't create a new employee, hence validation failed.
		else return true;
	}

}
