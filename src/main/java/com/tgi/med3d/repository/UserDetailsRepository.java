package com.tgi.med3d.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tgi.med3d.model.User;
import com.tgi.med3d.model.UserDetails;


public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	
	
}
