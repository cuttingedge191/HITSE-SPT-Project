CREATE TABLE `inventory_transaction` (
  `iti_id` int NOT NULL AUTO_INCREMENT,
  `u_id` int NOT NULL,
  `i_id_s` int NOT NULL,
  `quantity` int NOT NULL,
  `il_id_d` int NOT NULL,
  PRIMARY KEY (`iti_id`),
  UNIQUE KEY `iti_id_UNIQUE` (`iti_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
