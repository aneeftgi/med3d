package com.tgi.med3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tgi.med3d.model.HospitalDetails;
import com.tgi.med3d.model.User;

public interface HospitalDetailsRepository extends JpaRepository<HospitalDetails,Long>{
	@Query("select u from HospitalDetails u where u.id=:id ")
	HospitalDetails getById(@Param("id") Long id);
}
