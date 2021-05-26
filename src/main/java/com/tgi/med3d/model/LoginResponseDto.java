package com.tgi.med3d.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4630728974156437691L;
	
	private Long userId;	
	
	private String userName; //emailId is their user name
	
	private String accessToken;
		
	private String expiresIn;
	
	private String refreshToken;
	
	private String roleName;

	
}
