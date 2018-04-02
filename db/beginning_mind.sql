/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : beginning_mind

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-03-31 16:47:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `inserted_at` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of demo
-- ----------------------------

-- ----------------------------
-- Table structure for tiny
-- ----------------------------
DROP TABLE IF EXISTS `tiny`;
CREATE TABLE `tiny` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tiny
-- ----------------------------
INSERT INTO `tiny` VALUES ('1', '没有任何审计字段');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '通用字段 ID',
  `inserted_at` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '通用字段 插入时间',
  `updated_at` datetime DEFAULT NULL COMMENT '通用字段 更新时间',
  `deleted_at` datetime DEFAULT NULL COMMENT '通用字段 删除时间',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `salt` char(9) DEFAULT NULL COMMENT '随机盐',
  `sex` enum('m','f','n','a') DEFAULT NULL COMMENT '性别（m男 f女 n中 a无）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `flag` tinyint(1) DEFAULT NULL COMMENT '“是否”',
  `ymd` date DEFAULT NULL COMMENT '年月日',
  `hms` time DEFAULT NULL COMMENT '时分秒',
  `ymdhms` datetime DEFAULT NULL COMMENT '年月日时分秒',
  `money` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `rich_text` text COMMENT '富文本',
  `serial_number` bigint(20) DEFAULT NULL COMMENT '编号',
  `percent` double(5,2) DEFAULT NULL COMMENT '百分率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2018-01-05 11:15:44', null, '2018-03-17 21:03:47', 'aaa', '999911110', 'm', '20', '0', '2018-01-05', '12:05:32', '2018-03-25 06:59:09', '50.12', '<b>大家好！</b>', '2018010500100203111', '0.01');
INSERT INTO `user` VALUES ('2', '2018-01-05 11:15:48', null, '2018-03-17 21:03:47', 'bbb', null, null, null, null, null, null, '2018-03-17 21:03:46', null, null, null, null);
INSERT INTO `user` VALUES ('3', '2018-01-05 11:15:52', null, '2018-03-17 21:03:47', 'ccc', null, null, null, null, null, null, '2018-03-17 21:03:47', null, null, null, null);
INSERT INTO `user` VALUES ('4', '2018-03-17 20:37:59', '2018-03-17 20:36:08', '2018-03-17 21:04:04', 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 21:04:04', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('5', '2018-03-17 20:38:30', '2018-03-23 21:06:58', null, '唱着', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-23 21:06:58', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('6', '2018-03-17 20:38:31', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('7', '2018-03-17 20:38:32', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('8', '2018-03-17 20:38:32', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('9', '2018-03-17 20:38:32', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('10', '2018-03-17 20:38:32', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('11', '2018-03-17 20:38:32', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('12', '2018-03-17 20:38:32', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('13', '2018-03-17 20:39:34', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('14', '2018-03-17 20:43:24', null, null, '测试', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('15', '2018-03-17 20:44:19', null, null, '测试', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('16', '2018-03-17 20:46:05', null, null, '测试', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('17', '2018-03-17 20:49:07', null, null, '测试', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('18', '2018-03-17 20:49:14', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('19', '2018-03-17 20:49:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('20', '2018-03-17 20:50:00', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('21', '2018-03-17 20:52:25', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('22', '2018-03-17 20:52:29', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('23', '2018-03-17 20:52:34', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('24', '2018-03-17 20:52:36', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('25', '2018-03-17 20:52:36', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('26', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('27', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('28', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('29', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('30', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('31', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('32', '2018-03-17 20:52:37', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('33', '2018-03-17 20:52:38', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('34', '2018-03-17 20:52:38', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('35', '2018-03-17 20:52:38', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('36', '2018-03-17 20:52:38', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('37', '2018-03-17 20:52:38', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('38', '2018-03-17 20:52:38', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('39', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('40', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('41', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('42', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('43', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('44', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('45', '2018-03-17 20:52:39', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('46', '2018-03-17 20:52:40', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('47', '2018-03-17 20:52:40', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('48', '2018-03-17 20:52:40', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('49', '2018-03-17 20:52:40', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('50', '2018-03-17 20:52:40', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('51', '2018-03-17 20:52:40', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('52', '2018-03-17 20:52:41', '2018-03-17 20:36:08', null, 'string', 'string', 'f', '0', '1', '2018-03-17', '20:36:08', '2018-03-17 20:36:08', '0.00', 'string', '0', '0.00');
INSERT INTO `user` VALUES ('53', '2018-03-23 20:42:44', null, '2018-03-23 21:11:56', '长者', 'dF5d0(w1?', 'm', '10', '0', '2018-03-23', '20:42:44', '2018-03-23 21:11:55', '10.22', '<b>hey!</b>', '13131313135614852', '10.01');
INSERT INTO `user` VALUES ('57', '2018-03-25 07:45:01', null, null, '测试1', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('58', '2018-03-25 07:45:01', null, null, '测试2', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('59', '2018-03-25 07:45:01', null, null, '测试3', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('60', '2018-03-25 11:40:49', '2018-03-25 11:47:19', '2018-03-25 11:48:00', '繁體', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('61', '2018-03-25 15:08:05', null, null, '多堆垛', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('62', '2018-03-25 15:09:25', null, null, null, '反应堆A1', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('63', '2018-03-25 15:09:25', null, null, null, '反应堆A2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('64', '2018-03-25 15:09:25', null, '2018-03-25 15:28:00', null, '反应堆A3', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('65', '2018-03-25 15:09:25', '2018-03-25 15:16:19', null, '核反应', '反应堆A4', null, null, null, null, null, null, null, null, null, null);
