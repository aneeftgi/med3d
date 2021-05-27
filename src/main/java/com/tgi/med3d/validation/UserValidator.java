package com.tgi.med3d.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgi.med3d.exception.InvalidDataValidation;
import com.tgi.med3d.model.User;
import com.tgi.med3d.model.UserRequestDto;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.ValidatorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserValidator {
	@Autowired
	UserRepository userRepository;
	
	public static void createUserValidator(UserRequestDto userRequestDto) {
		Boolean valid=true;
		StringBuilder errorMsg=new StringBuilder();
		if(userRequestDto.getRoleId()==null || userRequestDto.getRoleId()==0) {
			valid=false;
			errorMsg.append("Role Id is Required");
		}else 
			if(userRequestDto.getPassword()==null || userRequestDto.getPassword().isEmpty()){
			valid=false;
			errorMsg.append("Password is Required");
		}else if(userRequestDto.getUserName()!=null && !ValidatorUtil.isEmailValid(userRequestDto.getUserName())) {
			valid=false;
			errorMsg.append("Email Id is empty/not valid");
		}
		
		if(!valid) {
			throw new InvalidDataValidation(errorMsg.toString());
		}
		
	}
	
	
	
}
