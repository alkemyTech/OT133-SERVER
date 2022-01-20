CREATE DATABASE grupo133;

CREATE TABLE news (
new_id int NOT NULL,
name VARCHAR(255) NOT NULL,
content TEXT NOT NULL,
image VARCHAR(255) NOT NULL,
date TIMESTAMP,
deleted BIT(1) NOT NULL,
PRIMARY KEY (newId),
FOREIGN KEY (category_id) REFERENCES categories(category_id)
);