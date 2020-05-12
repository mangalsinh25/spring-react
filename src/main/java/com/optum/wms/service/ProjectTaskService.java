package com.optum.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optum.wms.domain.Backlog;
import com.optum.wms.domain.ProjectTask;
import com.optum.wms.exception.ProjectNotFoundException;
import com.optum.wms.repository.BacklogRepository;
import com.optum.wms.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		//PTs to be added to specific Project, Project != null, BL Exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project Backlog not found for Project Identifier "+projectIdentifier.toUpperCase());
		}
		//Set BL to PT
		projectTask.setBacklog(backlog);
		// Project Sequence
		Integer BacklogSequence = backlog.getPTSequence();
		//Update the BL Sequence
		BacklogSequence++;
		backlog.setPTSequence(BacklogSequence);
		//Add Sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		//INITIAL Priority
		if(projectTask.getPriority() == null || projectTask.getPriority() <= 0) {
			projectTask.setPriority(3);
		}
		//INTIAL Status
		if(projectTask.getStatus() == null || projectTask.getStatus().isEmpty()) {
			//Implement ENUM
			projectTask.setStatus("TO_DO");
		}
		return projectTaskRepository.save(projectTask);
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		Iterable<ProjectTask> projectTasks =  projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
		if(projectTasks == null || !projectTasks.iterator().hasNext()) {
			throw new ProjectNotFoundException("Project with ID :"+backlog_id+" does not exist");
		}
		
		return projectTasks;
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id) {
		//make sure we are searching on the right backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project with ID "+backlog_id+" does not exist");
		}
		
		//make sure out task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task with sequence "+pt_id+" not found");
		}
		
		// make sure project task is part of backlog/project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task "+pt_id+" does not exist in Project "+backlog_id);
		}
		
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask,String backlog_id,String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);
		
		projectTask = updatedTask;
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id,String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);
		projectTaskRepository.delete(projectTask);
	}
}
