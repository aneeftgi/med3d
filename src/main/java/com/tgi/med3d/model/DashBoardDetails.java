package com.tgi.med3d.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
public class DashBoardDetails {
	
	private long hospitalCount ;
	private long activeHospitalCount ;
	private long inActiveHospitalCount ;
	
	private long userCount ;
	private long activeUserCount ;
	private long inActiveUserCount ;
	
	private long hospitalUserCount ;
	private long activeHospitalUserCount ;
	private long inActiveHospitalUserCount ;

}
