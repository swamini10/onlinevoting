CREATE TABLE country (
  id bigint NOT NULL ,
  name VARCHAR(50) UNIQUE NOT NULL,    
  is_active bit(1) DEFAULT 1,
  description VARCHAR(255) ,
  id bigint PRIMARY KEY             
);