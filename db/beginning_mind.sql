/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : beginning_mind

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-04-30 07:41:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `signer_type` int(11) DEFAULT NULL COMMENT '登录者类型（1买家 2卖家）',
  `signer_id` bigint(20) DEFAULT NULL COMMENT '登录者ID（逻辑外键）',
  `username` varchar(16) DEFAULT NULL COMMENT '“用户名”',
  `password` varchar(16) DEFAULT NULL COMMENT '密码',
  `enable_sign` tinyint(1) DEFAULT NULL COMMENT '能否登录',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='帐号（用于登录的信息）';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '2018-04-30 07:26:59', '2018-04-30 07:39:19', '0', '2', '1', 'fresh2018', '123456', '1');
INSERT INTO `account` VALUES ('2', '2018-04-30 07:26:59', '2018-04-30 07:39:19', '0', '2', '2', 'like_food', '123456', '1');
INSERT INTO `account` VALUES ('3', '2018-04-30 07:26:59', '2018-04-30 07:39:19', '0', '1', '1', 'smrs', '123456', '1');
INSERT INTO `account` VALUES ('4', '2018-04-30 07:26:59', '2018-04-30 07:39:19', '0', '1', '2', 'abe', '123456', '1');
INSERT INTO `account` VALUES ('5', '2018-04-30 07:27:00', '2018-04-30 07:39:20', '0', '1', '3', 'jeandiata', '123456', '1');
INSERT INTO `account` VALUES ('6', '2018-04-30 07:27:00', '2018-04-30 07:39:20', '0', '1', '4', 'dan', '123456', '1');

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
INSERT INTO `goods` VALUES ('1', '2018-04-30 07:13:39', '2018-04-30 07:18:32', '0', '西冷牛排', '1000', '390.00', '2350');
INSERT INTO `goods` VALUES ('2', '2018-04-30 07:13:39', '2018-04-30 07:19:18', '0', '黄油', '200', '23.80', '2816');
INSERT INTO `goods` VALUES ('3', '2018-04-30 07:13:40', '2018-04-30 07:20:35', '0', '可可豆', '1000', '235.00', '128');
INSERT INTO `goods` VALUES ('4', '2018-04-30 07:13:40', '2018-04-30 07:21:16', '0', '西兰花', '500', '7.50', '255');
INSERT INTO `goods` VALUES ('5', '2018-04-30 07:13:40', '2018-04-30 07:21:48', '0', '金枪鱼', '250', '388.00', '8672');
INSERT INTO `goods` VALUES ('6', '2018-04-30 07:13:40', '2018-04-30 07:22:21', '0', '薄荷', '30', '28.80', '14080');
INSERT INTO `goods` VALUES ('7', '2018-04-30 07:13:40', '2018-04-30 07:22:57', '0', '牛油果', '300', '69.80', '8365');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='卖家';

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
