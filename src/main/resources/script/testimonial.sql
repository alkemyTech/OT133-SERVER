CREATE TABLE testimonials(
testimonial_id VARCHAR(255) NOT NULL auto_increment,
name VARCHAR(255) NOT null,
image varchar(255) NULL,
content varchar(255) NULL,
deleted bit,
date datetime NULL DEFAULT CURRENT_TIMESTAMP
);