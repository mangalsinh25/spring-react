package com.optum.wms.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optum.wms.domain.ProjectTask;
import com.optum.wms.service.ProjectTaskService;
import com.optum.wms.service.ValidationErrorService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private ValidationErrorService validationErrorService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id) {
		ResponseEntity<?> errorMap = validationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
		return projectTaskService.findBacklogById(backlog_id);
	}
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id){
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id,pt_id);
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,BindingResult result,
												@PathVariable String backlog_id,@PathVariable String pt_id){
		ResponseEntity<?> errorMap = validationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		
		ProjectTask updatedProjectTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id);
		
		return new ResponseEntity<ProjectTask>(updatedProjectTask, HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id){
		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);
		return new ResponseEntity<String>("Project Task"+pt_id,HttpStatus.OK); 
	}
	
}
