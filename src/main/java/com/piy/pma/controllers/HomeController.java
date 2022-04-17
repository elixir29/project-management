package com.piy.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.piy.pma.dto.IChartData;
import com.piy.pma.dto.IEmployeeProject;
import com.piy.pma.entities.Project;
import com.piy.pma.services.EmployeeService;
import com.piy.pma.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	
	@Value("${version}")
	private String ver;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Value("${homeProjectPageSize}")
	int homeProjectPageSize;
	
	@Value("${homeEmployeesProjectsCntPageSize}")
	int homeEmployeesProjectsCntPageSize;
	
	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {
		return displayPaginatedHome(1, 1, model);
	}
	
	@GetMapping("/page")
	public String displayPaginatedHome(@RequestParam("homeEmployeesProjectsCntPageNo") int homeEmployeesProjectsCntPageNo, @RequestParam("homeProjectPageNo") int homeProjectPageNo, Model model) throws JsonProcessingException {
		
		model.addAttribute("versionNumber", ver);
		
		Page<Project> homeProjectPage = projectService.getPaginatedProjects(homeProjectPageNo, homeProjectPageSize);
		List<Project> projects = homeProjectPage.getContent();
		
		List<IChartData> projectData = projectService.getProjectStatus();
		//Converting the projectData object to json for use in javaScript pie chart.
		ObjectMapper objectMapper = new ObjectMapper();
		String projectDatajsonString = objectMapper.writeValueAsString(projectData);
		//projectDatajsonString will now have a json structure like this:
		//[["NOT STARTED", 1], ["INPROGRESS", 2], ["COMPLETED", 3]]
		
		
		Page<IEmployeeProject> homeEmployeesProjectsCntPage = employeeService.getEmployeeProjectsPaginated(homeEmployeesProjectsCntPageNo, homeEmployeesProjectsCntPageSize);
		List<IEmployeeProject> employeesProjectsCnt = homeEmployeesProjectsCntPage.getContent();
		
		model.addAttribute("projects", projects);
		model.addAttribute("projectStatusCnt", projectDatajsonString);
		model.addAttribute("employeesListProjectsCnt", employeesProjectsCnt);
		
		model.addAttribute("currentHomeProjectPage", homeProjectPageNo);
		model.addAttribute("totalHomeProjectPages", homeProjectPage.getTotalPages());
		model.addAttribute("totalHomeProjectItems", homeProjectPage.getTotalElements());
		model.addAttribute("totalHomeProjectItemsInPage", homeProjectPage.getSize());
		
		model.addAttribute("currentHomeEmployeesProjectsCntPage", homeEmployeesProjectsCntPageNo);
		model.addAttribute("totalHomeEmployeesProjectsCntPages", homeEmployeesProjectsCntPage.getTotalPages());
		model.addAttribute("totalHomeEmployeesProjectsCntItems", homeEmployeesProjectsCntPage.getTotalElements());
		model.addAttribute("totalHomeEmployeesProjectsCntItemsInPage", homeEmployeesProjectsCntPage.getSize());
		
		return "main/home";
	}
}
