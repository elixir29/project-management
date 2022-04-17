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

import com.piy.pma.dto.ITimelineData;
import com.piy.pma.entities.Employee;
import com.piy.pma.entities.Project;
import com.piy.pma.helper.Message;
import com.piy.pma.services.EmployeeService;
import com.piy.pma.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/projects")
public class ProjectController{
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Value("${projectPageSize}")
	int pageSize;
	
	@Value("${timelinesPageSize}")
	int timelinesPageSize;
	
	@GetMapping
	public String displayProjects(Model model) {
		return displayPaginatedProjects(1, model);
	}
	
	@GetMapping("/page")
	public String displayPaginatedProjects(@RequestParam("pageNo") int pageNo, Model model) {
		
		Page<Project> page = projectService.getPaginatedProjects(pageNo, pageSize);
		List<Project> projects = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalItemsInPage", page.getSize());
		
		model.addAttribute("projects", projects);

		return "projects/project-list";
	}
	
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		
		Project aProject = new Project();
		model.addAttribute("project", aProject);
		
		List<Employee> employees = employeeService.getAll();
		model.addAttribute("allEmployees", employees);
		
		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(@Valid Project project, BindingResult result, Model model, HttpSession session) {
		
		
		if(!result.hasErrors()) {
			//EndDate must always be greater than startDate.
			if(project.getStartDate().after(project.getEndDate())) {
				model.addAttribute("errorMessage", "End date can't be earlier than start date.");
				return "projects/project-creation-error";
			}
		}
		else if(result.hasErrors()) {
			return "projects/project-creation-error";
		}
		
		projectService.save(project);
		session.setAttribute("message", new Message("success", "Project entered successfully"));
		return "redirect:/projects";
	}
	
	@GetMapping("update")
	public String updateProject(@RequestParam("id") long id, Model model) {
		
		Project project = projectService.getProjectById(id);
		model.addAttribute("project", project);
		
		List<Employee> employees = employeeService.getAll();
		model.addAttribute("allEmployees", employees);
		
		return "projects/new-project";
	}
	
	@GetMapping("delete")
	public String deleteProject(@RequestParam("id") long id, HttpSession session) {
		projectService.deleteProject(projectService.getProjectById(id));
		session.setAttribute("message", new Message("warning", "Project deleted"));
		return "redirect:/projects";
	}
	
	@GetMapping("/timelines")
	public String displayProjectTimelines(Model model) throws JsonProcessingException {
		return displayPaginatedProjectTimelines(1, model);
	}
	
	@GetMapping("/timelines/pages")
	public String displayPaginatedProjectTimelines(@RequestParam("timelinesPageNo") int timelinesPageNo, Model model) throws JsonProcessingException {
		
		Page<ITimelineData> projectTimelinesPage = projectService.getPaginatedTimelinesData(timelinesPageNo, timelinesPageSize);
		List<ITimelineData> projectTimelines = projectTimelinesPage.getContent();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String projectTimeLinesJsonString = objectMapper.writeValueAsString(projectTimelines);
		
		System.out.println("------------------------Project Timelines------------------------------------");
		System.out.println(projectTimeLinesJsonString);
		
		model.addAttribute("projectTimeList", projectTimeLinesJsonString);
		
		model.addAttribute("currentTimelinesPage", timelinesPageNo);
		model.addAttribute("totalTimelinePages", projectTimelinesPage.getTotalPages());
		model.addAttribute("totalTimeLineItems", projectTimelinesPage.getTotalElements());
		model.addAttribute("totalTimeLineItemsInPage", projectTimelinesPage.getSize());
		
		return "projects/project-timelines";
	}
}
