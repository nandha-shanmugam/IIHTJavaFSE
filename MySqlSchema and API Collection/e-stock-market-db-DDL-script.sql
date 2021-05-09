

CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(45) DEFAULT NULL,
  `company_name` varchar(45) NOT NULL,
  `company_ceo` varchar(45) NOT NULL,
  `company_turnover` float NOT NULL COMMENT 'Company turn over value should be in Cr.',
  `company_website` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_website_UNIQUE` (`company_website`),
  UNIQUE KEY `company_code_UNIQUE` (`company_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `stock_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `price` float DEFAULT NULL COMMENT 'Price should be float value and it should be in Crore.',
  `stock_created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'stock reated date is entry date time.',
  PRIMARY KEY (`id`),
  KEY `company_company_id_foreign_key_idx` (`company_id`),
  CONSTRAINT `company_company_id_foreign_key` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
