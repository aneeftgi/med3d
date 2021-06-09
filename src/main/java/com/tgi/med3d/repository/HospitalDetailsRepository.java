package com.tgi.med3d.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tgi.med3d.model.HospitalDetails;
import com.tgi.med3d.model.User;

public interface HospitalDetailsRepository extends JpaRepository<HospitalDetails,Long>{
	
	@Query("select h from HospitalDetails h where h.id=:id ")
	HospitalDetails getById(@Param("id") Long id);	
	
	@Query("select count(*) as totalHospital, \r\n"
			+ "COALESCE(sum(case when h.hospitalStatus='Active' then 1 else 0 end),0) as activehospital,\r\n"
			+ "COALESCE(sum(case when h.hospitalStatus='Inactive' then 1 else 0 end),0) as inActivehospital \r\n"
			+ "from HospitalDetails h")	
	List<Object[]> getAllHospitalCount();	
	
	@Query("select h from HospitalDetails h where h.hospitalName like :hospitalName%")
	Page<HospitalDetails> findByHospitalName(@Param("hospitalName") String hospitalName,Pageable pageable);
	
	Page<HospitalDetails> findAll(Pageable pageable);
		
}
