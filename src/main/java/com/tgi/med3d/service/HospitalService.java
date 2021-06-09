package com.tgi.med3d.service;

import com.tgi.med3d.model.HospitalRequestDto;
import com.tgi.med3d.utility.GenericResponse;


public interface HospitalService {
	
	public GenericResponse getAllHospital();
	
	public GenericResponse addHospital(HospitalRequestDto hospitalRequestDto);

	public GenericResponse updateHospital(HospitalRequestDto hospitalRequestDto) ;
	
	public GenericResponse deleteHospital(Long id) ;

	public GenericResponse searchHospital(String search, int pageNo, int pageSize);

}
