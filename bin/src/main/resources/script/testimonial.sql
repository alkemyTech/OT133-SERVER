CREATE TABLE testimonials(
testimonial_id VARCHAR(255) NOT NULL auto_increment,
name VARCHAR(255) NOT null,
image varchar(255) NULL,
content varchar(255) NULL,
softDelete bit,
timestamps datetime NULL DEFAULT CURRENT_TIMESTAMP
);