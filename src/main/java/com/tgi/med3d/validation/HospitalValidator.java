package com.tgi.med3d.validation;

import com.tgi.med3d.exception.InvalidDataValidation;
import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.utility.ValidatorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HospitalValidator {
	public static void createHospitalValidator(HospitalRequestDto hospitalMasterRequestDto) {
		Boolean valid=true;
		StringBuilder errorMsg=new StringBuilder();
		if(hospitalMasterRequestDto.getHospitalDetails().getHospitalName()==null 
				|| hospitalMasterRequestDto.getHospitalDetails().getHospitalName().isEmpty()) {
			valid=false;
			errorMsg.append("Hospital Name is Requied");
		}else 
			if(hospitalMasterRequestDto.getPassword()==null || hospitalMasterRequestDto.getPassword().isEmpty()){
			valid=false;
			errorMsg.append("Password is Requied");
		}else if(hospitalMasterRequestDto.getUserName()!=null && !ValidatorUtil.isEmailValid(hospitalMasterRequestDto.getUserName())) {
			valid=false;
			errorMsg.append("Email Id is empty/not valid");
		} /*
			 * else if(userMasterRequestDto.getUserType()==null ||
			 * !EnumUtils.isValidEnum(UserType.class, userMasterRequestDto.getUserType())) {
			 * valid=false; errorMsg.append("User Type not valid"); }
			 */
		
		if(!valid) {
			throw new InvalidDataValidation(errorMsg.toString());
		}
		
	}
	
}
