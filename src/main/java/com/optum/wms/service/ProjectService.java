package com.optum.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optum.wms.domain.Backlog;
import com.optum.wms.domain.Project;
import com.optum.wms.exception.ProjectIdException;
import com.optum.wms.repository.BacklogRepository;
import com.optum.wms.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			String projectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifier);
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectIdentifier);
			} else {
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
			}
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
		}
	}
	
	public Project findByProjectIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase()); 
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' does not exists");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteByProjectId(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Cannot delete project with ID  '"+projectId.toUpperCase()+"'. This project does not exists");
		}
		projectRepository.delete(project);
	}
}
