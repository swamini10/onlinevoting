CREATE TABLE `role_feature_mapping` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `feature_id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `feature_id` (`feature_id`),
  CONSTRAINT `role_feature_mapping_ibfk_11` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_feature_mapping_ibfk_21` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`)
);