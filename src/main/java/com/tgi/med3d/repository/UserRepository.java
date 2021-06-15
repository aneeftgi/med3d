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
	
	@Query("select u from User u where u.hospital.id=:hospital_id")
	List<User> getUserByHospitalDetailsId(@Param("hospital_id") Long hospitalId);
	
	@Query("select count(*) as totalUsers, \r\n"
			+ "COALESCE(sum(case when u.status='1' then 1 else 0 end),0) as activeUsers,\r\n"
			+ "COALESCE(sum(case when u.status='0' then 1 else 0 end),0) as inActiveUsers \r\n"
			+ "from User u")	
	List<Object[]> getAllUserCount();	
	
	@Query("select count(*) as totoalUsers, \r\n"
			+ "COALESCE(sum(case when u.status='1' then 1 else 0 end),0) as activeUsers,\r\n"
			+ "COALESCE(sum(case when u.status='0' then 1 else 0 end),0) as inActiveUsers \r\n"
			+ "from User u where u.hospital.id=:hospital_id")
	List<Object[]> getHospitalUserCount(@Param("hospital_id") Long hospitalId);

}
