package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class UserResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7157547015793301184L;
	
	private Long id;

	private String userName;
	
	private String roleName;
	
	private String phoneNumber;	
	
	private boolean status;
	
	
}
