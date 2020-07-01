use testForBatis;

CREATE TABLE empuser (
id varchar(64) NOT NULL,
password varchar(255) DEFAULT NULL,
username varchar(255) DEFAULT NULL,
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2;

INSERT INTO testForBatis.empuser (id, password, username) VALUES ('1', '123', 'dyy');
INSERT INTO testForBatis.empuser (id, password, username) VALUES ('2', 'asd', 'ben');
INSERT INTO testForBatis.empuser (id, password, username) VALUES ('3', '12f', 'alex');