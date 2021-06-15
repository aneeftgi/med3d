package com.tgi.med3d.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.exception.RecordNotFoundException;
import com.tgi.med3d.model.Role;
import com.tgi.med3d.repository.RoleRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RoleServiceImpl implements RoleService {
	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	
	@Autowired
	RoleRepository roleMasterRepository;
	

	public GenericResponse getAllRoles() {
		log.debug("getAllRoles starts");
		List<Role> roleMasterList =new ArrayList<Role>();
		roleMasterList.add(roleMasterRepository.getById((long) 2));
		roleMasterList.add(roleMasterRepository.getById((long) 3));
		roleMasterList.add(roleMasterRepository.getById((long) 4));

		if (roleMasterList.size() > 0) {
			log.debug("getAllRoles ends");
			return Library.getSuccessfulResponse(roleMasterList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			log.error("Record not found");
			throw new RecordNotFoundException();
		}
	}





}