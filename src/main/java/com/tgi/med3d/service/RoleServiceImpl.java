package com.tgi.med3d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.exception.RecordNotFoundException;
import com.tgi.med3d.model.RoleMaster;
import com.tgi.med3d.repository.RoleMasterRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleMasterRepository roleMasterRepository;
	

	public GenericResponse getAllRoles() {
		List<RoleMaster> roleMasterList =new ArrayList<RoleMaster>();
		roleMasterList.add(roleMasterRepository.getById((long) 2));
		roleMasterList.add(roleMasterRepository.getById((long) 3));

		if (roleMasterList.size() > 0) {
			
			return Library.getSuccessfulResponse(roleMasterList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
		} else {
			throw new RecordNotFoundException();
		}
	}





}