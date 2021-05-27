package com.tgi.med3d.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tgi.med3d.model.User;


public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query("select u from User u where u.id=:id ")
	User getById(@Param("id") Long id);
	
	User findByUserName(String userName);
	
	@Query("select u from User u where u.hospitalDetails.id=:hospital_id")
	List<User> getUserByHospitalDetailsId(@Param("hospital_id") Long hospitalId);


}
