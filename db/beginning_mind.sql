/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : beginning_mind

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-05-04 15:45:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for buyer
-- ----------------------------
DROP TABLE IF EXISTS `buyer`;
CREATE TABLE `buyer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `wallet_balance` decimal(10,2) DEFAULT NULL COMMENT '钱包余额',
  `vip_level` int(11) DEFAULT NULL COMMENT 'VIP等级（最低0，代表非VIP）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='买家';

-- ----------------------------
-- Records of buyer
-- ----------------------------
INSERT INTO `buyer` VALUES ('1', '2018-04-30 07:27:24', '2018-04-30 07:35:08', '0', 'Shimarisu', '9999.00', '1');
INSERT INTO `buyer` VALUES ('2', '2018-04-30 07:27:26', '2018-04-30 07:35:08', '0', 'Abe', '9998.00', '0');
INSERT INTO `buyer` VALUES ('3', '2018-04-30 07:34:48', '2018-04-30 07:35:08', '0', 'jeandiata', '9997.00', '0');
INSERT INTO `buyer` VALUES ('4', '2018-04-30 07:34:49', '2018-04-30 07:35:08', '0', 'Dan', '9996.00', '0');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `net_weight` int(11) DEFAULT NULL COMMENT '净重（单位g）',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `stock_balance` int(11) DEFAULT NULL COMMENT '库存余量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '2018-04-30 07:13:39', '2018-04-30 08:25:15', '0', '111', '1000', '390.00', '2350');
INSERT INTO `goods` VALUES ('2', '2018-04-30 07:13:39', '2018-04-30 07:19:18', '0', '黄油', '200', '23.80', '2816');
INSERT INTO `goods` VALUES ('3', '2018-04-30 07:13:40', '2018-04-30 07:20:35', '0', '可可豆', '1000', '235.00', '128');
INSERT INTO `goods` VALUES ('4', '2018-04-30 07:13:40', '2018-04-30 07:21:16', '0', '西兰花', '500', '7.50', '255');
INSERT INTO `goods` VALUES ('5', '2018-04-30 07:13:40', '2018-04-30 07:21:48', '0', '金枪鱼', '250', '388.00', '8672');
INSERT INTO `goods` VALUES ('6', '2018-04-30 07:13:40', '2018-04-30 07:22:21', '0', '薄荷', '30', '28.80', '14080');
INSERT INTO `goods` VALUES ('7', '2018-04-30 07:13:40', '2018-04-30 07:22:57', '0', '牛油果', '300', '69.80', '8365');

-- ----------------------------
-- Table structure for security_account
-- ----------------------------
DROP TABLE IF EXISTS `security_account`;
CREATE TABLE `security_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `signer_type` int(11) DEFAULT NULL COMMENT '登录者类型（1买家 2卖家）',
  `signer_id` bigint(20) DEFAULT NULL COMMENT '登录者ID（逻辑外键）',
  `username` varchar(16) DEFAULT NULL COMMENT '“用户名”',
  `password` char(128) DEFAULT NULL COMMENT '密码',
  `salt` char(32) DEFAULT NULL COMMENT '盐',
  `enable_sign` tinyint(1) DEFAULT NULL COMMENT '能否登录',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='帐号（用于登录的信息）';

-- ----------------------------
-- Records of security_account
-- ----------------------------
INSERT INTO `security_account` VALUES ('1', '2018-04-30 07:26:59', '2018-05-04 15:24:26', '0', '2', '1', 'fresh2018', 'a74114ebe219ec5282fe2184f688c4a72c57887e635c6f9dc41c7cc80826725562de3d2c426c8bd0c69443be8416383a6396d090b6edbbd809eb3e242499489e', '=m=/B@M/lWi:0~C\'&]J,G0NJ(r]G:P+<', '1');
INSERT INTO `security_account` VALUES ('2', '2018-04-30 07:26:59', '2018-05-04 15:24:26', '0', '2', '2', 'like_food', 'cb66485b929ecc40e0313c0f2f6ede165db9d749f359f465adf86adbc3362170616b1b2d30990e9987566e407a896cdfe08007da0114016f71e42b0a3dbb68f3', '#7hcw~={BR[s@gXZ,{e91b^;p^+l\\]aX', '1');
INSERT INTO `security_account` VALUES ('3', '2018-04-30 07:26:59', '2018-05-04 15:24:26', '0', '1', '1', 'smrs', '0fe579a13075498b2bf7fd1992b9c2b3f3b59f5a1afcca1f532810470e552039eb3c9a2453f74dcdd4a38880b32db61010587a82d4591e24c94eeafec8023894', 'i_cu3c7N3^XEj+*zPwjN%pWk^:baG1W5', '1');
INSERT INTO `security_account` VALUES ('4', '2018-04-30 07:26:59', '2018-05-04 15:24:26', '0', '1', '2', 'abe', 'ed2e1878019be744c3da9a3da00755b790ee6eac4a633a35e2c0f575f614adfc8d504e4b7c004e43c2d4383bb695b542b32cc7c5316898d9430b1b5bbb309609', '3><^A\\zBCF?S./F*B0Zr[\'kOF\"GFWZ-P', '1');
INSERT INTO `security_account` VALUES ('5', '2018-04-30 07:27:00', '2018-05-04 15:24:26', '0', '1', '3', 'jeandiata', '52b42cfe2df957425eb158c96317400e6e50f9e2243d8712c2fc3aa9d337fe1b0ad76f7b7046cb67aacf9e7185da09004d70c28694fa5f52afe9aba0af8b564c', 'd33Wj;~#CDY}0F99w&V{b[=yon7zN6aR', '1');
INSERT INTO `security_account` VALUES ('6', '2018-04-30 07:27:00', '2018-05-04 15:24:26', '0', '1', '4', 'dan', '44f463a626487dfc7eb131747baef7914581af4adff6190d953c73abcd4288f5b77797fa7bf32032af8b4282c662a5c676c98c78dc39798d47c53a246859ab8f', 'XK2a>^d0Tt\'1&emgFy}\'(h/eCn\'R@M\\[', '1');

-- ----------------------------
-- Table structure for security_accounts2roles
-- ----------------------------
DROP TABLE IF EXISTS `security_accounts2roles`;
CREATE TABLE `security_accounts2roles` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `account_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='帐号与权限的关联';

-- ----------------------------
-- Records of security_accounts2roles
-- ----------------------------

-- ----------------------------
-- Table structure for security_permission
-- ----------------------------
DROP TABLE IF EXISTS `security_permission`;
CREATE TABLE `security_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `requires_permissions_mapping` varchar(255) DEFAULT NULL COMMENT '请求方法@RequiresPermissions注解属性的映射',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Records of security_permission
-- ----------------------------

-- ----------------------------
-- Table structure for security_role
-- ----------------------------
DROP TABLE IF EXISTS `security_role`;
CREATE TABLE `security_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of security_role
-- ----------------------------

-- ----------------------------
-- Table structure for security_roles2permissions
-- ----------------------------
DROP TABLE IF EXISTS `security_roles2permissions`;
CREATE TABLE `security_roles2permissions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与权限的关联';

-- ----------------------------
-- Records of security_roles2permissions
-- ----------------------------

-- ----------------------------
-- Table structure for seller
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='卖家';

-- ----------------------------
-- Records of seller
-- ----------------------------
INSERT INTO `seller` VALUES ('1', '2018-04-30 07:27:32', '2018-04-30 07:36:21', '0', 'FreshSeller');
INSERT INTO `seller` VALUES ('2', '2018-04-30 07:27:32', '2018-04-30 07:37:21', '0', 'ProcessedFoodSeller');

-- ----------------------------
-- Table structure for __template
-- ----------------------------
DROP TABLE IF EXISTS `__template`;
CREATE TABLE `__template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of __template
-- ----------------------------

-- ----------------------------
-- Table structure for __tiny
-- ----------------------------
DROP TABLE IF EXISTS `__tiny`;
CREATE TABLE `__tiny` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of __tiny
-- ----------------------------
