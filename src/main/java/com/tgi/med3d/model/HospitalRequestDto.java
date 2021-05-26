package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalRequestDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1918106382614020753L;

	private Long id; 

	private String userName; 
	
	private long phoneNumber; 
	
	private String password; 
	
	private String status;
		
	private Long roleId;	
	
	private HospitalDetails hospitalDetails;
	
}
