package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.app.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "modifiedOn","createdOn", "deletedOn","deletedBy" })
public class RecordModifier implements Serializable {	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(name = "created_by",nullable = false, updatable = false)
	private String createdBy;
	
	@CreatedDate
    @Column(name = "created_on",nullable = false, updatable = false)
	private	Date createdOn = new Date();
	
	@LastModifiedBy
    @Column(name = "modified_by", nullable = false)
	private String modifiedBy;
	
	@LastModifiedDate
	@Column(name = "modified_on", nullable = false)
	private	Date modifiedOn;
	 
	@Column(name="deleted_on")
	private	Date deletedOn;
	
	@Column(name="deleted_by")
	private	String deletedBy;
	
	@NotNull(message = "Status cannot be blank")
    @ApiModelProperty(value = "Valid status", required = true, allowableValues = "ACTIVE, INACTIVE")
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status =Status.ACTIVE;
	
}
