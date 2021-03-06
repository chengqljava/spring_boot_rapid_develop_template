/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-09-10 17:21:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for position_city
-- ----------------------------
DROP TABLE IF EXISTS `position_city`;
CREATE TABLE `position_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province_id` int(10) unsigned NOT NULL COMMENT '地级市id',
  `city_id` bigint(20) unsigned NOT NULL COMMENT '县级市id',
  `city_name` char(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `city_id` (`city_id`),
  KEY `province_id` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8 COMMENT='县级市数据库';

-- ----------------------------
-- Records of position_city
-- ----------------------------
INSERT INTO `position_city` VALUES ('1', '110', '110100000000', '市辖区');
INSERT INTO `position_city` VALUES ('2', '110', '110200000000', '市辖县');
INSERT INTO `position_city` VALUES ('3', '120', '120100000000', '市辖区');
INSERT INTO `position_city` VALUES ('4', '120', '120200000000', '市辖县');
INSERT INTO `position_city` VALUES ('5', '130', '130100000000', '石家庄市');
INSERT INTO `position_city` VALUES ('6', '130', '130200000000', '唐山市');
INSERT INTO `position_city` VALUES ('7', '130', '130300000000', '秦皇岛市');
INSERT INTO `position_city` VALUES ('8', '130', '130400000000', '邯郸市');
INSERT INTO `position_city` VALUES ('9', '130', '130500000000', '邢台市');
INSERT INTO `position_city` VALUES ('10', '130', '130600000000', '保定市');
INSERT INTO `position_city` VALUES ('11', '130', '130700000000', '张家口市');
INSERT INTO `position_city` VALUES ('12', '130', '130800000000', '承德市');
INSERT INTO `position_city` VALUES ('13', '130', '130900000000', '沧州市');
INSERT INTO `position_city` VALUES ('14', '130', '131000000000', '廊坊市');
INSERT INTO `position_city` VALUES ('15', '130', '131100000000', '衡水市');
INSERT INTO `position_city` VALUES ('16', '140', '140100000000', '太原市');
INSERT INTO `position_city` VALUES ('17', '140', '140200000000', '大同市');
INSERT INTO `position_city` VALUES ('18', '140', '140300000000', '阳泉市');
INSERT INTO `position_city` VALUES ('19', '140', '140400000000', '长治市');
INSERT INTO `position_city` VALUES ('20', '140', '140500000000', '晋城市');
INSERT INTO `position_city` VALUES ('21', '140', '140600000000', '朔州市');
INSERT INTO `position_city` VALUES ('22', '140', '140700000000', '晋中市');
INSERT INTO `position_city` VALUES ('23', '140', '140800000000', '运城市');
INSERT INTO `position_city` VALUES ('24', '140', '140900000000', '忻州市');
INSERT INTO `position_city` VALUES ('25', '140', '141000000000', '临汾市');
INSERT INTO `position_city` VALUES ('26', '140', '141100000000', '吕梁市');
INSERT INTO `position_city` VALUES ('27', '150', '150100000000', '呼和浩特市');
INSERT INTO `position_city` VALUES ('28', '150', '150200000000', '包头市');
INSERT INTO `position_city` VALUES ('29', '150', '150300000000', '乌海市');
INSERT INTO `position_city` VALUES ('30', '150', '150400000000', '赤峰市');
INSERT INTO `position_city` VALUES ('31', '150', '150500000000', '通辽市');
INSERT INTO `position_city` VALUES ('32', '150', '150600000000', '鄂尔多斯市');
INSERT INTO `position_city` VALUES ('33', '150', '150700000000', '呼伦贝尔市');
INSERT INTO `position_city` VALUES ('34', '150', '150800000000', '巴彦淖尔市');
INSERT INTO `position_city` VALUES ('35', '150', '150900000000', '乌兰察布市');
INSERT INTO `position_city` VALUES ('36', '150', '152200000000', '兴安盟');
INSERT INTO `position_city` VALUES ('37', '150', '152500000000', '锡林郭勒盟');
INSERT INTO `position_city` VALUES ('38', '150', '152900000000', '阿拉善盟');
INSERT INTO `position_city` VALUES ('39', '210', '210100000000', '沈阳市');
INSERT INTO `position_city` VALUES ('40', '210', '210200000000', '大连市');
INSERT INTO `position_city` VALUES ('41', '210', '210300000000', '鞍山市');
INSERT INTO `position_city` VALUES ('42', '210', '210400000000', '抚顺市');
INSERT INTO `position_city` VALUES ('43', '210', '210500000000', '本溪市');
INSERT INTO `position_city` VALUES ('44', '210', '210600000000', '丹东市');
INSERT INTO `position_city` VALUES ('45', '210', '210700000000', '锦州市');
INSERT INTO `position_city` VALUES ('46', '210', '210800000000', '营口市');
INSERT INTO `position_city` VALUES ('47', '210', '210900000000', '阜新市');
INSERT INTO `position_city` VALUES ('48', '210', '211000000000', '辽阳市');
INSERT INTO `position_city` VALUES ('49', '210', '211100000000', '盘锦市');
INSERT INTO `position_city` VALUES ('50', '210', '211200000000', '铁岭市');
INSERT INTO `position_city` VALUES ('51', '210', '211300000000', '朝阳市');
INSERT INTO `position_city` VALUES ('52', '210', '211400000000', '葫芦岛市');
INSERT INTO `position_city` VALUES ('53', '220', '220100000000', '长春市');
INSERT INTO `position_city` VALUES ('54', '220', '220200000000', '吉林市');
INSERT INTO `position_city` VALUES ('55', '220', '220300000000', '四平市');
INSERT INTO `position_city` VALUES ('56', '220', '220400000000', '辽源市');
INSERT INTO `position_city` VALUES ('57', '220', '220500000000', '通化市');
INSERT INTO `position_city` VALUES ('58', '220', '220600000000', '白山市');
INSERT INTO `position_city` VALUES ('59', '220', '220700000000', '松原市');
INSERT INTO `position_city` VALUES ('60', '220', '220800000000', '白城市');
INSERT INTO `position_city` VALUES ('61', '220', '222400000000', '延边朝鲜族自治州');
INSERT INTO `position_city` VALUES ('62', '230', '230100000000', '哈尔滨市');
INSERT INTO `position_city` VALUES ('63', '230', '230200000000', '齐齐哈尔市');
INSERT INTO `position_city` VALUES ('64', '230', '230300000000', '鸡西市');
INSERT INTO `position_city` VALUES ('65', '230', '230400000000', '鹤岗市');
INSERT INTO `position_city` VALUES ('66', '230', '230500000000', '双鸭山市');
INSERT INTO `position_city` VALUES ('67', '230', '230600000000', '大庆市');
INSERT INTO `position_city` VALUES ('68', '230', '230700000000', '伊春市');
INSERT INTO `position_city` VALUES ('69', '230', '230800000000', '佳木斯市');
INSERT INTO `position_city` VALUES ('70', '230', '230900000000', '七台河市');
INSERT INTO `position_city` VALUES ('71', '230', '231000000000', '牡丹江市');
INSERT INTO `position_city` VALUES ('72', '230', '231100000000', '黑河市');
INSERT INTO `position_city` VALUES ('73', '230', '231200000000', '绥化市');
INSERT INTO `position_city` VALUES ('74', '230', '232700000000', '大兴安岭地区');
INSERT INTO `position_city` VALUES ('75', '310', '310100000000', '市辖区');
INSERT INTO `position_city` VALUES ('76', '310', '310200000000', '市辖县');
INSERT INTO `position_city` VALUES ('77', '320', '320100000000', '南京市');
INSERT INTO `position_city` VALUES ('78', '320', '320200000000', '无锡市');
INSERT INTO `position_city` VALUES ('79', '320', '320300000000', '徐州市');
INSERT INTO `position_city` VALUES ('80', '320', '320400000000', '常州市');
INSERT INTO `position_city` VALUES ('81', '320', '320500000000', '苏州市');
INSERT INTO `position_city` VALUES ('82', '320', '320600000000', '南通市');
INSERT INTO `position_city` VALUES ('83', '320', '320700000000', '连云港市');
INSERT INTO `position_city` VALUES ('84', '320', '320800000000', '淮安市');
INSERT INTO `position_city` VALUES ('85', '320', '320900000000', '盐城市');
INSERT INTO `position_city` VALUES ('86', '320', '321000000000', '扬州市');
INSERT INTO `position_city` VALUES ('87', '320', '321100000000', '镇江市');
INSERT INTO `position_city` VALUES ('88', '320', '321200000000', '泰州市');
INSERT INTO `position_city` VALUES ('89', '320', '321300000000', '宿迁市');
INSERT INTO `position_city` VALUES ('90', '330', '330100000000', '杭州市');
INSERT INTO `position_city` VALUES ('91', '330', '330200000000', '宁波市');
INSERT INTO `position_city` VALUES ('92', '330', '330300000000', '温州市');
INSERT INTO `position_city` VALUES ('93', '330', '330400000000', '嘉兴市');
INSERT INTO `position_city` VALUES ('94', '330', '330500000000', '湖州市');
INSERT INTO `position_city` VALUES ('95', '330', '330600000000', '绍兴市');
INSERT INTO `position_city` VALUES ('96', '330', '330700000000', '金华市');
INSERT INTO `position_city` VALUES ('97', '330', '330800000000', '衢州市');
INSERT INTO `position_city` VALUES ('98', '330', '330900000000', '舟山市');
INSERT INTO `position_city` VALUES ('99', '330', '331000000000', '台州市');
INSERT INTO `position_city` VALUES ('100', '330', '331100000000', '丽水市');
INSERT INTO `position_city` VALUES ('101', '340', '340100000000', '合肥市');
INSERT INTO `position_city` VALUES ('102', '340', '340200000000', '芜湖市');
INSERT INTO `position_city` VALUES ('103', '340', '340300000000', '蚌埠市');
INSERT INTO `position_city` VALUES ('104', '340', '340400000000', '淮南市');
INSERT INTO `position_city` VALUES ('105', '340', '340500000000', '马鞍山市');
INSERT INTO `position_city` VALUES ('106', '340', '340600000000', '淮北市');
INSERT INTO `position_city` VALUES ('107', '340', '340700000000', '铜陵市');
INSERT INTO `position_city` VALUES ('108', '340', '340800000000', '安庆市');
INSERT INTO `position_city` VALUES ('109', '340', '341000000000', '黄山市');
INSERT INTO `position_city` VALUES ('110', '340', '341100000000', '滁州市');
INSERT INTO `position_city` VALUES ('111', '340', '341200000000', '阜阳市');
INSERT INTO `position_city` VALUES ('112', '340', '341300000000', '宿州市');
INSERT INTO `position_city` VALUES ('113', '340', '341500000000', '六安市');
INSERT INTO `position_city` VALUES ('114', '340', '341600000000', '亳州市');
INSERT INTO `position_city` VALUES ('115', '340', '341700000000', '池州市');
INSERT INTO `position_city` VALUES ('116', '340', '341800000000', '宣城市');
INSERT INTO `position_city` VALUES ('117', '350', '350100000000', '福州市');
INSERT INTO `position_city` VALUES ('118', '350', '350200000000', '厦门市');
INSERT INTO `position_city` VALUES ('119', '350', '350300000000', '莆田市');
INSERT INTO `position_city` VALUES ('120', '350', '350400000000', '三明市');
INSERT INTO `position_city` VALUES ('121', '350', '350500000000', '泉州市');
INSERT INTO `position_city` VALUES ('122', '350', '350600000000', '漳州市');
INSERT INTO `position_city` VALUES ('123', '350', '350700000000', '南平市');
INSERT INTO `position_city` VALUES ('124', '350', '350800000000', '龙岩市');
INSERT INTO `position_city` VALUES ('125', '350', '350900000000', '宁德市');
INSERT INTO `position_city` VALUES ('126', '360', '360100000000', '南昌市');
INSERT INTO `position_city` VALUES ('127', '360', '360200000000', '景德镇市');
INSERT INTO `position_city` VALUES ('128', '360', '360300000000', '萍乡市');
INSERT INTO `position_city` VALUES ('129', '360', '360400000000', '九江市');
INSERT INTO `position_city` VALUES ('130', '360', '360500000000', '新余市');
INSERT INTO `position_city` VALUES ('131', '360', '360600000000', '鹰潭市');
INSERT INTO `position_city` VALUES ('132', '360', '360700000000', '赣州市');
INSERT INTO `position_city` VALUES ('133', '360', '360800000000', '吉安市');
INSERT INTO `position_city` VALUES ('134', '360', '360900000000', '宜春市');
INSERT INTO `position_city` VALUES ('135', '360', '361000000000', '抚州市');
INSERT INTO `position_city` VALUES ('136', '360', '361100000000', '上饶市');
INSERT INTO `position_city` VALUES ('137', '370', '370100000000', '济南市');
INSERT INTO `position_city` VALUES ('138', '370', '370200000000', '青岛市');
INSERT INTO `position_city` VALUES ('139', '370', '370300000000', '淄博市');
INSERT INTO `position_city` VALUES ('140', '370', '370400000000', '枣庄市');
INSERT INTO `position_city` VALUES ('141', '370', '370500000000', '东营市');
INSERT INTO `position_city` VALUES ('142', '370', '370600000000', '烟台市');
INSERT INTO `position_city` VALUES ('143', '370', '370700000000', '潍坊市');
INSERT INTO `position_city` VALUES ('144', '370', '370800000000', '济宁市');
INSERT INTO `position_city` VALUES ('145', '370', '370900000000', '泰安市');
INSERT INTO `position_city` VALUES ('146', '370', '371000000000', '威海市');
INSERT INTO `position_city` VALUES ('147', '370', '371100000000', '日照市');
INSERT INTO `position_city` VALUES ('148', '370', '371200000000', '莱芜市');
INSERT INTO `position_city` VALUES ('149', '370', '371300000000', '临沂市');
INSERT INTO `position_city` VALUES ('150', '370', '371400000000', '德州市');
INSERT INTO `position_city` VALUES ('151', '370', '371500000000', '聊城市');
INSERT INTO `position_city` VALUES ('152', '370', '371600000000', '滨州市');
INSERT INTO `position_city` VALUES ('153', '370', '371700000000', '菏泽市');
INSERT INTO `position_city` VALUES ('154', '410', '410100000000', '郑州市');
INSERT INTO `position_city` VALUES ('155', '410', '410200000000', '开封市');
INSERT INTO `position_city` VALUES ('156', '410', '410300000000', '洛阳市');
INSERT INTO `position_city` VALUES ('157', '410', '410400000000', '平顶山市');
INSERT INTO `position_city` VALUES ('158', '410', '410500000000', '安阳市');
INSERT INTO `position_city` VALUES ('159', '410', '410600000000', '鹤壁市');
INSERT INTO `position_city` VALUES ('160', '410', '410700000000', '新乡市');
INSERT INTO `position_city` VALUES ('161', '410', '410800000000', '焦作市');
INSERT INTO `position_city` VALUES ('162', '410', '410900000000', '濮阳市');
INSERT INTO `position_city` VALUES ('163', '410', '411000000000', '许昌市');
INSERT INTO `position_city` VALUES ('164', '410', '411100000000', '漯河市');
INSERT INTO `position_city` VALUES ('165', '410', '411200000000', '三门峡市');
INSERT INTO `position_city` VALUES ('166', '410', '411300000000', '南阳市');
INSERT INTO `position_city` VALUES ('167', '410', '411400000000', '商丘市');
INSERT INTO `position_city` VALUES ('168', '410', '411500000000', '信阳市');
INSERT INTO `position_city` VALUES ('169', '410', '411600000000', '周口市');
INSERT INTO `position_city` VALUES ('170', '410', '411700000000', '驻马店市');
INSERT INTO `position_city` VALUES ('171', '410', '419000000000', '省直辖县级行政区划');
INSERT INTO `position_city` VALUES ('172', '420', '420100000000', '武汉市');
INSERT INTO `position_city` VALUES ('173', '420', '420200000000', '黄石市');
INSERT INTO `position_city` VALUES ('174', '420', '420300000000', '十堰市');
INSERT INTO `position_city` VALUES ('175', '420', '420500000000', '宜昌市');
INSERT INTO `position_city` VALUES ('176', '420', '420600000000', '襄阳市');
INSERT INTO `position_city` VALUES ('177', '420', '420700000000', '鄂州市');
INSERT INTO `position_city` VALUES ('178', '420', '420800000000', '荆门市');
INSERT INTO `position_city` VALUES ('179', '420', '420900000000', '孝感市');
INSERT INTO `position_city` VALUES ('180', '420', '421000000000', '荆州市');
INSERT INTO `position_city` VALUES ('181', '420', '421100000000', '黄冈市');
INSERT INTO `position_city` VALUES ('182', '420', '421200000000', '咸宁市');
INSERT INTO `position_city` VALUES ('183', '420', '421300000000', '随州市');
INSERT INTO `position_city` VALUES ('184', '420', '422800000000', '恩施土家族苗族自治州');
INSERT INTO `position_city` VALUES ('185', '420', '429000000000', '省直辖县级行政区划');
INSERT INTO `position_city` VALUES ('186', '430', '430100000000', '长沙市');
INSERT INTO `position_city` VALUES ('187', '430', '430200000000', '株洲市');
INSERT INTO `position_city` VALUES ('188', '430', '430300000000', '湘潭市');
INSERT INTO `position_city` VALUES ('189', '430', '430400000000', '衡阳市');
INSERT INTO `position_city` VALUES ('190', '430', '430500000000', '邵阳市');
INSERT INTO `position_city` VALUES ('191', '430', '430600000000', '岳阳市');
INSERT INTO `position_city` VALUES ('192', '430', '430700000000', '常德市');
INSERT INTO `position_city` VALUES ('193', '430', '430800000000', '张家界市');
INSERT INTO `position_city` VALUES ('194', '430', '430900000000', '益阳市');
INSERT INTO `position_city` VALUES ('195', '430', '431000000000', '郴州市');
INSERT INTO `position_city` VALUES ('196', '430', '431100000000', '永州市');
INSERT INTO `position_city` VALUES ('197', '430', '431200000000', '怀化市');
INSERT INTO `position_city` VALUES ('198', '430', '431300000000', '娄底市');
INSERT INTO `position_city` VALUES ('199', '430', '433100000000', '湘西土家族苗族自治州');
INSERT INTO `position_city` VALUES ('200', '440', '440100000000', '广州市');
INSERT INTO `position_city` VALUES ('201', '440', '440200000000', '韶关市');
INSERT INTO `position_city` VALUES ('202', '440', '440300000000', '深圳市');
INSERT INTO `position_city` VALUES ('203', '440', '440400000000', '珠海市');
INSERT INTO `position_city` VALUES ('204', '440', '440500000000', '汕头市');
INSERT INTO `position_city` VALUES ('205', '440', '440600000000', '佛山市');
INSERT INTO `position_city` VALUES ('206', '440', '440700000000', '江门市');
INSERT INTO `position_city` VALUES ('207', '440', '440800000000', '湛江市');
INSERT INTO `position_city` VALUES ('208', '440', '440900000000', '茂名市');
INSERT INTO `position_city` VALUES ('209', '440', '441200000000', '肇庆市');
INSERT INTO `position_city` VALUES ('210', '440', '441300000000', '惠州市');
INSERT INTO `position_city` VALUES ('211', '440', '441400000000', '梅州市');
INSERT INTO `position_city` VALUES ('212', '440', '441500000000', '汕尾市');
INSERT INTO `position_city` VALUES ('213', '440', '441600000000', '河源市');
INSERT INTO `position_city` VALUES ('214', '440', '441700000000', '阳江市');
INSERT INTO `position_city` VALUES ('215', '440', '441800000000', '清远市');
INSERT INTO `position_city` VALUES ('216', '440', '441900000000', '东莞市');
INSERT INTO `position_city` VALUES ('217', '440', '442000000000', '中山市');
INSERT INTO `position_city` VALUES ('218', '440', '445100000000', '潮州市');
INSERT INTO `position_city` VALUES ('219', '440', '445200000000', '揭阳市');
INSERT INTO `position_city` VALUES ('220', '440', '445300000000', '云浮市');
INSERT INTO `position_city` VALUES ('221', '450', '450100000000', '南宁市');
INSERT INTO `position_city` VALUES ('222', '450', '450200000000', '柳州市');
INSERT INTO `position_city` VALUES ('223', '450', '450300000000', '桂林市');
INSERT INTO `position_city` VALUES ('224', '450', '450400000000', '梧州市');
INSERT INTO `position_city` VALUES ('225', '450', '450500000000', '北海市');
INSERT INTO `position_city` VALUES ('226', '450', '450600000000', '防城港市');
INSERT INTO `position_city` VALUES ('227', '450', '450700000000', '钦州市');
INSERT INTO `position_city` VALUES ('228', '450', '450800000000', '贵港市');
INSERT INTO `position_city` VALUES ('229', '450', '450900000000', '玉林市');
INSERT INTO `position_city` VALUES ('230', '450', '451000000000', '百色市');
INSERT INTO `position_city` VALUES ('231', '450', '451100000000', '贺州市');
INSERT INTO `position_city` VALUES ('232', '450', '451200000000', '河池市');
INSERT INTO `position_city` VALUES ('233', '450', '451300000000', '来宾市');
INSERT INTO `position_city` VALUES ('234', '450', '451400000000', '崇左市');
INSERT INTO `position_city` VALUES ('235', '460', '460100000000', '海口市');
INSERT INTO `position_city` VALUES ('236', '460', '460200000000', '三亚市');
INSERT INTO `position_city` VALUES ('237', '460', '460300000000', '三沙市');
INSERT INTO `position_city` VALUES ('238', '460', '469000000000', '省直辖县级行政区划');
INSERT INTO `position_city` VALUES ('239', '500', '500100000000', '市辖区');
INSERT INTO `position_city` VALUES ('240', '500', '500200000000', '市辖县');
INSERT INTO `position_city` VALUES ('241', '510', '510100000000', '成都市');
INSERT INTO `position_city` VALUES ('242', '510', '510300000000', '自贡市');
INSERT INTO `position_city` VALUES ('243', '510', '510400000000', '攀枝花市');
INSERT INTO `position_city` VALUES ('244', '510', '510500000000', '泸州市');
INSERT INTO `position_city` VALUES ('245', '510', '510600000000', '德阳市');
INSERT INTO `position_city` VALUES ('246', '510', '510700000000', '绵阳市');
INSERT INTO `position_city` VALUES ('247', '510', '510800000000', '广元市');
INSERT INTO `position_city` VALUES ('248', '510', '510900000000', '遂宁市');
INSERT INTO `position_city` VALUES ('249', '510', '511000000000', '内江市');
INSERT INTO `position_city` VALUES ('250', '510', '511100000000', '乐山市');
INSERT INTO `position_city` VALUES ('251', '510', '511300000000', '南充市');
INSERT INTO `position_city` VALUES ('252', '510', '511400000000', '眉山市');
INSERT INTO `position_city` VALUES ('253', '510', '511500000000', '宜宾市');
INSERT INTO `position_city` VALUES ('254', '510', '511600000000', '广安市');
INSERT INTO `position_city` VALUES ('255', '510', '511700000000', '达州市');
INSERT INTO `position_city` VALUES ('256', '510', '511800000000', '雅安市');
INSERT INTO `position_city` VALUES ('257', '510', '511900000000', '巴中市');
INSERT INTO `position_city` VALUES ('258', '510', '512000000000', '资阳市');
INSERT INTO `position_city` VALUES ('259', '510', '513200000000', '阿坝藏族羌族自治州');
INSERT INTO `position_city` VALUES ('260', '510', '513300000000', '甘孜藏族自治州');
INSERT INTO `position_city` VALUES ('261', '510', '513400000000', '凉山彝族自治州');
INSERT INTO `position_city` VALUES ('262', '520', '520100000000', '贵阳市');
INSERT INTO `position_city` VALUES ('263', '520', '520200000000', '六盘水市');
INSERT INTO `position_city` VALUES ('264', '520', '520300000000', '遵义市');
INSERT INTO `position_city` VALUES ('265', '520', '520400000000', '安顺市');
INSERT INTO `position_city` VALUES ('266', '520', '520500000000', '毕节市');
INSERT INTO `position_city` VALUES ('267', '520', '520600000000', '铜仁市');
INSERT INTO `position_city` VALUES ('268', '520', '522300000000', '黔西南布依族苗族自治州');
INSERT INTO `position_city` VALUES ('269', '520', '522600000000', '黔东南苗族侗族自治州');
INSERT INTO `position_city` VALUES ('270', '520', '522700000000', '黔南布依族苗族自治州');
INSERT INTO `position_city` VALUES ('271', '530', '530100000000', '昆明市');
INSERT INTO `position_city` VALUES ('272', '530', '530300000000', '曲靖市');
INSERT INTO `position_city` VALUES ('273', '530', '530400000000', '玉溪市');
INSERT INTO `position_city` VALUES ('274', '530', '530500000000', '保山市');
INSERT INTO `position_city` VALUES ('275', '530', '530600000000', '昭通市');
INSERT INTO `position_city` VALUES ('276', '530', '530700000000', '丽江市');
INSERT INTO `position_city` VALUES ('277', '530', '530800000000', '普洱市');
INSERT INTO `position_city` VALUES ('278', '530', '530900000000', '临沧市');
INSERT INTO `position_city` VALUES ('279', '530', '532300000000', '楚雄彝族自治州');
INSERT INTO `position_city` VALUES ('280', '530', '532500000000', '红河哈尼族彝族自治州');
INSERT INTO `position_city` VALUES ('281', '530', '532600000000', '文山壮族苗族自治州');
INSERT INTO `position_city` VALUES ('282', '530', '532800000000', '西双版纳傣族自治州');
INSERT INTO `position_city` VALUES ('283', '530', '532900000000', '大理白族自治州');
INSERT INTO `position_city` VALUES ('284', '530', '533100000000', '德宏傣族景颇族自治州');
INSERT INTO `position_city` VALUES ('285', '530', '533300000000', '怒江傈僳族自治州');
INSERT INTO `position_city` VALUES ('286', '530', '533400000000', '迪庆藏族自治州');
INSERT INTO `position_city` VALUES ('287', '540', '540100000000', '拉萨市');
INSERT INTO `position_city` VALUES ('288', '540', '542100000000', '昌都地区');
INSERT INTO `position_city` VALUES ('289', '540', '542200000000', '山南地区');
INSERT INTO `position_city` VALUES ('290', '540', '542300000000', '日喀则地区');
INSERT INTO `position_city` VALUES ('291', '540', '542400000000', '那曲地区');
INSERT INTO `position_city` VALUES ('292', '540', '542500000000', '阿里地区');
INSERT INTO `position_city` VALUES ('293', '540', '542600000000', '林芝地区');
INSERT INTO `position_city` VALUES ('294', '610', '610100000000', '西安市');
INSERT INTO `position_city` VALUES ('295', '610', '610200000000', '铜川市');
INSERT INTO `position_city` VALUES ('296', '610', '610300000000', '宝鸡市');
INSERT INTO `position_city` VALUES ('297', '610', '610400000000', '咸阳市');
INSERT INTO `position_city` VALUES ('298', '610', '610500000000', '渭南市');
INSERT INTO `position_city` VALUES ('299', '610', '610600000000', '延安市');
INSERT INTO `position_city` VALUES ('300', '610', '610700000000', '汉中市');
INSERT INTO `position_city` VALUES ('301', '610', '610800000000', '榆林市');
INSERT INTO `position_city` VALUES ('302', '610', '610900000000', '安康市');
INSERT INTO `position_city` VALUES ('303', '610', '611000000000', '商洛市');
INSERT INTO `position_city` VALUES ('304', '620', '620100000000', '兰州市');
INSERT INTO `position_city` VALUES ('305', '620', '620200000000', '嘉峪关市');
INSERT INTO `position_city` VALUES ('306', '620', '620300000000', '金昌市');
INSERT INTO `position_city` VALUES ('307', '620', '620400000000', '白银市');
INSERT INTO `position_city` VALUES ('308', '620', '620500000000', '天水市');
INSERT INTO `position_city` VALUES ('309', '620', '620600000000', '武威市');
INSERT INTO `position_city` VALUES ('310', '620', '620700000000', '张掖市');
INSERT INTO `position_city` VALUES ('311', '620', '620800000000', '平凉市');
INSERT INTO `position_city` VALUES ('312', '620', '620900000000', '酒泉市');
INSERT INTO `position_city` VALUES ('313', '620', '621000000000', '庆阳市');
INSERT INTO `position_city` VALUES ('314', '620', '621100000000', '定西市');
INSERT INTO `position_city` VALUES ('315', '620', '621200000000', '陇南市');
INSERT INTO `position_city` VALUES ('316', '620', '622900000000', '临夏回族自治州');
INSERT INTO `position_city` VALUES ('317', '620', '623000000000', '甘南藏族自治州');
INSERT INTO `position_city` VALUES ('318', '630', '630100000000', '西宁市');
INSERT INTO `position_city` VALUES ('319', '630', '630200000000', '海东市');
INSERT INTO `position_city` VALUES ('320', '630', '632200000000', '海北藏族自治州');
INSERT INTO `position_city` VALUES ('321', '630', '632300000000', '黄南藏族自治州');
INSERT INTO `position_city` VALUES ('322', '630', '632500000000', '海南藏族自治州');
INSERT INTO `position_city` VALUES ('323', '630', '632600000000', '果洛藏族自治州');
INSERT INTO `position_city` VALUES ('324', '630', '632700000000', '玉树藏族自治州');
INSERT INTO `position_city` VALUES ('325', '630', '632800000000', '海西蒙古族藏族自治州');
INSERT INTO `position_city` VALUES ('326', '640', '640100000000', '银川市');
INSERT INTO `position_city` VALUES ('327', '640', '640200000000', '石嘴山市');
INSERT INTO `position_city` VALUES ('328', '640', '640300000000', '吴忠市');
INSERT INTO `position_city` VALUES ('329', '640', '640400000000', '固原市');
INSERT INTO `position_city` VALUES ('330', '640', '640500000000', '中卫市');
INSERT INTO `position_city` VALUES ('331', '650', '650100000000', '乌鲁木齐市');
INSERT INTO `position_city` VALUES ('332', '650', '650200000000', '克拉玛依市');
INSERT INTO `position_city` VALUES ('333', '650', '652100000000', '吐鲁番地区');
INSERT INTO `position_city` VALUES ('334', '650', '652200000000', '哈密地区');
INSERT INTO `position_city` VALUES ('335', '650', '652300000000', '昌吉回族自治州');
INSERT INTO `position_city` VALUES ('336', '650', '652700000000', '博尔塔拉蒙古自治州');
INSERT INTO `position_city` VALUES ('337', '650', '652800000000', '巴音郭楞蒙古自治州');
INSERT INTO `position_city` VALUES ('338', '650', '652900000000', '阿克苏地区');
INSERT INTO `position_city` VALUES ('339', '650', '653000000000', '克孜勒苏柯尔克孜自治州');
INSERT INTO `position_city` VALUES ('340', '650', '653100000000', '喀什地区');
INSERT INTO `position_city` VALUES ('341', '650', '653200000000', '和田地区');
INSERT INTO `position_city` VALUES ('342', '650', '654000000000', '伊犁哈萨克自治州');
INSERT INTO `position_city` VALUES ('343', '650', '654200000000', '塔城地区');
INSERT INTO `position_city` VALUES ('344', '650', '654300000000', '阿勒泰地区');
INSERT INTO `position_city` VALUES ('345', '650', '659000000000', '自治区直辖县级行政区划');
