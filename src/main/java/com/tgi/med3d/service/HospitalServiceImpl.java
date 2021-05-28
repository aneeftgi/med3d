package com.tgi.med3d.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.jasypt.digest.StringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.enums.UserStatus;
import com.tgi.med3d.exception.InvalidDataValidation;
import com.tgi.med3d.exception.RecordNotFoundException;
import com.tgi.med3d.model.HospitalDetails;
import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.model.HospitalResponseDto;
import com.tgi.med3d.model.RoleMaster;
import com.tgi.med3d.model.User;
import com.tgi.med3d.model.UserResponseDto;
import com.tgi.med3d.repository.HospitalDetailsRepository;
import com.tgi.med3d.repository.RoleMasterRepository;
import com.tgi.med3d.repository.UserDetailsRepository;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;
import com.tgi.med3d.validation.HospitalValidator;
import com.tgi.med3d.validation.UserValidator;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HospitalServiceImpl implements HospitalService  {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleMasterRepository roleMasterRepository;

	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	HospitalDetailsRepository hospitalDetailsRepository;

	 @Override
	public GenericResponse getAllHospital() {
		List<HospitalDetails> hospitalDetailsList = hospitalDetailsRepository.findAll();
		if (hospitalDetailsList.size() > 0) {
			
			hospitalDetailsList.removeIf((HospitalDetails Hd)-> Hd.getHospitalStatus().equals(UserStatus.Inactive.name()));

			return Library.getSuccessfulResponse(hospitalDetailsList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			throw new RecordNotFoundException();
		}
	}	
			
	@SuppressWarnings("unused")
	public GenericResponse addHospital(HospitalRequestDto hospitalRequestDto) {

		HospitalValidator.createHospitalValidator(hospitalRequestDto);
		isUserExists(hospitalRequestDto.getUserName());

		if (hospitalRequestDto != null) {
			User user = new User();
			
			/* user save starts */
			// user 
			user.setUserName(hospitalRequestDto.getUserName());
			user.setPhoneNumber(hospitalRequestDto.getPhoneNumber());
			user.setStatus(hospitalRequestDto.getStatus());
			user.setRoleId(hospitalRequestDto.getRoleId());
			if (user.getPassword() == null) {
				String newPasswordEnc = (hospitalRequestDto.getPassword().trim());
				
				user.setPassword(passwordEncoder.encode(newPasswordEnc));
			}			
			//user
			// hospital details
			HospitalDetails hospitalDetails = new HospitalDetails();
			pouplateHospitalDetails(user,hospitalRequestDto,hospitalDetails);
			// hospital details
			
			userRepository.save(user);
			return Library.getSuccessfulResponse(user,
					ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_CREATED);
						
		} else {
			throw new InvalidDataValidation();
		}
	}

	 private void pouplateHospitalDetails(User userMaster, HospitalRequestDto
			 hospitalRequestDto,HospitalDetails hospitalDetails) 
	 { 
			  hospitalDetails.setHospitalName(hospitalRequestDto.getHospitalDetails().getHospitalName());
			 hospitalDetails.setHospitalStatus(hospitalRequestDto.getHospitalDetails().getHospitalStatus());
			 hospitalDetails.setAddressLine1(hospitalRequestDto.getHospitalDetails().getAddressLine1());
			 hospitalDetails.setAddressLine2(hospitalRequestDto.getHospitalDetails().getAddressLine2());
			 hospitalDetails.setDistrictId(hospitalRequestDto.getHospitalDetails().getDistrictId());
			 hospitalDetails.setStateId(hospitalRequestDto.getHospitalDetails().getStateId());
			 hospitalDetails.setTalukId(hospitalRequestDto.getHospitalDetails().getTalukId());
			  userMaster.setHospitalDetails(hospitalDetails);
			  
			  }
	public GenericResponse updateHospital(HospitalRequestDto hospitalRequestDto) {
		HospitalValidator.createHospitalValidator(hospitalRequestDto);

		if (hospitalRequestDto != null && hospitalRequestDto.getId() != null) {
			User user = userRepository.getById(hospitalRequestDto.getId());		

			if (user != null && user.getId() != null) {
				
			HospitalDetails hospitalDetails = user.getHospitalDetails();
				
			pouplateHospitalDetails(user,hospitalRequestDto,hospitalDetails);
						
			userRepository.save(user);
			return Library.getSuccessfulResponse(user,
						ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_UPDATED);
			} else {
				throw new RecordNotFoundException("User is not available");
			}
			
		} else {
			throw new RecordNotFoundException();
		}
	}


	public GenericResponse deleteHospital(Long id) {
		HospitalDetails hospitalDetails = hospitalDetailsRepository.getById(id);
		if (hospitalDetails != null && hospitalDetails.getId() != null) {
			
			//Hospital Inactive
			hospitalDetails.setHospitalStatus("Inactive");
			hospitalDetailsRepository.save(hospitalDetails);
			//Hospital Inactive
			
			//Hospital users Inactive
			List<User> userList = userRepository.getUserByHospitalDetailsId(hospitalDetails.getId());
			if (userList.size() > 0) {
				userList.forEach(um -> {
					um.setStatus("Inactive");
					userRepository.save(um);				
				});
			}
			//Hospital users Inactive

			return Library.getSuccessfulResponse(null,
					ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_DELETED);
		} else {
			throw new RecordNotFoundException();
		}
	}
	
private void isUserExists(String userName) {
		
		User user= userRepository.findByUserName(userName);
		if(null!=user)
			throw new InvalidDataValidation("User Name already Exists");
		
	}
	

	}


