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
import com.tgi.med3d.model.RoleMaster;
import com.tgi.med3d.model.User;
import com.tgi.med3d.model.UserDetails;
import com.tgi.med3d.model.UserRequestDto;
import com.tgi.med3d.model.UserResponseDto;
import com.tgi.med3d.repository.HospitalDetailsRepository;
import com.tgi.med3d.repository.RoleMasterRepository;
import com.tgi.med3d.repository.UserDetailsRepository;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;
import com.tgi.med3d.validation.UserValidator;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService   {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleMasterRepository roleMasterRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	HospitalDetailsRepository hospitalDetailsRepository;

	
	public GenericResponse getAllUser() {
		List<User> userList = userRepository.findAll();
		if (userList.size() > 0) 
			userList.remove(0);
		if (userList.size() > 0) {
			List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
			userList.forEach(um -> {
				if(um.getStatus().equals(UserStatus.Active.name()))
				userResponseDtoList.add(convertUserEntityToDto(um));
			});
			return Library.getSuccessfulResponse(userResponseDtoList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			throw new RecordNotFoundException();
		}
	}
	
	public GenericResponse getUserByHospitalId(Long hospitalId) {
		HospitalDetails hospitalDetails = hospitalDetailsRepository.getById(hospitalId);
		if(hospitalDetails!=null && hospitalDetails.getHospitalStatus().equals(UserStatus.Active.name())) {
		List<User> userList = userRepository.getUserByHospitalDetailsId(hospitalId);
		if (userList.size() > 0) {
			List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
			userList.forEach(um -> {
				userResponseDtoList.add(convertUserEntityToDto(um));
			});
			return Library.getSuccessfulResponse(userResponseDtoList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			throw new RecordNotFoundException();
		}
		} else {
			throw new RecordNotFoundException();
		}
		}


	private UserResponseDto convertUserEntityToDto(User user) {		
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setId(user.getId());
		userResponseDto.setUserName(user.getUserName());
		userResponseDto.setPhoneNumber(user.getPhoneNumber());
		userResponseDto.setStatus(user.getStatus());
		userResponseDto.setUserDetails(user.getUserDetails());	
		if (user != null && user.getId() != null && user.getRoleId() != null) {
			userResponseDto.setRoleName(roleMasterRepository.getById(user.getRoleId()).getRoleName());
		}

		return userResponseDto;
	}
		
	@SuppressWarnings("unused")
	public GenericResponse addUser(UserRequestDto userRequestDto) {

		UserValidator.createUserValidator(userRequestDto);
		isUserExists(userRequestDto.getUserName());

		if (userRequestDto != null) {
	
			User user = new User();
			/* user save starts */
			// user 
			user.setUserName(userRequestDto.getUserName());
			user.setPhoneNumber(userRequestDto.getPhoneNumber());
			user.setStatus(userRequestDto.getStatus());
			user.setRoleId(userRequestDto.getRoleId());
			if (user.getPassword() == null) {
				String newPasswordEnc = (userRequestDto.getPassword().trim());
				
				user.setPassword(passwordEncoder.encode(newPasswordEnc));
			}			
			//user
			// userdetails
			UserDetails userDetails = new UserDetails();
			pouplateUserDetails(user,userRequestDto,userDetails);
			// userdetails
			// hospital details
			if( null != userRequestDto.getHospitalId()) {
				HospitalDetails hospitalDetails = hospitalDetailsRepository.getById(userRequestDto.getHospitalId());
				user.setHospitalDetails(hospitalDetails);				
			}
			// hospital details
			userRepository.save(user);
			
			/* user save ends */
					
			return Library.getSuccessfulResponse(convertUserEntityToDto(user),
					ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_CREATED);
						
		} else {
			throw new InvalidDataValidation();
		}
	}


	private void pouplateUserDetails(User userMaster, UserRequestDto userMasterRequestDto,UserDetails userDetails) {
		userDetails.setAddressLine1(userMasterRequestDto.getUserDetails().getAddressLine1());
		userDetails.setAddressLine2(userMasterRequestDto.getUserDetails().getAddressLine2());
		userDetails.setDistrictId(userMasterRequestDto.getUserDetails().getDistrictId());
		userDetails.setFirstName(userMasterRequestDto.getUserDetails().getFirstName());
		userDetails.setLastName(userMasterRequestDto.getUserDetails().getLastName());
		userDetails.setMiddleName(userMasterRequestDto.getUserDetails().getMiddleName());
		userDetails.setNationality(userMasterRequestDto.getUserDetails().getNationality());
		userDetails.setSalutationId(userMasterRequestDto.getUserDetails().getSalutationId());
		userDetails.setStateId(userMasterRequestDto.getUserDetails().getStateId());
		userDetails.setTalukId(userMasterRequestDto.getUserDetails().getTalukId());
		userMaster.setUserDetails(userDetails);
		
	}


	public GenericResponse updateUser(UserRequestDto userRequestDto) {
		UserValidator.createUserValidator(userRequestDto);
		if (userRequestDto != null && userRequestDto.getId() != null) {
			User user = userRepository.getById(userRequestDto.getId());		

			if (user != null && user.getId() != null) {
				
				UserDetails userDetails = user.getUserDetails();
				
//				user.setUserName(userRequestDto.getUserName());
				user.setPhoneNumber(userRequestDto.getPhoneNumber());
				user.setStatus(userRequestDto.getStatus());
				user.setRoleId(userRequestDto.getRoleId());
				if (user.getPassword() == null) {
					String newPasswordEnc = (userRequestDto.getPassword().trim());
					
					user.setPassword(passwordEncoder.encode(newPasswordEnc));
				}			
				pouplateUserDetails(user,userRequestDto,userDetails);
	
				userRepository.save(user);
				return Library.getSuccessfulResponse(convertUserEntityToDto(user),
						ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_UPDATED);
			} else {
				throw new RecordNotFoundException("User not available");
			}
			
		} else {
			throw new RecordNotFoundException();
		}
	}



	public GenericResponse deleteUser(Long id) {
		User user = userRepository.getById(id);
		if (user != null && user.getId() != null) {
			user.setStatus("Inactive");
			userRepository.save(user);
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
