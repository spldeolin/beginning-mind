/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : beginning_mind_2

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-05-09 19:20:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ex
-- ----------------------------
DROP TABLE IF EXISTS `ex`;
CREATE TABLE `ex` (
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ex
-- ----------------------------
INSERT INTO `ex` VALUES ('哇');
INSERT INTO `ex` VALUES ('aaa');
