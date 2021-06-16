package com.tgi.med3d.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.exception.InvalidDataValidation;
import com.tgi.med3d.exception.RecordNotFoundException;
import com.tgi.med3d.model.Hospital;
import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.model.HospitalResponseDto;
import com.tgi.med3d.model.PaginationResponseDTO;
import com.tgi.med3d.model.User;
import com.tgi.med3d.repository.HospitalRepository;
import com.tgi.med3d.repository.RoleRepository;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;
import com.tgi.med3d.validation.HospitalValidator;

@Service
public class HospitalServiceImpl implements HospitalService  {
	
	private static final Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

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
		 logger.debug("getAllHospital starts");
		List<Hospital> hospitalDetailsList = hospitalDetailsRepository.findAll();
		if (hospitalDetailsList.size() > 0) {
			
			hospitalDetailsList.removeIf((Hospital Hd)-> Hd.isHospitalStatus()==false);			
			
		} else {
			logger.error("No Record Found");
			throw new RecordNotFoundException();
		}
		logger.debug("getAllHospital ends");
		return Library.getSuccessfulResponse(hospitalDetailsList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
				ErrorMessages.RECORED_FOUND);
		
	}	
		private HospitalResponseDto convertUserEntityToDto(User user, Hospital hospital) {		
			HospitalResponseDto hospitalResponseDto = new HospitalResponseDto();
			hospitalResponseDto.setHospitalId(user.getHospital().getId());
			hospitalResponseDto.setUserName(user.getUserName());
			hospitalResponseDto.setHospitalName(hospital.getHospitalName());
			hospitalResponseDto.setHospitalStatus(hospital.isHospitalStatus());
			hospitalResponseDto.setAddress1(hospital.getAddress1());
			hospitalResponseDto.setAddress2(hospital.getAddress2());
			hospitalResponseDto.setContactNumber(hospital.getContactNumber());
			if (user != null && user.getId() != null && user.getRole() != null) {
				hospitalResponseDto.setRoleName(user.getRole().getRoleName());
			}

			return hospitalResponseDto;
		}
			
	@SuppressWarnings("unused")
	public GenericResponse addHospital(HospitalRequestDto hospitalRequestDto) {
		logger.debug("addHospital starts");
		HospitalValidator.createHospitalValidator(hospitalRequestDto);
		isUserExists(hospitalRequestDto.getUserName());
		Hospital hospitalDetails = new Hospital();
		User user = new User();

		if (hospitalRequestDto != null) {
			
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
			pouplateHospitalDetails(user,hospitalRequestDto,hospitalDetails);
			// hospital details
			
			userRepository.save(user);
			
						
		} else {
			logger.error("Invalid data");
			throw new InvalidDataValidation();
		}
		logger.debug("addHospital ends");
		return Library.getSuccessfulResponse(convertUserEntityToDto(user,hospitalDetails),
				ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_CREATED);
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
		logger.debug("updateHospital starts");
		HospitalValidator.createHospitalValidator(hospitalRequestDto);

		if (hospitalRequestDto != null && hospitalRequestDto.getId() != null) {
			User user = userRepository.getById(hospitalRequestDto.getId());		

			if (user != null && user.getId() != null) {
				
			Hospital hospitalDetails = user.getHospital();
				
			pouplateHospitalDetails(user,hospitalRequestDto,hospitalDetails);
						
			userRepository.save(user);
			logger.debug("updateHospital ends");
			return Library.getSuccessfulResponse(convertUserEntityToDto(user,hospitalDetails),
						ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_UPDATED);
			} else {
				logger.error("User Not Found");
				throw new RecordNotFoundException("User is not available");
			}
			
		} else {
			logger.error("Record Not Found");
			throw new RecordNotFoundException();
		}
	}


	public GenericResponse deleteHospital(Long id) {
		logger.debug("deleteHospital starts");
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

			
		} else {
			logger.error("Record Not Found");
			throw new RecordNotFoundException();
		}
		logger.debug("deleteHospital ends");
		return Library.getSuccessfulResponse(null,
				ErrorCode.SUCCESS_RESPONSE.getErrorCode(), ErrorMessages.RECORED_DELETED);
	}
	
private void isUserExists(String userName) {
		
		User user= userRepository.findByUserName(userName);
		if(null!=user)
			throw new InvalidDataValidation("User Name already Exists");
		
	}

@Override
public GenericResponse searchHospital(String search, int pageNo, int pageSize) {
	logger.debug("searchHospital starts");
	PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO();
	if(pageNo>=0 &&  pageSize>0) {
		Pageable pageableRequest = PageRequest.of(pageNo,pageSize,Sort.Direction.ASC,"id");
		Page<Object> hp;
		if(search == null)
			hp = hospitalDetailsRepository.searchAllHospital(pageableRequest);
		else
		 hp = hospitalDetailsRepository.findByHospitalName(search, pageableRequest);
	if(hp!=null) {		
		paginationResponseDTO.setContents(convertObjectEntityToDto(hp.getContent()));
		paginationResponseDTO.setNumberOfElements(hp.getNumberOfElements());
		paginationResponseDTO.setTotalElements(hp.getTotalElements());
		paginationResponseDTO.setTotalPages(hp.getTotalPages());
	
	}
	else {
		logger.error("Record Not Found");
		throw new RecordNotFoundException();
	}
	}
	else {
		logger.error("Invalid data");
		throw new InvalidDataValidation();
	}
	logger.debug("searchHospital ends");
	return Library.getSuccessfulResponse(paginationResponseDTO, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
			ErrorMessages.RECORED_FOUND);
}

private List<Object> convertObjectEntityToDto(List<Object> obj) {		
	List<Object> objList = new ArrayList<Object>();
	
		obj.forEach(o->{
			HospitalResponseDto hospitalResponseDto= new HospitalResponseDto();
			User user = (User)o ;
			//Hospital hospital = (Hospital)obj.get(1);
			hospitalResponseDto.setHospitalId(user.getHospital().getId());
			hospitalResponseDto.setUserName(user.getUserName());
			hospitalResponseDto.setHospitalName(user.getHospital().getHospitalName());
			hospitalResponseDto.setHospitalStatus(user.getHospital().isHospitalStatus());
			hospitalResponseDto.setAddress1(user.getHospital().getAddress1());
			hospitalResponseDto.setAddress2(user.getHospital().getAddress2());
			hospitalResponseDto.setContactNumber(user.getHospital().getContactNumber());
			if (user != null && user.getId() != null && user.getRole() != null) {
				hospitalResponseDto.setRoleName(user.getRole().getRoleName());
			}
			objList.add(hospitalResponseDto);
		});		

	return objList;
}


	}


