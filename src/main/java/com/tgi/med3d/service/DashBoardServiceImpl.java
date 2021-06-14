package com.tgi.med3d.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgi.med3d.constant.ErrorCode;
import com.tgi.med3d.constant.ErrorMessages;
import com.tgi.med3d.exception.RecordNotFoundException;
import com.tgi.med3d.model.DashBoardDetails;
import com.tgi.med3d.model.Hospital;
import com.tgi.med3d.repository.HospitalRepository;
import com.tgi.med3d.repository.UserRepository;
import com.tgi.med3d.utility.GenericResponse;
import com.tgi.med3d.utility.Library;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DashBoardServiceImpl implements DashBoardService {
	
	@Autowired
	HospitalRepository hospitalDetailsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public GenericResponse superAdminDashBoard() {
		
		DashBoardDetails dashBoardDetails= new DashBoardDetails();
		List<Object[]>  userCount = userRepository.getAllUserCount();		
		if(userCount.size()>0) {
			userCount.forEach(record->{
				dashBoardDetails.setUserCount((long) record[0]);
				dashBoardDetails.setActiveUserCount((long)record[1]);
				dashBoardDetails.setInActiveUserCount((long)record[2]);
					
			});
		}

		List<Object[]>  hospitalCount = hospitalDetailsRepository.getAllHospitalCount();
		if(hospitalCount.size()>0) {
				hospitalCount.forEach(record->{
				dashBoardDetails.setHospitalCount((long)record[0]);
				dashBoardDetails.setActiveHospitalCount((long)record[1]);
				dashBoardDetails.setInActiveHospitalCount((long)record[2]);
			});
			}
		
			return Library.getSuccessfulResponse(dashBoardDetails, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);
	}

	@Override
	public GenericResponse hospitalDashBoard(Long hospitalId) {
		DashBoardDetails dashBoardDetails= new DashBoardDetails();

		Hospital hospitalDetails = hospitalDetailsRepository.getById(hospitalId);
		if(hospitalDetails!=null) {
			List<Object[]>  hospitalUserCount = userRepository.getHospitalUserCount(hospitalId);		
		
		if(hospitalUserCount.size()>0) {
			hospitalUserCount.forEach(record->{
			dashBoardDetails.setHospitalUserCount((long)record[0]);
			dashBoardDetails.setActiveHospitalUserCount((long)record[1]);
			dashBoardDetails.setInActiveHospitalUserCount((long)record[2]);
		});
		}
		
		return Library.getSuccessfulResponse(dashBoardDetails, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
			ErrorMessages.RECORED_FOUND);
		}
	else 
	throw new RecordNotFoundException();
	}


}
