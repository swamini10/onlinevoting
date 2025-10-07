CREATE TABLE `user_role_mapping` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_mapping_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_detail` (`id`),
  CONSTRAINT `user_role_mapping_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);