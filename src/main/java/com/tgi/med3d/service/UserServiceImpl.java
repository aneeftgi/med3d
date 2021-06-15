package com.tgi.med3d.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.jasypt.digest.StringDigester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleMasterRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	HospitalRepository hospitalDetailsRepository;

	
	public GenericResponse getAllUser() {
		log.debug("getAllUser starts");
		List<User> userList = userRepository.findAll();
		List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
		if (userList.size() > 0) 
			userList.remove(0);
		if (userList.size() > 0) {
			userList.forEach(um -> {
				if(um.isStatus())
				userResponseDtoList.add(convertUserEntityToDto(um));
			});
			
		} else {
			log.error("No record found");
			throw new RecordNotFoundException();
		}
		log.debug("getAllUser ends");
		return Library.getSuccessfulResponse(userResponseDtoList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
				ErrorMessages.RECORED_FOUND);
	}
	
	public GenericResponse getUserByHospitalId(Long hospitalId) {
		log.debug("getUserByHospitalId starts");

		Hospital hospitalDetails = hospitalDetailsRepository.getById(hospitalId);
		List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();

		if(hospitalDetails!=null && hospitalDetails.isHospitalStatus()) {
		List<User> userList = userRepository.getUserByHospitalDetailsId(hospitalId);
		if (userList.size() > 0) {
			userList.forEach(um -> {
				userResponseDtoList.add(convertUserEntityToDto(um));
			});
			
		} else {
			log.error("No record found");
			throw new RecordNotFoundException();
		}
		} else {
			log.error("No record found");
			throw new RecordNotFoundException();
		}
		
		log.debug("getUserByHospitalId ends");

		return Library.getSuccessfulResponse(userResponseDtoList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
				ErrorMessages.RECORED_FOUND);
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
		log.debug("addUser starts");

		UserValidator.createUserValidator(userRequestDto);
		isUserExists(userRequestDto.getUserName());
		User user = new User();

		if (userRequestDto != null) {
	
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
					
			
						
		} else {
			log.error("Invalid data");
			throw new InvalidDataValidation();
		}
		log.debug("addUser ends");
		return Library.getSuccessfulResponse(convertUserEntityToDto(user),
				ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_CREATED);
	}

	public GenericResponse updateUser(UserRequestDto userRequestDto) {
		log.debug("updateUser starts");

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
				log.debug("updateUser ends");
				return Library.getSuccessfulResponse(convertUserEntityToDto(user),
						ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_UPDATED);
			} else {
				log.error("User Not found" );
				throw new RecordNotFoundException("User not available");
			}
			
		} else {
			log.error("No record found" );
			throw new RecordNotFoundException();
		}
		
	}



	public GenericResponse deleteUser(Long id) {
		log.debug("deleteUser starts");

		User user = userRepository.getById(id);
		if (user != null && user.getId() != null) {
			user.setStatus(false);
			userRepository.save(user);
			
		} else {
			log.error("No record found");
			throw new RecordNotFoundException();
		}
		log.debug("deleteUser ends");
		return Library.getSuccessfulResponse(null,
				ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_DELETED);
	}

private void isUserExists(String userName) {
		
		User user= userRepository.findByUserName(userName);
		if(null!=user)
			throw new InvalidDataValidation("User Name already Exists");
		
	}
	
	

}
