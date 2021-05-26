package com.tgi.med3d.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tgi.med3d.model.RoleMaster;

@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {
	
	@Query("select r from RoleMaster r where r.id=:id ")
	RoleMaster getById(@Param("id") Long id);


}
