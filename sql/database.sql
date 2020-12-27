CREATE DATABASE IF NOT EXISTS classlist DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

CREATE USER IF NOT EXISTS 'dbuser'@'localhost' IDENTIFIED BY '123456';

GRANT ALL ON classlist.* TO 'dbuser'@'localhost';