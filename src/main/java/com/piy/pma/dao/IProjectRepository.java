package com.piy.pma.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.piy.pma.dto.IChartData;
import com.piy.pma.dto.ITimelineData;
import com.piy.pma.entities.Project;

public interface IProjectRepository extends PagingAndSortingRepository<Project, Long> {
	
	@Override
	public List<Project> findAll();
	
	@Query(nativeQuery = true, value = "SELECT stage AS label, COUNT(stage) AS value FROM project GROUP BY stage")
	public List<IChartData> getProjectStatus();
	
	@Query(nativeQuery = true, value = "SELECT name AS projectName, start_date AS startDate, end_date AS endDate FROM project")
	public List<ITimelineData> displayProjectTimelines();
	
	@Query(nativeQuery = true,
		   countQuery = "SELECT COUNT(*) FROM project",
		   value = "SELECT name AS projectName, start_date AS startDate, end_date AS endDate\n"
			+ "FROM project")
	public Page<ITimelineData> getPaginatedProjectTimelines(Pageable pageable);
}
