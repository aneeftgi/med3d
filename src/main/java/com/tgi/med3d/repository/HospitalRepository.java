package com.tgi.med3d.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tgi.med3d.model.Hospital;
import com.tgi.med3d.model.User;

public interface HospitalRepository extends JpaRepository<Hospital,Long>{
	
	@Query("select h from Hospital h where h.id=:id ")
	Hospital getById(@Param("id") Long id);	
	
	@Query("select count(*) as totalHospital, \r\n"
			+ "COALESCE(sum(case when h.hospitalStatus='Active' then 1 else 0 end),0) as activehospital,\r\n"
			+ "COALESCE(sum(case when h.hospitalStatus='Inactive' then 1 else 0 end),0) as inActivehospital \r\n"
			+ "from Hospital h")	
	List<Object[]> getAllHospitalCount();	
	
	@Query("select h from Hospital h where h.hospitalName like :hospitalName%")
	Page<Hospital> findByHospitalName(@Param("hospitalName") String hospitalName,Pageable pageable);
	
	Page<Hospital> findAll(Pageable pageable);
		
}
