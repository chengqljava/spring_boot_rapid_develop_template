/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : oracel_to_mysql

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-03-31 10:51:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for column_type
-- ----------------------------
DROP TABLE IF EXISTS column_type;
CREATE TABLE column_type (
  ID varchar(32) NOT NULL,
  type_int int(255) NOT NULL,
  type_integer int(11) NOT NULL,
  type_bigint bigint(20) NOT NULL,
  type_double double(10,2) NOT NULL,
  type_float float(8,2) NOT NULL,
  type_char char(4) NOT NULL,
  type_date date NOT NULL,
  type_time datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  type_blob blob,
  type_text text COMMENT '主键',
  type_boolean tinyint(1) DEFAULT '0',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'MYSQL类型';

-- ----------------------------
-- Records of column_type
-- ----------------------------
