package com.optum.wms.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optum.wms.domain.Project;
import com.optum.wms.service.ProjectService;
import com.optum.wms.service.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController{

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ValidationErrorService validationErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,BindingResult result){
		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationService(result);
		if(errorMap != null) return errorMap;
		
		Project project1 = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project1,HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId){
		Project project = projectService.findByProjectIdentifier(projectId);
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProject(){
		return projectService.findAllProjects();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteByProjectId(@PathVariable String projectId) {
		projectService.deleteByProjectId(projectId);
		return new ResponseEntity("Project with ID '"+projectId+"' was deleted",HttpStatus.OK);
	}
}
