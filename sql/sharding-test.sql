-- CREATE DATABASE IF NOT EXISTS sharding-test DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS `sharding-test` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

use `sharding-test`;

CREATE TABLE IF NOT EXISTS `account_0` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `account_1` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;