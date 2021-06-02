package com.tgi.med3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.service.DashBoardService;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.ResponseHeaderUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value = "Dash baord")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
@ApiResponse(code = 409, message = "Conflict occurred") })

@RequestMapping("/dashBoard")
public class DashBoardController {		
	
	@Autowired
	DashBoardService dashBoardService;		

	@RequestMapping(value = "/superAdmin", method = RequestMethod.GET)
	@ApiOperation(value = "This api is used to get admin dashboard", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> adminDashboard() {
		GenericResponse objGenericResponse = dashBoardService.superAdminDashBoard();
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hospital/{hospitalId}", method = RequestMethod.GET)
	@ApiOperation(value = "This api is used to get hospital admin dashboard", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> userDashboard(@PathVariable Long hospitalId) {
		GenericResponse objGenericResponse = dashBoardService.hospitalDashBoard(hospitalId);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}



}
