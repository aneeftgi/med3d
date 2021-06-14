package com.tgi.med3d.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tgi.med3d.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query("select r from Role r where r.id=:id ")
	Role getById(@Param("id") Long id);


}
