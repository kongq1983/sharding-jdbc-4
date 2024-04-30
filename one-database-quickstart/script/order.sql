
CREATE DATABASE IF NOT EXISTS `sharding-db` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

CREATE TABLE `t_order` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS t_order_202404 LIKE t_order;
CREATE TABLE IF NOT EXISTS t_order_202405 LIKE t_order;
CREATE TABLE IF NOT EXISTS t_order_202406 LIKE t_order;








