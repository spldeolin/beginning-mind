/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50621
Source Host           : 192.168.2.2:3306
Source Database       : beginning_mind

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-11-16 17:01:12
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for generator_demo
-- ----------------------------
DROP TABLE IF EXISTS `generator_demo`;
CREATE TABLE `generator_demo` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
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
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
  `name` varchar(255) NOT NULL COMMENT '权限名（perms[xxxx]）',
  `mapping` varchar(255) NOT NULL COMMENT '请求方法的全路由（控制器路由+方法路由）',
  `display` varchar(255) NOT NULL DEFAULT '' COMMENT '用于展示的名称',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
  `is_get_request` tinyint(1) unsigned NOT NULL COMMENT '是否是GET请求',
  `must_have` tinyint(1) unsigned NOT NULL COMMENT '是否所有用户都应该拥有该权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通用字段 插入时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '通用字段 更新时间',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名字',
  `mobile` char(20) NOT NULL COMMENT '手机号',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT 'E-mail',
  `password` char(128) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` char(32) NOT NULL DEFAULT '' COMMENT '盐',
  `enable_sign` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '能否登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';
