CREATE TABLE country (
  id bigint NOT NULL PRIMARY KEY  ,
  name VARCHAR(50) UNIQUE NOT NULL,    
  is_active bit(1) DEFAULT 1,
  description VARCHAR(255) ,         
);