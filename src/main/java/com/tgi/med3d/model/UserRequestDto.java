package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class UserRequestDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1918106382614020753L;

	private Long id; 
	
	private String userName; 
	
	private String phoneNumber; 
	
	private String gender;
	
	private String password; 
	
	private boolean status;
	
	private String address1;	
	
	private String address2;	
	
	private Long roleId;
	
	private Long hospitalId;
		
	
}
