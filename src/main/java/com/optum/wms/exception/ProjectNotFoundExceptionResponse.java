package com.optum.wms.exception;

public class ProjectNotFoundExceptionResponse {

	private String ProjectNotFound;

	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		ProjectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		ProjectNotFound = projectNotFound;
	}
	
	
	
}
