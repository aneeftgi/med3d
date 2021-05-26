-- med3d_user.user_role definition
use med3d_user;
 drop table if exists role_master;
 CREATE TABLE `role_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
 -- `role_code` varchar(255) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)  
 ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 INSERT INTO med3d_user.role_master (role_name) VALUES('SUPERADMIN');
  INSERT INTO med3d_user.role_master (role_name) VALUES('ADMIN');
   INSERT INTO med3d_user.role_master (role_name) VALUES('USER'); 
-- INSERT INTO med3d_user.user_role (role_code, role_name) VALUES('ROLE002', 'ADMIN');
-- INSERT INTO med3d_user.user_role (role_code, role_name) VALUES('ROLE003', 'USER');
 INSERT INTO med3d_user.user (user_name, password, phone_number, status, role_id) VALUES ('browns.tgi@gmail.com', '$2a$10$7zxuqZverau9tbJamI56iuRN3rBAom6kreLNJkdTd701hmz53r6Um', 95975, "Active", '1');
 -- INSERT INTO med3d_user.user_details (address_line_1, address_line_2, district_id, first_name, last_name, middle_name, nationality, salutation_id, state_id, taluk_id) VALUES('Pasumalai', 'Madurai', 1, 'brown', 'admin', 'super', 1, 1, 1, 1);



