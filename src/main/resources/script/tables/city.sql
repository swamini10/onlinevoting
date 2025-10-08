
CREATE TABLE city (
  id bigint NOT NULL ,
  name VARCHAR(50) UNIQUE NOT NULL,  
  state_id bigint NOT NULL,
  is_active bit(1) DEFAULT 1  ,
  KEY `state_id` (`state_id`),
   PRIMARY KEY (`id`),
 CONSTRAINT `state_id` FOREIGN KEY (`state_id`) REFERENCES `states` (`id`)
);
