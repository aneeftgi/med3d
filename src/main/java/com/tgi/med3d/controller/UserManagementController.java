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

import com.tgi.med3d.model.UserRequestDto;
import com.tgi.med3d.service.UserService;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.ResponseHeaderUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@Api(value = "User Management")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
@ApiResponse(code = 409, message = "Conflict occurred") })

@RequestMapping("/usermanager")
public class UserManagementController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
	@ApiOperation(value = "This api is used to get all user data", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> getAllUser() {
		GenericResponse objGenericResponse = userService.getAllUser();
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllUserByHospitalId", method = RequestMethod.GET)
	@ApiOperation(value = "This api is used to get all user data by Hospital Id", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> getUserByHospitalId(@RequestParam("hospitalId") Long hospitalId) {
		GenericResponse objGenericResponse = userService.getUserByHospitalId(hospitalId);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ApiOperation(value = "add user ", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> addUser(@RequestBody UserRequestDto userRequestDto) throws Exception {
		GenericResponse objGenericResponse = userService.addUser(userRequestDto);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
	@ApiOperation(value = "update user ", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> updateUser(@RequestBody UserRequestDto userRequestDto) throws Exception {
		GenericResponse objGenericResponse = userService.updateUser(userRequestDto);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
	@ApiOperation(value = "delete user by id", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> deleteUser(@RequestParam Long userId) throws Exception {
		GenericResponse objGenericResponse = userService.deleteUser(userId);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
}
