-- med3d_user.hospital definition
use med3d_user

drop table hospital 
CREATE TABLE `hospital` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `address_1` varchar(255) DEFAULT NULL,
  `address_2` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `hospital_desc` varchar(255) DEFAULT NULL,
  `hospital_logo` varchar(255) DEFAULT NULL,
  `hospital_name` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

-- med3d_user.`role` definition

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- med3d_user.`user` definition
drop table `user` 
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `address_1` varchar(255) DEFAULT NULL,
  `address_2` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `hospital_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmo2mvccns3uvgpuk7vgrtcjq9` (`hospital_id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

 INSERT INTO med3d_user.role (role_name) VALUES('SUPERADMIN');
  INSERT INTO med3d_user.role (role_name) VALUES('HOSPITALADMIN');
  INSERT INTO med3d_user.role (role_name) VALUES('ADMIN');
   INSERT INTO med3d_user.role (role_name) VALUES('USER'); 
   
  INSERT INTO med3d_user.`user` (password, phone_number, status, user_name,role_id) 
 VALUES('$2a$10$7zxuqZverau9tbJamI56iuRN3rBAom6kreLNJkdTd701hmz53r6Um', '+91-9597545243', 1, 'browns.tgi@gmail.com','1');