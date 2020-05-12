package com.optum.wms.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	public ProjectTask() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false,unique = true)
	private String projectSequence;
	@NotBlank(message = "Please include Project Summary")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	private Date dueDate;
	//ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="backlog_id",updatable = false,nullable = false)
	@JsonIgnore
	private Backlog backlog;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	private Date created;
	private Date updated;
	
	
	@PrePersist
	protected void onCreate() {
		this.created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	
	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getCreated() {
		return created;
	}

	
	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}