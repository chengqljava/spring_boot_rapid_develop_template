/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-09-10 17:21:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for position_provice
-- ----------------------------
DROP TABLE IF EXISTS `position_provice`;
CREATE TABLE `position_provice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `provice_id` int(11) unsigned NOT NULL COMMENT '省份id、省份编号',
  `provice_name` char(32) NOT NULL COMMENT '省份名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `provice_id` (`provice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='省份数据库';

-- ----------------------------
-- Records of position_provice
-- ----------------------------
INSERT INTO `position_provice` VALUES ('1', '110', '北京市');
INSERT INTO `position_provice` VALUES ('2', '120', '天津市');
INSERT INTO `position_provice` VALUES ('3', '130', '河北省');
INSERT INTO `position_provice` VALUES ('4', '140', '山西省');
INSERT INTO `position_provice` VALUES ('5', '150', '内蒙古自治区');
INSERT INTO `position_provice` VALUES ('6', '210', '辽宁省');
INSERT INTO `position_provice` VALUES ('7', '220', '吉林省');
INSERT INTO `position_provice` VALUES ('8', '230', '黑龙江省');
INSERT INTO `position_provice` VALUES ('9', '310', '上海市');
INSERT INTO `position_provice` VALUES ('10', '320', '江苏省');
INSERT INTO `position_provice` VALUES ('11', '330', '浙江省');
INSERT INTO `position_provice` VALUES ('12', '340', '安徽省');
INSERT INTO `position_provice` VALUES ('13', '350', '福建省');
INSERT INTO `position_provice` VALUES ('14', '360', '江西省');
INSERT INTO `position_provice` VALUES ('15', '370', '山东省');
INSERT INTO `position_provice` VALUES ('16', '410', '河南省');
INSERT INTO `position_provice` VALUES ('17', '420', '湖北省');
INSERT INTO `position_provice` VALUES ('18', '430', '湖南省');
INSERT INTO `position_provice` VALUES ('19', '440', '广东省');
INSERT INTO `position_provice` VALUES ('20', '450', '广西壮族自治区');
INSERT INTO `position_provice` VALUES ('21', '460', '海南省');
INSERT INTO `position_provice` VALUES ('22', '500', '重庆市');
INSERT INTO `position_provice` VALUES ('23', '510', '四川省');
INSERT INTO `position_provice` VALUES ('24', '520', '贵州省');
INSERT INTO `position_provice` VALUES ('25', '530', '云南省');
INSERT INTO `position_provice` VALUES ('26', '540', '西藏自治区');
INSERT INTO `position_provice` VALUES ('27', '610', '陕西省');
INSERT INTO `position_provice` VALUES ('28', '620', '甘肃省');
INSERT INTO `position_provice` VALUES ('29', '630', '青海省');
INSERT INTO `position_provice` VALUES ('30', '640', '宁夏回族自治区');
INSERT INTO `position_provice` VALUES ('31', '650', '新疆维吾尔自治区');
