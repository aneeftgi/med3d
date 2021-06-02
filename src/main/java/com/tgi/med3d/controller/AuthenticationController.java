package com.tgi.med3d.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tgi.med3d.model.LoginRequestDto;
import com.tgi.med3d.service.LoginService;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.ResponseHeaderUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Authentication")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
		@ApiResponse(code = 409, message = "Conflict occurred") })

@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "This api is used to login", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> login(@RequestBody LoginRequestDto userLoginRequestDto, HttpSession httpSession,
			 HttpServletRequest httpServletRequest) throws JSONException, JsonMappingException, JsonProcessingException {
		GenericResponse objGenericResponse = loginService.login(userLoginRequestDto, httpSession,httpServletRequest);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	

}
