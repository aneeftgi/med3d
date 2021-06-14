package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7157547015793301184L;

	private Long userId;

	private String userName; 
	
	private String roleName;
			
	private String hospitalName;	
	
	private boolean hospitalStatus;
	
	private String address1;	
	
	private String address2;		

	private String contactNumber;	

	
}
