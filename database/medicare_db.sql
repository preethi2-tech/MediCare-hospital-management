CREATE DATABASE IF NOT EXISTS medicare_db;
USE medicare_db;
CREATE TABLE IF NOT EXISTS users (user_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL, salt VARCHAR(64) NOT NULL, role ENUM('ADMIN','DOCTOR','RECEPTIONIST') NOT NULL, full_name VARCHAR(100) NOT NULL, email VARCHAR(100), phone VARCHAR(15), is_active TINYINT(1) NOT NULL DEFAULT 1, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
INSERT INTO users (username,password_hash,salt,role,full_name,email,phone) VALUES ('admin','lgOOOOTtzOGcN/urwUjAfhE7KLTdkDzbYv4l8SZF528=','Pu5JAGWFzLfch5pG0SkTGg==','ADMIN','System Administrator','admin@medicare.com','9000000001') ON DUPLICATE KEY UPDATE username=username;
