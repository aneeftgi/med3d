package com.tgi.med3d.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.jasypt.digest.StringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.model.HospitalResponseDto;
import com.tgi.med3d.model.PaginationResponseDTO;
import com.tgi.med3d.model.User;
import com.tgi.med3d.model.UserResponseDto;
import com.tgi.med3d.repository.HospitalRepository;
import com.tgi.med3d.repository.RoleRepository;
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
	RoleRepository roleMasterRepository;

	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	HospitalRepository hospitalDetailsRepository;

	 @Override
	public GenericResponse getAllHospital() {
		List<Hospital> hospitalDetailsList = hospitalDetailsRepository.findAll();
		if (hospitalDetailsList.size() > 0) {
			
			hospitalDetailsList.removeIf((Hospital Hd)-> Hd.isHospitalStatus()==false);

			return Library.getSuccessfulResponse(hospitalDetailsList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			throw new RecordNotFoundException();
		}
	}	
//		private HospitalResponseDto convertUserEntityToDto(User user) {		
//			HospitalResponseDto hospitalResponseDto = new HospitalResponseDto();
//			userResponseDto.setId(user.getId());
//			userResponseDto.setUserName(user.getUserName());
//			userResponseDto.setPhoneNumber(user.getPhoneNumber());
//			userResponseDto.setStatus(user.isStatus());
//			if (user != null && user.getId() != null && user.getRole() != null) {
//				userResponseDto.setRoleName(user.getRole().getRoleName());
//			}
//
//			return userResponseDto;
//		}
			
	@SuppressWarnings("unused")
	public GenericResponse addHospital(HospitalRequestDto hospitalRequestDto) {

		HospitalValidator.createHospitalValidator(hospitalRequestDto);
		isUserExists(hospitalRequestDto.getUserName());

		if (hospitalRequestDto != null) {
			User user = new User();
			
			/* user save starts */
			// user 
			user.setUserName(hospitalRequestDto.getUserName());
			user.setStatus(true);
			user.setRole(roleMasterRepository.getById(hospitalRequestDto.getRoleId()));
			if (user.getPassword() == null) {
				String newPasswordEnc = (hospitalRequestDto.getPassword().trim());
				
				user.setPassword(passwordEncoder.encode(newPasswordEnc));
			}			
			//user
			// hospital details
			Hospital hospitalDetails = new Hospital();
			pouplateHospitalDetails(user,hospitalRequestDto,hospitalDetails);
			// hospital details
			
			userRepository.save(user);
			return Library.getSuccessfulResponse(hospitalDetails,
					ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_CREATED);
						
		} else {
			throw new InvalidDataValidation();
		}
	}

	 private void pouplateHospitalDetails(User userMaster, HospitalRequestDto
			 hospitalRequestDto,Hospital hospitalDetails) 
	 { 
			  hospitalDetails.setHospitalName(hospitalRequestDto.getHospitalDetails().getHospitalName());
			 hospitalDetails.setHospitalStatus(hospitalRequestDto.getHospitalDetails().isHospitalStatus());
			 hospitalDetails.setAddress1(hospitalRequestDto.getHospitalDetails().getAddress1());
			 hospitalDetails.setAddress2(hospitalRequestDto.getHospitalDetails().getAddress2());
			 hospitalDetails.setContactNumber(hospitalRequestDto.getHospitalDetails().getContactNumber());
			 hospitalDetails.setHospitalLogo(hospitalRequestDto.getHospitalDetails().getHospitalLogo());
			 hospitalDetails.setHospitalDescription(hospitalRequestDto.getHospitalDetails().getHospitalDescription());

			  userMaster.setHospital(hospitalDetails);
			  
			  }
	public GenericResponse updateHospital(HospitalRequestDto hospitalRequestDto) {
		HospitalValidator.createHospitalValidator(hospitalRequestDto);

		if (hospitalRequestDto != null && hospitalRequestDto.getId() != null) {
			User user = userRepository.getById(hospitalRequestDto.getId());		

			if (user != null && user.getId() != null) {
				
			Hospital hospitalDetails = user.getHospital();
				
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
		Hospital hospitalDetails = hospitalDetailsRepository.getById(id);
		if (hospitalDetails != null && hospitalDetails.getId() != null) {
			
			//Hospital Inactive
			hospitalDetails.setHospitalStatus(false);
			hospitalDetailsRepository.save(hospitalDetails);
			//Hospital Inactive
			
			//Hospital users Inactive
			List<User> userList = userRepository.getUserByHospitalDetailsId(hospitalDetails.getId());
			if (userList.size() > 0) {
				userList.forEach(um -> {
					um.setStatus(false);
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

@Override
public GenericResponse searchHospital(String search, int pageNo, int pageSize) {
	if(pageNo>=0 &&  pageSize>0) {
		Pageable pageableRequest = PageRequest.of(pageNo,pageSize,Sort.Direction.ASC,"id");
		Page<Hospital> hp;
		if(search == null)
			hp = hospitalDetailsRepository.findAll(pageableRequest);
		else
		 hp = hospitalDetailsRepository.findByHospitalName(search, pageableRequest);
	if(hp!=null) {		
		PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO();
		paginationResponseDTO.setContents(hp.getContent());
		paginationResponseDTO.setNumberOfElements(hp.getNumberOfElements());
		paginationResponseDTO.setTotalElements(hp.getTotalElements());
		paginationResponseDTO.setTotalPages(hp.getTotalPages());
		return Library.getSuccessfulResponse(paginationResponseDTO, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
				ErrorMessages.RECORED_FOUND);
	}
	else {
		throw new RecordNotFoundException();
	}
	}
	else 
		throw new InvalidDataValidation();
	
}


	}


