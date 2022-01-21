CREATE TABLE IF NOT EXISTS `organizations` (
	`idOrganization` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(20) NOT NULL,
	`image` varchar(45) NOT NULL,
	`address` varchar(20) DEFAULT NULL,
	`phone` int(11) DEFAULT NULL,
	`email` varchar(45) NOT NULL,
	`welcomeText` text(50) NOT NULL,
	`aboutUsText` text(50) DEFAULT NULL,
	`timestamps` date,
	`softDelete` bit,
	PRIMARY KEY (`idOrganization`)
)