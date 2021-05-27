package com.tgi.med3d.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.configurationprocessor.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tgi.med3d.exception.InvalidUserValidation;
import com.tgi.med3d.model.LoginRequestDto;
import com.tgi.med3d.utility.GenericResponse;

public interface LoginService {
	
	public GenericResponse login(LoginRequestDto userLoginRequestDto, HttpSession httpSession,
			HttpServletRequest httpServletRequest) throws JSONException, JsonMappingException, JsonProcessingException, InvalidUserValidation;

}
