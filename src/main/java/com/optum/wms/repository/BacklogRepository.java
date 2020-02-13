package com.optum.wms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.optum.wms.domain.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long>{

	Backlog findByProjectIdentifier(String projectIdentifier);
}
