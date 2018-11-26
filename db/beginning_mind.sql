/*
Navicat MySQL Data Transfer

Source Server         : vagrant
Source Server Version : 50621
Source Host           : 192.168.2.2:3306
Source Database       : beginning_mind

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-11-26 15:46:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for __template
-- ----------------------------
DROP TABLE IF EXISTS `__template`;
CREATE TABLE `__template` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '通用字段 是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '通用字段 数据版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of __template
-- ----------------------------
INSERT INTO `__template` VALUES ('1', '2018-11-12 13:24:43', '2018-11-26 07:20:50', '1', '1');

-- ----------------------------
-- Table structure for generator_demo
-- ----------------------------
DROP TABLE IF EXISTS `generator_demo`;
CREATE TABLE `generator_demo` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '通用字段 是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '通用字段 数据版本',
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
  `wechat_nickname` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='                   ';

-- ----------------------------
-- Records of generator_demo
-- ----------------------------
INSERT INTO `generator_demo` VALUES ('1147388605632512', '2018-11-15 17:24:41', '2018-11-15 17:24:41', '0', '1', '', 'asdf啊地方', '', '', '', '0', '', '0', '0', '', '', null, '0', '', '', '0', null, '', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `generator_demo` VALUES ('1147489021464576', '2018-11-15 17:25:05', '2018-11-15 17:25:05', '0', '1', '', '', '', '', '', '0', '', '0', '0', '', '', null, '0', '', '', '0', null, '1 asdf啊地方', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `generator_demo` VALUES ('1147489197625344', '2018-11-15 17:25:05', '2018-11-15 17:25:05', '0', '1', '', '', '', '', '', '0', '', '0', '0', '', '', null, '0', '', '', '0', null, '2 asdf啊地方', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `generator_demo` VALUES ('1147489197625345', '2018-11-15 17:25:05', '2018-11-15 17:25:05', '0', '1', '', '', '', '', '', '0', '', '0', '0', '', '', null, '0', '', '', '0', null, '3 asdf啊地方', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `generator_demo` VALUES ('1147489201819648', '2018-11-15 17:25:05', '2018-11-15 17:31:10', '0', '1', '', '名称', '', '', '', '0', '', '0', '0', '', '', null, '0', '', '', '0', null, '4 asdf啊地方', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `generator_demo` VALUES ('1147489206013952', '2018-11-15 17:25:05', '2018-11-15 17:25:05', '0', '1', '', '', '', '', '', '0', '', '0', '0', '', '', null, '0', '', '', '0', null, '5 asdf啊地方', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '通用字段 是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '通用字段 数据版本',
  `name` varchar(255) NOT NULL COMMENT '权限名（perms[xxxx]）',
  `mapping` varchar(255) NOT NULL COMMENT '请求方法的全路由（控制器路由+方法路由）',
  `display` varchar(255) NOT NULL DEFAULT '' COMMENT '用于展示的名称',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
  `is_get_request` tinyint(1) unsigned NOT NULL COMMENT '是否是GET请求',
  `must_have` tinyint(1) unsigned NOT NULL COMMENT '是否所有用户都应该拥有该权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '2018-08-05 08:45:48', '2018-08-05 08:45:48', '0', '1', 'image_upload', '/image/upload', '普通上传', '1', '0', '0');
INSERT INTO `permission` VALUES ('2', '2018-08-05 08:45:48', '2018-08-05 08:45:48', '0', '1', 'signManagement_isSigning', '/signManagement/isSigning', '查看指定用户是否登录中', '1', '1', '0');
INSERT INTO `permission` VALUES ('3', '2018-08-05 08:45:48', '2018-08-05 08:45:48', '0', '1', 'signManagement_kill', '/signManagement/kill', '将指定用户踢下线', '1', '0', '0');
INSERT INTO `permission` VALUES ('4', '2018-08-05 08:45:48', '2018-08-05 08:45:48', '0', '1', 'test_ppp', '/test/ppp', '', '9223372036854775807', '1', '1');
INSERT INTO `permission` VALUES ('5', '2018-08-05 08:45:48', '2018-08-05 08:45:48', '0', '1', 'test_ggg', '/test/ggg', '', '1', '1', '0');
INSERT INTO `permission` VALUES ('6', '2018-08-05 08:46:38', '2018-08-05 08:46:38', '0', '1', 'test_pp1p', '/test/pp1p', '', '9223372036854775807', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '通用字段 是否被删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '通用字段 数据版本',
  `serial_number` varchar(255) DEFAULT NULL COMMENT '用户编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `mobile` char(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT 'E-mail',
  `password` char(128) DEFAULT NULL COMMENT '密码',
  `salt` char(32) DEFAULT NULL COMMENT '盐',
  `enable_sign` tinyint(1) unsigned DEFAULT NULL COMMENT '能否登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1970-01-01 00:00:00', '2018-11-19 10:16:41', '0', '1', '', 'adam', 'admin', '', '660afa204c78e8bdebc5323e7ff9168aec159e12ce56250b6c77c220fd20d8e89a6aa382de171135a0b7aeaa28d9a0c2ca5658b5ad12fc42ad4fec6568aa0db2', 'w!-Fz?bbdWQ>[VZNu9{&jG\'Ort0s/i\"C', '1');
INSERT INTO `user` VALUES ('2', '2018-08-07 08:33:52', '2018-11-19 10:16:43', '0', '1', '', '1', '', '', '', '', '0');
INSERT INTO `user` VALUES ('3', '2018-08-07 08:34:34', '2018-11-19 10:16:43', '0', '1', '', '1', '', '', '', '', '0');
INSERT INTO `user` VALUES ('285221975101440', '2018-11-13 08:20:56', '2018-11-19 10:16:43', '0', '2', '', '乐观锁', '1', '', '', '', '0');
INSERT INTO `user` VALUES ('287884624138240', '2018-11-13 08:31:34', '2018-11-19 10:16:43', '0', '1', '', '汉字', '1', '', '', '', '0');
INSERT INTO `user` VALUES ('288782687539200', '2018-11-13 08:35:08', '2018-11-19 10:16:43', '0', '1', '', '汉字', '1', '', '', '', '0');
INSERT INTO `user` VALUES ('289120698109952', '2018-11-13 08:36:29', '2018-11-19 10:16:43', '0', '1', '', '汉字', '1', '1', '', '', '0');
INSERT INTO `user` VALUES ('290956356227072', '2018-11-13 08:43:46', '2018-11-19 10:16:43', '0', '1', '', '汉字', '1', '2', '', '', '0');
INSERT INTO `user` VALUES ('303135042179072', '2018-11-13 09:32:10', '2018-11-19 10:16:43', '0', '1', '', '汉字', '1', '2', '', '', '0');
INSERT INTO `user` VALUES ('400214552875008', '2018-11-13 15:57:56', '2018-11-19 10:32:09', '0', '1', '', 'a1a@a', '0', '', '', '', '0');
INSERT INTO `user` VALUES ('400214720647168', '2018-11-13 15:57:56', '2018-11-26 07:28:40', '1', '1', '', '批量1', '1111', '', '', '', '0');
INSERT INTO `user` VALUES ('400214720647169', '2018-11-13 15:57:56', '2018-11-19 10:32:38', '0', '1', '', '批量2', '2', 'aa@a', '', '', '0');
INSERT INTO `user` VALUES ('400214724841472', '2018-11-13 15:57:56', '2018-11-26 07:33:13', '1', '1', '', '批量3', '3', '', '', '', '0');
INSERT INTO `user` VALUES ('400214724841473', '2018-11-13 15:57:56', '2018-11-19 10:16:43', '0', '1', '', '批量4', '4', '', '', '', '0');
