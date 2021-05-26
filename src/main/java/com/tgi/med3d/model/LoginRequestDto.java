package com.tgi.med3d.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6058478600184470778L;

	private String userName;
	
	private String password;
	
}
