package com.tgi.med3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgi.med3d.service.RoleService;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.ResponseHeaderUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@Api(value = "Role Management")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
@ApiResponse(code = 409, message = "Conflict occurred") })

@RequestMapping("/rolemanager")
@CrossOrigin
public class RoleManagementController {

	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "/getAllRoles", method = RequestMethod.GET)
	@ApiOperation(value = "get All roles", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> getAllRoles() throws Exception {
		GenericResponse objGenericResponse = roleService.getAllRoles();
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	

	
	
}
