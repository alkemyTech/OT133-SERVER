CREATE TABLE users(
	id VARCHAR(255),
	first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    photo VARCHAR(100) NULL,
    role_id INTEGER,
    timestamps TIMESTAMP,
    softDelete BIT NOT NULL DEFAULT 0,
    CONSTRAINT pk_users PRIMARY KEY(id),
	CONSTRAINT fk_user_role FOREIGN KEY(role_id) REFERENCES Roles(id)
);