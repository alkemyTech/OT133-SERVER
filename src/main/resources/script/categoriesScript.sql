CREATE TABLE `categories` (
  `category_id` varchar(250) NOT NULL,
  `name` varchar(250) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `image` varchar(250) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `timestamps` timestamp(6) NOT NULL,
  PRIMARY KEY (`category_id`)
);