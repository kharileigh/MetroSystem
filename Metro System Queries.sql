-- CREATING THE DATABASE:
CREATE DATABASE MetroSystem;

-- USING THE 'MetroSystem' DATABASE:
use MetroSystem;

-- CREATING TABLES:
-- USER:
CREATE TABLE User(
userId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
userName VARCHAR(25) NOT NULL,
userPassword VARCHAR(25) NOT NULL,
userBalance DECIMAL(15, 2) NOT NULL
);

-- STATION:
CREATE TABLE Station(
stationId INT NOT NULL PRIMARY KEY,
stationName VARCHAR(25) NOT NULL
);

-- INSERTING DATA:
-- USER:
INSERT INTO User(userName, userPassword, userBalance)
VALUES("Alison", "hello", 5),
("Khari", "password", 7),
("Lidija", "station", 12),
("Priyanka", "wiley", 15),
("Steph", "edge", 19),
("Vic", "root", 20),
("Sam", "rock", 23);

-- STATION:
INSERT INTO Station
VALUES(1, "Bank"),
(2, "Lewisham"),
(3, "Croydon"),
(4, "Harlow"),
(5, "Euston");

-- SHOW DATA:
select * from User;
select * from Station;