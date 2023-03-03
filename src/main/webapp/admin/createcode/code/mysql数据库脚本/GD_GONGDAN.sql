
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `GD_GONGDAN`
-- ----------------------------
DROP TABLE IF EXISTS `GD_GONGDAN`;
CREATE TABLE `GD_GONGDAN` (
 		`GONGDAN_ID` varchar(100) NOT NULL,
		`TITLE` varchar(100) DEFAULT NULL COMMENT '标题',
		`CREATETIME` varchar(32) DEFAULT NULL COMMENT '创建时间',
  		PRIMARY KEY (`GONGDAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
