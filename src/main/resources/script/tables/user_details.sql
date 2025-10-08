CREATE TABLE `user_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `aadhar_number` bigint NOT NULL,
  `address_id` bigint NOT NULL,
  `dob` date NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `photo` longblob,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);