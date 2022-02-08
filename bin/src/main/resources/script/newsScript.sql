CREATE TABLE news (
new_id VARCHAR(255) NOT NULL,
name VARCHAR(255) NOT NULL,
content TEXT NOT NULL,
image VARCHAR(255) NOT NULL,
timestamp DATETIME,
softDelete BIT(1) NOT NULL,
category_id VARCHAR(255) NOT NULL,
PRIMARY KEY (new_id),
FOREIGN KEY (category_id) REFERENCES categories(category_id)
);