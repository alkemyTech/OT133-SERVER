CREATE TABLE activities (
`activity_id` VARCHAR(255) NOT NULL,
`name` VARCHAR(255) NOT NULL,
`content` TEXT NOT NULL,
`image` VARCHAR(255) NOT NULL,
`timestamps` date,
`softDelete` BIT(1) NOT NULL
);