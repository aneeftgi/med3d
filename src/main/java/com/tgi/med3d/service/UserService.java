package com.tgi.med3d.service;

import com.tgi.med3d.model.UserRequestDto;
import com.tgi.med3d.utility.GenericResponse;

public interface UserService {
	
	public GenericResponse deleteUser(Long id);
	
	public GenericResponse updateUser(UserRequestDto userRequestDto);
	
	public GenericResponse addUser(UserRequestDto userRequestDto);
	
	public GenericResponse getUserByHospitalId(Long hospitalId);
	
	public GenericResponse getAllUser();

}
