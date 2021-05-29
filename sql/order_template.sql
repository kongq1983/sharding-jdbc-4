CREATE DATABASE IF NOT EXISTS `sharding-test` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


CREATE TABLE `t_order_temp` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_detail_temp` (
  `id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `inventory_id` varchar(32) DEFAULT NULL,
  `unit_price` DECIMAL(18,6) NOT NULL,
  `price` DECIMAL(18,6) NOT NULL,
  `num` int NOT NULL,
  `sale_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_account` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_account_detail` (
  `id` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `address` varchar(64),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


insert into t_account(id,username,phone,province,createtime) values(1,'admin','1350001111','zj',NOW());
insert into t_account(id,username,phone,province,createtime) values(2,'king','1350002222','zj',NOW());

insert into t_account_detail(id,account_id,address) values(1,1,'hz.zj');
insert into t_account_detail(id,account_id,address) values(2,2,'zs.zj');