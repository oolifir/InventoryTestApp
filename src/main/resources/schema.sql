DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS users;

CREATE TABLE flight (
    id INT AUTO_INCREMENT PRIMARY KEY,
    carrier_code VARCHAR(2) NOT NULL,
    flight_number VARCHAR(4) NOT NULL,
    flight_date DATE NOT NULL,
    origin_airport_code VARCHAR(3) NOT NULL,
    destination_airport_code VARCHAR(3) NOT NULL
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    role_name varchar(100) NOT NULL
);