package com.piy.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.piy.pma.dao.IProjectRepository;
import com.piy.pma.dto.IChartData;
import com.piy.pma.dto.ITimelineData;
import com.piy.pma.entities.Project;

@Service
public class ProjectService {
	
	@Autowired
	IProjectRepository projectRepository;
	
	public Project save(Project project) {
		return projectRepository.save(project);
	}
	
	public List<Project> getAll() {
		return projectRepository.findAll();
	}
	
	public List<IChartData> getProjectStatus() {
		return projectRepository.getProjectStatus();
	}
	
	public Project getProjectById(long id) {
		return projectRepository.findById(id).get();
	}
	
	public void deleteProject(Project project) {
		projectRepository.delete(project);
	}
	
	public List<ITimelineData> displayProjectTimelines() {
		return projectRepository.displayProjectTimelines();
	}
	
	public Page<Project> getPaginatedProjects(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return projectRepository.findAll(pageable);
	}
	
	public Page<ITimelineData> getPaginatedTimelinesData(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return projectRepository.getPaginatedProjectTimelines(pageable);
	}
}
