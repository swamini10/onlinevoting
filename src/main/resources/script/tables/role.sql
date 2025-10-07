CREATE TABLE role (
  id bigint NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) UNIQUE NOT NULL,    
  is_active bit(1) DEFAULT 1,
  description VARCHAR(255)              
);