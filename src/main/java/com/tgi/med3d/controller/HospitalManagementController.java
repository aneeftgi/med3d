package com.tgi.med3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.service.HospitalService;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.ResponseHeaderUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Hospital Management")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
@ApiResponse(code = 409, message = "Conflict occurred") })

//@CrossOrigin(origins ="http://localhost:3000/")
@RequestMapping("/hospitalManager")
public class HospitalManagementController {

	@Autowired
	HospitalService hospitalService;
	
	/*
	 * @RequestMapping(value = "/getAllHospital", method = RequestMethod.GET)
	 * 
	 * @ApiOperation(value = "This api is used to get all hospital data", notes =
	 * "Returns HTTP 200 if successful get the record") public
	 * ResponseEntity<Object> getAllHospital() { GenericResponse objGenericResponse
	 * = hospitalService.getAllHospital(); return new
	 * ResponseEntity<>(objGenericResponse,
	 * ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK); }
	 */
	
	@RequestMapping(value = "/addHospital", method = RequestMethod.POST)
	@ApiOperation(value = "add hospital ", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> createHospital(@RequestBody HospitalRequestDto hospitalMasterRequestDto) throws Exception {
		GenericResponse objGenericResponse = hospitalService.addHospital(hospitalMasterRequestDto);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateHospital", method = RequestMethod.POST)
	@ApiOperation(value = "update hospital ", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> updateHospital(@RequestBody HospitalRequestDto hospitalMasterRequestDto) throws Exception {
		GenericResponse objGenericResponse = hospitalService.updateHospital(hospitalMasterRequestDto);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/deleteHospital", method = RequestMethod.DELETE)
	@ApiOperation(value = "delete hospital by id", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> deleteHospital(@RequestParam Long hospitalId) throws Exception {
		GenericResponse objGenericResponse = hospitalService.deleteHospital(hospitalId);
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
//	@CrossOrigin(origins ="http://localhost:3000/")
	@GetMapping(value = "/search")
	@ApiOperation(value = "search by hospital by name", notes = "Returns HTTP 200 if successful get the record")
	public ResponseEntity<Object> searchHospital(@RequestParam(required=false) String search,
			   @RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size) throws Exception {				
		GenericResponse objGenericResponse = hospitalService.searchHospital(search,page,size);		
		return new ResponseEntity<>(objGenericResponse, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);		
	}
	
}
