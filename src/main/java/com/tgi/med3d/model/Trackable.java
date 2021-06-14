package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
//@Audited
public class Trackable implements Serializable {

	private static final long serialVersionUID = 1346562084432072428L;
	
	@JsonIgnore
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	public Date createdDate;
	
	@JsonIgnore
	@CreatedBy
	@Column(name = "created_by")
	public Long createdBy;
	
	@JsonIgnore
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date modifiedDate;
	
	
	@JsonIgnore
	@LastModifiedBy
	@Column(name = "modified_by")
	public Long modifiedBy;
	
	public Trackable()
	{
		createdDate=new Date();
	}

}