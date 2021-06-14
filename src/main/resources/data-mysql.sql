-- med3d_user.user_role definition
use med3d_user;
 drop table if exists role;
 CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)  
 ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 INSERT INTO med3d_user.role (role_name) VALUES('SUPERADMIN');
  INSERT INTO med3d_user.role (role_name) VALUES('HOSPITALADMIN');
  INSERT INTO med3d_user.role (role_name) VALUES('ADMIN');
   INSERT INTO med3d_user.role (role_name) VALUES('USER'); 

 INSERT INTO med3d_user.user (user_name, password, phone_number, status, role_id) VALUES ('browns.tgi@gmail.com', '$2a$10$7zxuqZverau9tbJamI56iuRN3rBAom6kreLNJkdTd701hmz53r6Um', 95975, "Active", '1');



