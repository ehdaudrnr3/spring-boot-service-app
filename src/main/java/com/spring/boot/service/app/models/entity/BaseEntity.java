package com.spring.boot.service.app.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {
	
	@Column(updatable = false)
	private Date createdDate;
	
	private Date lastModifiedDate;
	
	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
		lastModifiedDate = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() { 
		lastModifiedDate = new Date();
	}
}

