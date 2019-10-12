package com.optum.wms.repository;

import org.springframework.data.repository.CrudRepository;

import com.optum.wms.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>{

	Project findByProjectIdentifier (String projectIdentifier);
	
	@Override
	Iterable<Project> findAll();
}
