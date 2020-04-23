
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



insert into account(id,username,phone) values(1,'aaa','20011112221');
insert into account(id,username,phone) values(2,'bbb','20511112222');
insert into account(id,username,phone) values(3,'ccc','20511112223');


CREATE TABLE `dict` (
  `ID` varchar(32) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `sort` integer DEFAULT 0,
  `status` integer DEFAULT 1,
  `description` text,
  `ACTIVE` integer DEFAULT 1,
  PRIMARY KEY (`ID`),
  KEY `IDX_DICTIONARY` (`type`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 初始化数据
INSERT INTO `dict` VALUES (1, 'Status', '1', '启用', 1, 1, null, 1);
INSERT INTO `dict` VALUES (2, 'Status', '0', '停用', 2, 1, null, 1);
