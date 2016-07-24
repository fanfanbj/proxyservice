#-------------------------------------------------
# proxyservice initial sql script
#-------------------------------------------------
create database proxyservice;

use proxyservice;

create table service(
   service_id INT NOT NULL AUTO_INCREMENT,
   service_name  VARCHAR(100) NOT NULL,
   image_name  VARCHAR(100) NOT NULL,
   service_tag 	 VARCHAR(40) NOT NULL,
   PRIMARY KEY ( service_id )
);

create table service_port(
   service_id  VARCHAR(40) NOT NULL,
   port	  VARCHAR(40) NOT NULL	
);
