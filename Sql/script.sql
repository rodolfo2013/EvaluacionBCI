CREATE TABLE users (
   uuid VARCHAR(50) NOT NULL,
   name VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL,
   password VARCHAR(50) NOT NULL,
   created DATETIME,
   modified DATETIME,
   last_login DATETIME,
   token VARCHAR(1000),
   isactive BIT,
   PRIMARY KEY (uuid)
);



CREATE TABLE phones(
 number VARCHAR(50) NOT NULL,
 citycode VARCHAR(3) NOT NULL,
 contrycode VARCHAR(3) NOT NULL,
 uuid VARCHAR(50) NOT NULL,
 PRIMARY KEY (number),
 FOREIGN KEY (uuid) REFERENCES users(uuid)
);