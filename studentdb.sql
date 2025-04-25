
CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    roll_no VARCHAR(20),
    course VARCHAR(100),
    marks INT
);
