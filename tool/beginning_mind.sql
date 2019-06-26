/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 127.0.0.1:3306
 Source Schema         : beginning_mind

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 26/06/2019 17:19:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for __template
-- ----------------------------
DROP TABLE IF EXISTS `__template`;
CREATE TABLE `__template` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `inserted_insignia` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '插入数据时所处请求的insignia',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `updated_insignia` char(6) DEFAULT NULL COMMENT '最近一次更新数据时所处请求的insignia',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of __template
-- ----------------------------
BEGIN;
INSERT INTO `__template` VALUES (1, '2018-11-12 13:24:43', NULL, '2018-11-26 07:20:50', NULL, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for biz_demo
-- ----------------------------
DROP TABLE IF EXISTS `biz_demo`;
CREATE TABLE `biz_demo` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `inserted_insignia` char(6) NOT NULL COMMENT '插入数据时所处请求的insignia',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `updated_insignia` char(6) NOT NULL COMMENT '最近一次更新数据时所处请求的insignia',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本',
  `user_number` char(20) NOT NULL DEFAULT '' COMMENT '工号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名字',
  `mobile` char(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT 'E-mail',
  `telephone` varchar(255) NOT NULL DEFAULT '' COMMENT '座机号',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别（0请选择，1男，2女）',
  `school` varchar(255) NOT NULL DEFAULT '' COMMENT '毕业院校',
  `education_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '学历（0请选择，1初，2高，3专，4本，5硕，7博，8其他）',
  `credential_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '证件类型（0请选择，1身份证，2护照，3军人证，4其他）',
  `credential_number` varchar(255) NOT NULL DEFAULT '' COMMENT '证件号码',
  `work_title` varchar(255) NOT NULL DEFAULT '' COMMENT '职称',
  `induction_date` date DEFAULT NULL COMMENT '入职时间',
  `work_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '工作状态（0请选择，1在职，2停职，3停职留薪）',
  `password` char(128) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` char(32) NOT NULL DEFAULT '' COMMENT '盐',
  `enable_sign` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '能否登录',
  `wechat_openid` varchar(255) DEFAULT NULL,
  `wechat_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `is_permitted_update_parking_weekly_1` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改停车周报的第1栏',
  `is_permitted_update_parking_weekly_2` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改停车周报的第2栏',
  `is_permitted_update_parking_weekly_3` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改停车周报的第3栏',
  `is_permitted_update_development_weekly_1` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改物业开发周报的第1栏',
  `is_permitted_update_development_weekly_2` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改物业开发周报的第2栏',
  `is_permitted_update_half_monthly_1` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改半月报的第1栏',
  `is_permitted_update_parking_monthly_1` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改停车月报的第1栏',
  `is_permitted_update_parking_monthly_2` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改停车月报的第2栏',
  `is_permitted_update_development_monthly_1` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改物业开发月报的第1栏',
  `is_permitted_update_development_monthly_2` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许修改物业开发月报的第2栏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务示例';

-- ----------------------------
-- Records of biz_demo
-- ----------------------------
BEGIN;
INSERT INTO `biz_demo` VALUES (2, '2019-04-12 16:35:18', '000000', '2019-06-26 17:14:06', '000000', 0, 1, '', 'a', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `biz_demo` VALUES (1147388605632512, '2018-11-15 17:24:41', '000000', '2019-06-26 17:14:06', '000000', 0, 1, '', 'asdf啊地方', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `biz_demo` VALUES (1147489021464576, '2018-11-15 17:25:05', '000000', '2019-06-26 17:14:06', '000000', 0, 1, '', '', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '1 asdf啊地方', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `biz_demo` VALUES (1147489197625344, '2018-11-15 17:25:05', '000000', '2019-06-26 17:14:06', '000000', 0, 1, '', '', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '2 asdf啊地方', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `biz_demo` VALUES (1147489197625345, '2018-11-15 17:25:05', '000000', '2019-06-26 17:14:06', '000000', 0, 1, '', '', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '3 asdf啊地方', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `biz_demo` VALUES (1147489201819648, '2018-11-15 17:25:05', '000000', '2019-06-26 17:14:05', '000000', 0, 1, '', '名称', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '4 asdf啊地方', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `biz_demo` VALUES (1147489206013952, '2018-11-15 17:25:05', '000000', '2019-06-26 17:14:06', '000000', 0, 1, '', '', '', '', '', 0, '', 0, 0, '', '', NULL, 0, '', '', 0, NULL, '5 asdf啊地方', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `inserted_insignia` char(6) NOT NULL COMMENT '插入数据时所处请求的insignia',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `updated_insignia` char(6) NOT NULL COMMENT '最近一次更新数据时所处请求的insignia',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本',
  `mapping_method` varchar(255) DEFAULT NULL COMMENT '映射方法（HTTP方法，e.g.: POST）',
  `mapping_path` varchar(255) DEFAULT NULL COMMENT '映射路由（控制器路由+方法路由，e.g.: /user/create）',
  `display` varchar(255) DEFAULT NULL COMMENT '用于展示的名称',
  `is_granted_for_all` tinyint(1) unsigned DEFAULT NULL COMMENT '是否所有用户都拥有本权限',
  `is_forbidden` tinyint(1) unsigned DEFAULT NULL COMMENT '是否所有用户都不拥有本权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Table structure for security_access_token
-- ----------------------------
DROP TABLE IF EXISTS `security_access_token`;
CREATE TABLE `security_access_token` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `inserted_insignia` char(6) NOT NULL COMMENT '插入数据时所处请求的insignia',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `updated_insignia` char(6) NOT NULL COMMENT '最近一次更新数据时所处请求的insignia',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本',
  `mapping_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '映射方法（HTTP方法，e.g.: POST）',
  `mapping_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '映射路由（控制器路由+方法路由，e.g.: /user/create）',
  `token` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'TOKEN（30位随机大小写字母+数字）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值';

-- ----------------------------
-- Records of security_access_token
-- ----------------------------
BEGIN;
INSERT INTO `security_access_token` VALUES (1, '2019-02-23 13:10:00', '000000', '2019-06-26 17:14:54', '000000', 0, 1, 'post', '/test/requestTrackReport', 'ss');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `inserted_insignia` char(6) NOT NULL COMMENT '插入数据时所处请求的insignia',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `updated_insignia` char(6) NOT NULL COMMENT '最近一次更新数据时所处请求的insignia',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本',
  `serial_number` varchar(255) DEFAULT NULL COMMENT '用户编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `mobile` char(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT 'E-mail',
  `password` char(128) DEFAULT NULL COMMENT '密码',
  `salt` char(32) DEFAULT NULL COMMENT '盐',
  `enable_sign` tinyint(1) unsigned DEFAULT NULL COMMENT '能否登录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '1970-01-01 00:00:00', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', 'adam', 'admin', '', '660afa204c78e8bdebc5323e7ff9168aec159e12ce56250b6c77c220fd20d8e89a6aa382de171135a0b7aeaa28d9a0c2ca5658b5ad12fc42ad4fec6568aa0db2', 'w!-Fz?bbdWQ>[VZNu9{&jG\'Ort0s/i\"C', 1);
INSERT INTO `user` VALUES (2, '2018-08-07 08:33:52', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '1', '', '', '', '', 0);
INSERT INTO `user` VALUES (3, '2018-08-07 08:34:34', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '1', '', '', '', '', 0);
INSERT INTO `user` VALUES (285221975101440, '2018-11-13 08:20:56', '000000', '2019-06-26 17:16:15', '000000', 0, 2, '', '乐观锁', '1', '', '', '', 0);
INSERT INTO `user` VALUES (287884624138240, '2018-11-13 08:31:34', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '汉字', '1', '', '', '', 0);
INSERT INTO `user` VALUES (288782687539200, '2018-11-13 08:35:08', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '汉字', '1', '', '', '', 0);
INSERT INTO `user` VALUES (289120698109952, '2018-11-13 08:36:29', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '汉字', '1', '1', '', '', 0);
INSERT INTO `user` VALUES (290956356227072, '2018-11-13 08:43:46', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '汉字', '1', '2', '', '', 0);
INSERT INTO `user` VALUES (303135042179072, '2018-11-13 09:32:10', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '汉字', '1', '2', '', '', 0);
INSERT INTO `user` VALUES (400214552875008, '2018-11-13 15:57:56', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', 'a1a@a', '0', '', '', '', 0);
INSERT INTO `user` VALUES (400214720647168, '2018-11-13 15:57:56', '000000', '2019-06-26 17:16:15', '000000', 1, 1, '', '批量1', '1111', '', '', '', 0);
INSERT INTO `user` VALUES (400214720647169, '2018-11-13 15:57:56', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '批量2', '2', 'aa@a', '', '', 0);
INSERT INTO `user` VALUES (400214724841472, '2018-11-13 15:57:56', '000000', '2019-06-26 17:16:15', '000000', 1, 1, '', '批量3', '3', '', '', '', 0);
INSERT INTO `user` VALUES (400214724841473, '2018-11-13 15:57:56', '000000', '2019-06-26 17:16:15', '000000', 0, 1, '', '批量4', '4', '', '', '', 0);
INSERT INTO `user` VALUES (8999000162308096, '2018-12-07 09:26:26', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '汉字', '1', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (36286656290099200, '2019-02-20 16:37:50', '000000', '2019-06-26 17:16:15', '000000', 1, 1, NULL, '批量0', '0', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (36286656566923264, '2019-02-20 16:37:50', '000000', '2019-06-26 17:16:15', '000000', 1, 1, NULL, '批量1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (36286656571117568, '2019-02-20 16:37:50', '000000', '2019-06-26 17:16:15', '000000', 1, 1, NULL, '批量2', '2', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (36286656575311872, '2019-02-20 16:37:50', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量3', '3', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (36286656579506176, '2019-02-20 16:37:50', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量4', '4', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (72171958074216448, '2019-05-30 17:13:13', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '汉字', '1', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (72172181794197504, '2019-05-30 17:14:06', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量0', '0', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (72172181831946240, '2019-05-30 17:14:06', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (72172181836140544, '2019-05-30 17:14:06', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量2', '2', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (72172181840334848, '2019-05-30 17:14:06', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量3', '3', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (72172181848723456, '2019-05-30 17:14:06', '000000', '2019-06-26 17:16:15', '000000', 0, 1, NULL, '批量4', '4', NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user2permission
-- ----------------------------
DROP TABLE IF EXISTS `user2permission`;
CREATE TABLE `user2permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `inserted_insignia` char(6) NOT NULL COMMENT '插入数据时所处请求的insignia',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `updated_insignia` char(6) NOT NULL COMMENT '最近一次更新数据时所处请求的insignia',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户与权限的关联关系';

SET FOREIGN_KEY_CHECKS = 1;
