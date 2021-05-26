
CREATE DATABASE IF NOT EXISTS `sharding-test` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;




CREATE TABLE `t_order_202105` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_202106` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_202107` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_202108` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_202109` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_202110` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;









