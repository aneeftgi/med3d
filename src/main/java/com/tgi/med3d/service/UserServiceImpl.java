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
import com.tgi.med3d.model.Hospital;
import com.tgi.med3d.model.User;
import com.tgi.med3d.model.UserRequestDto;
import com.tgi.med3d.model.UserResponseDto;
import com.tgi.med3d.repository.HospitalRepository;
import com.tgi.med3d.repository.RoleRepository;
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
	RoleRepository roleMasterRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	@Autowired
	HospitalRepository hospitalDetailsRepository;

	
	public GenericResponse getAllUser() {
		List<User> userList = userRepository.findAll();
		if (userList.size() > 0) 
			userList.remove(0);
		if (userList.size() > 0) {
			List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
			userList.forEach(um -> {
				if(um.isStatus())
				userResponseDtoList.add(convertUserEntityToDto(um));
			});
			return Library.getSuccessfulResponse(userResponseDtoList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			throw new RecordNotFoundException();
		}
	}
	
	public GenericResponse getUserByHospitalId(Long hospitalId) {
		Hospital hospitalDetails = hospitalDetailsRepository.getById(hospitalId);
		if(hospitalDetails!=null && hospitalDetails.isHospitalStatus()) {
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
		userResponseDto.setStatus(user.isStatus());
		if (user != null && user.getId() != null && user.getRole() != null) {
			userResponseDto.setRoleName(user.getRole().getRoleName());
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
			user.setGender(userRequestDto.getGender());
			user.setAddress1(userRequestDto.getAddress1());
			user.setAddress2(userRequestDto.getAddress2());
			user.setStatus(userRequestDto.isStatus());
			user.setRole(roleMasterRepository.getById(userRequestDto.getRoleId()));
			if (user.getPassword() == null) {
				String newPasswordEnc = (userRequestDto.getPassword().trim());
				
				user.setPassword(passwordEncoder.encode(newPasswordEnc));
			}			
			//user
	
			// hospital details
			if( null != userRequestDto.getHospitalId()) {
				Hospital hospitalDetails = hospitalDetailsRepository.getById(userRequestDto.getHospitalId());
				user.setHospital(hospitalDetails);				
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

	public GenericResponse updateUser(UserRequestDto userRequestDto) {
		UserValidator.createUserValidator(userRequestDto);
		if (userRequestDto != null && userRequestDto.getId() != null) {
			User user = userRepository.getById(userRequestDto.getId());		

			if (user != null && user.getId() != null) {
				
				
				
//				user.setUserName(userRequestDto.getUserName());
				user.setPhoneNumber(userRequestDto.getPhoneNumber());
				user.setStatus(userRequestDto.isStatus());
				user.setRole(roleMasterRepository.getById(userRequestDto.getRoleId()));
				if (user.getPassword() == null) {
					String newPasswordEnc = (userRequestDto.getPassword().trim());
					
					user.setPassword(passwordEncoder.encode(newPasswordEnc));
				}			
	
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
			user.setStatus(false);
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
