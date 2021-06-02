package com.tgi.med3d.service;

import com.tgi.med3d.utility.GenericResponse;

public interface DashBoardService {
	
	public GenericResponse superAdminDashBoard();
	
	public GenericResponse hospitalDashBoard(Long hospitalId) ;

}
