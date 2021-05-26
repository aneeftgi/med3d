package com.tgi.med3d.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;
import com.tgi.med3d.utility.ResponseHeaderUtility;



@ControllerAdvice
public class Med3dExceptionHandler {
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Object> NoResponseException(HttpServletRequest request, RecordNotFoundException ex) {
		GenericResponse GenericResponseObj=Library.noRecordFoundResponse(ErrorMessages.NO_RECORD_FOUND); 
		return new ResponseEntity<>(GenericResponseObj, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidDataValidation.class)
	public ResponseEntity<Object> InvalidDataValidation(HttpServletRequest request, InvalidDataValidation ex) {
		GenericResponse GenericResponseObj=Library.getFailResponseCode(ErrorCode.INVALID_DATA.getCode(), ex.getMessage());
		return new ResponseEntity<>(GenericResponseObj, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	@ExceptionHandler(InvalidUserValidation.class)
	public ResponseEntity<Object> InvalidUserValidation(HttpServletRequest request, InvalidUserValidation ex) {
		GenericResponse GenericResponseObj=Library.getFailResponseCode(ErrorCode.INVALID_DATA.getCode(), ex.getMessage());
		return new ResponseEntity<>(GenericResponseObj, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Object> httpClientError(HttpServletRequest request, HttpClientErrorException ex) {
		GenericResponse GenericResponseObj=Library.getFailResponseCode(ErrorCode.UNAUTHORIZED.getCode(),ErrorMessages.ERROR_USERID_INVALID);
		return new ResponseEntity<>(GenericResponseObj, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> exception(HttpServletRequest request, Exception ex) {
		ex.printStackTrace();
		GenericResponse GenericResponseObj=Library.getFailResponseCode(ErrorCode.EXCEPTION.getCode(), ErrorMessages.EXCEPTION_MESSAGE); 
		return new ResponseEntity<>(GenericResponseObj, ResponseHeaderUtility.HttpHeadersConfig(), HttpStatus.OK);
	}	
	
}
