/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : beginning_mind

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-05-27 17:52:51
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
INSERT INTO `buyer` VALUES ('1', '2018-04-30 07:27:24', '2018-05-26 10:40:21', '0', 'Shimarisu', '9998.00', '1');
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
INSERT INTO `goods` VALUES ('1', '2018-04-30 07:13:39', '2018-05-26 20:41:07', '0', '西冷牛排', '1000', '390.00', '2350');
INSERT INTO `goods` VALUES ('2', '2018-04-30 07:13:39', '2018-04-30 07:19:18', '0', '黄油', '200', '23.80', '2816');
INSERT INTO `goods` VALUES ('3', '2018-04-30 07:13:40', '2018-04-30 07:20:35', '0', '可可豆', '1000', '235.00', '128');
INSERT INTO `goods` VALUES ('4', '2018-04-30 07:13:40', '2018-04-30 07:21:16', '0', '西兰花', '500', '7.50', '255');
INSERT INTO `goods` VALUES ('5', '2018-04-30 07:13:40', '2018-04-30 07:21:48', '0', '金枪鱼', '250', '388.00', '8672');
INSERT INTO `goods` VALUES ('6', '2018-04-30 07:13:40', '2018-04-30 07:22:21', '0', '薄荷', '30', '28.80', '14080');
INSERT INTO `goods` VALUES ('7', '2018-04-30 07:13:40', '2018-04-30 07:22:57', '0', '牛油果', '300', '69.80', '8365');

-- ----------------------------
-- Table structure for security_user
-- ----------------------------
DROP TABLE IF EXISTS `security_user`;
CREATE TABLE `security_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `username` varchar(16) DEFAULT NULL COMMENT '“用户名”',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT 'E-Mail',
  `password` char(128) DEFAULT NULL COMMENT '密码',
  `salt` char(32) DEFAULT NULL COMMENT '盐',
  `enable_sign` tinyint(1) DEFAULT NULL COMMENT '能否登录',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `headerUrl` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `sex` enum('male','female') DEFAULT NULL COMMENT '性别',
  `province` char(6) DEFAULT NULL COMMENT '联系地址（省）',
  `city` char(6) DEFAULT NULL COMMENT '联系地址（市）',
  `area` char(6) DEFAULT NULL COMMENT '联系地址（区）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`) USING BTREE,
  UNIQUE KEY `index_mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `index_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Records of security_user
-- ----------------------------
INSERT INTO `security_user` VALUES ('1', '2018-04-30 07:26:59', '2018-05-28 17:24:00', '0', 'fresh2018', '13733770001', 'bm_fresh2018@gmail.com', 'a74114ebe219ec5282fe2184f688c4a72c57887e635c6f9dc41c7cc80826725562de3d2c426c8bd0c69443be8416383a6396d090b6edbbd809eb3e242499489e', '=m=/B@M/lWi:0~C\'&]J,G0NJ(r]G:P+<', '1', '可爱', null, null, null, null, null);
INSERT INTO `security_user` VALUES ('2', '2018-04-30 07:26:59', '2018-05-28 17:24:00', '0', 'like_food', '13733770002', 'bm_like_food@gmail.com', 'cb66485b929ecc40e0313c0f2f6ede165db9d749f359f465adf86adbc3362170616b1b2d30990e9987566e407a896cdfe08007da0114016f71e42b0a3dbb68f3', '#7hcw~={BR[s@gXZ,{e91b^;p^+l\\]aX', '1', '专注', null, null, null, null, null);
INSERT INTO `security_user` VALUES ('3', '2018-04-30 07:26:59', '2018-05-28 17:24:00', '0', 'smrs', '13733770003', 'bm_smrs@gmail.com', '0fe579a13075498b2bf7fd1992b9c2b3f3b59f5a1afcca1f532810470e552039eb3c9a2453f74dcdd4a38880b32db61010587a82d4591e24c94eeafec8023894', 'i_cu3c7N3^XEj+*zPwjN%pWk^:baG1W5', '1', '佐見リサ', null, null, null, null, null);
INSERT INTO `security_user` VALUES ('4', '2018-04-30 07:26:59', '2018-05-28 17:24:00', '0', 'abe', '13733770004', 'bm_abe@gmail.com', 'ed2e1878019be744c3da9a3da00755b790ee6eac4a633a35e2c0f575f614adfc8d504e4b7c004e43c2d4383bb695b542b32cc7c5316898d9430b1b5bbb309609', '3><^A\\zBCF?S./F*B0Zr[\'kOF\"GFWZ-P', '1', 'Mr. Abe', null, null, null, null, null);
INSERT INTO `security_user` VALUES ('5', '2018-04-30 07:27:00', '2018-05-28 17:24:00', '0', 'jeandiata', '13733770005', 'bm_jeandiata@gmail.com', '52b42cfe2df957425eb158c96317400e6e50f9e2243d8712c2fc3aa9d337fe1b0ad76f7b7046cb67aacf9e7185da09004d70c28694fa5f52afe9aba0af8b564c', 'd33Wj;~#CDY}0F99w&V{b[=yon7zN6aR', '1', 'Jean Diata', null, null, null, null, null);
INSERT INTO `security_user` VALUES ('6', '2018-04-30 07:27:00', '2018-05-28 17:24:00', '0', 'dan', '13733770006', 'bm_dan@gmail.com', '44f463a626487dfc7eb131747baef7914581af4adff6190d953c73abcd4288f5b77797fa7bf32032af8b4282c662a5c676c98c78dc39798d47c53a246859ab8f', 'XK2a>^d0Tt\'1&emgFy}\'(h/eCn\'R@M\\[', '1', '丹', null, null, null, null, null);

-- ----------------------------
-- Table structure for security_users2roles
-- ----------------------------
DROP TABLE IF EXISTS `security_users2roles`;
CREATE TABLE `security_users2roles` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与角色的关联';

-- ----------------------------
-- Records of security_users2roles
-- ----------------------------
INSERT INTO `security_users2roles` VALUES ('1', '2018-05-26 17:10:18', '2018-05-26 17:11:10', '0', '1', '1');

-- ----------------------------
-- Table structure for security_permission
-- ----------------------------
DROP TABLE IF EXISTS `security_permission`;
CREATE TABLE `security_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审计字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审计字段 更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审计字段 是否被删除',
  `display_name` varchar(255) DEFAULT NULL COMMENT '用于展示的名称',
  `mapping` varchar(255) DEFAULT NULL COMMENT '请求方法的全路由（控制器路由+方法路由）',
  `mark` varchar(255) DEFAULT NULL COMMENT '权限标记（perms[xxxx]）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_display_name` (`display_name`) USING BTREE,
  UNIQUE KEY `index_mapping` (`mapping`) USING BTREE,
  UNIQUE KEY `index_mark` (`mark`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Records of security_permission
-- ----------------------------
INSERT INTO `security_permission` VALUES ('227', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '买家详情', '/buyer/get/*', 'om8');
INSERT INTO `security_permission` VALUES ('228', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '更新买家', '/buyer/update/*', 'izx');
INSERT INTO `security_permission` VALUES ('229', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '批量删除买家', '/buyer/batchDelete', '3ow');
INSERT INTO `security_permission` VALUES ('230', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '删除买家', '/buyer/delete/*', 'bqt');
INSERT INTO `security_permission` VALUES ('231', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '创建买家', '/buyer/create', 'hq8');
INSERT INTO `security_permission` VALUES ('232', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '买家列表', '/buyer/search', 'wtc');
INSERT INTO `security_permission` VALUES ('233', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '商品详情', '/goods/get/*', '2py');
INSERT INTO `security_permission` VALUES ('234', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '更新商品', '/goods/update/*', 'zql');
INSERT INTO `security_permission` VALUES ('235', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '批量删除商品', '/goods/batchDelete', 'duy');
INSERT INTO `security_permission` VALUES ('236', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '删除商品', '/goods/delete/*', 'dqa');
INSERT INTO `security_permission` VALUES ('237', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '创建商品', '/goods/create', 'tcp');
INSERT INTO `security_permission` VALUES ('238', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '商品列表', '/goods/search', 'zxy');
INSERT INTO `security_permission` VALUES ('239', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '帐号详情', '/securityAccount/get/*', '2ea');
INSERT INTO `security_permission` VALUES ('240', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '更新帐号', '/securityAccount/update/*', 'qlr');
INSERT INTO `security_permission` VALUES ('241', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '批量删除帐号', '/securityAccount/batchDelete', '1fm');
INSERT INTO `security_permission` VALUES ('242', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '删除帐号', '/securityAccount/delete/*', 'zx2');
INSERT INTO `security_permission` VALUES ('243', '2018-05-26 16:40:08', '2018-05-26 16:40:08', '0', '创建帐号', '/securityAccount/create', 'm50');
INSERT INTO `security_permission` VALUES ('244', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '帐号列表', '/securityAccount/search', 'qtk');
INSERT INTO `security_permission` VALUES ('245', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '角色详情', '/securityRole/get/*', 'qyp');
INSERT INTO `security_permission` VALUES ('246', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '更新角色', '/securityRole/update/*', 'm7n');
INSERT INTO `security_permission` VALUES ('247', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '批量删除角色', '/securityRole/batchDelete', 's2d');
INSERT INTO `security_permission` VALUES ('248', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '删除角色', '/securityRole/delete/*', '65a');
INSERT INTO `security_permission` VALUES ('249', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '创建角色', '/securityRole/create', '0yb');
INSERT INTO `security_permission` VALUES ('250', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '角色列表', '/securityRole/search', 'zni');
INSERT INTO `security_permission` VALUES ('251', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '卖家详情', '/seller/get/*', '17c');
INSERT INTO `security_permission` VALUES ('252', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '更新卖家', '/seller/update/*', 'lmx');
INSERT INTO `security_permission` VALUES ('253', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '批量删除卖家', '/seller/batchDelete', 'mzw');
INSERT INTO `security_permission` VALUES ('254', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '删除卖家', '/seller/delete/*', 'j1r');
INSERT INTO `security_permission` VALUES ('255', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '创建卖家', '/seller/create', 'm48');
INSERT INTO `security_permission` VALUES ('256', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '卖家列表', '/seller/search', 'q0p');
INSERT INTO `security_permission` VALUES ('257', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '查看指定用户是否登录中', '/signAdmin/isSigning', 'o4t');
INSERT INTO `security_permission` VALUES ('258', '2018-05-26 16:40:09', '2018-05-26 16:40:09', '0', '将指定用户踢下线', '/signAdmin/kill', 'lld');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES ('1', '2018-05-26 17:10:27', '2018-05-26 17:10:30', '0', 's');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与权限的关联';

-- ----------------------------
-- Records of security_roles2permissions
-- ----------------------------
INSERT INTO `security_roles2permissions` VALUES ('1', '2018-05-26 17:10:33', '2018-05-26 17:11:08', '0', '1', '257');

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
