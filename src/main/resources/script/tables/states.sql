
CREATE TABLE states (
  id bigint NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) UNIQUE NOT NULL,  
  country_id bigint NOT NULL,
  is_active bit(1) DEFAULT 1  ,
  KEY `country_id` (`country_id`),
   PRIMARY KEY (`id`),
 CONSTRAINT `country_key` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
);
