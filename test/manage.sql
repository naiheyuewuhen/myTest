/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : manage

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-05-13 11:03:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `module_id` int(11) NOT NULL COMMENT '模块（菜单）id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('39', '2', '2');
INSERT INTO `authority` VALUES ('40', '2', '21');
INSERT INTO `authority` VALUES ('41', '2', '211');
INSERT INTO `authority` VALUES ('42', '2', '2111');
INSERT INTO `authority` VALUES ('43', '2', '2112');
INSERT INTO `authority` VALUES ('44', '2', '3');
INSERT INTO `authority` VALUES ('45', '2', '31');
INSERT INTO `authority` VALUES ('46', '2', '311');
INSERT INTO `authority` VALUES ('47', '2', '3112');
INSERT INTO `authority` VALUES ('65', '3', '3');
INSERT INTO `authority` VALUES ('66', '3', '31');
INSERT INTO `authority` VALUES ('67', '3', '311');
INSERT INTO `authority` VALUES ('68', '3', '3113');
INSERT INTO `authority` VALUES ('69', '4', '3');
INSERT INTO `authority` VALUES ('70', '4', '31');
INSERT INTO `authority` VALUES ('71', '4', '311');
INSERT INTO `authority` VALUES ('72', '4', '3114');
INSERT INTO `authority` VALUES ('110', '1', '1');
INSERT INTO `authority` VALUES ('111', '1', '12');
INSERT INTO `authority` VALUES ('112', '1', '121');
INSERT INTO `authority` VALUES ('113', '1', '1211');
INSERT INTO `authority` VALUES ('114', '1', '1212');
INSERT INTO `authority` VALUES ('115', '1', '2');
INSERT INTO `authority` VALUES ('116', '1', '21');
INSERT INTO `authority` VALUES ('117', '1', '211');
INSERT INTO `authority` VALUES ('118', '1', '2111');
INSERT INTO `authority` VALUES ('119', '1', '2112');
INSERT INTO `authority` VALUES ('120', '1', '3');
INSERT INTO `authority` VALUES ('121', '1', '31');
INSERT INTO `authority` VALUES ('122', '1', '311');
INSERT INTO `authority` VALUES ('123', '1', '3112');
INSERT INTO `authority` VALUES ('124', '1', '3113');
INSERT INTO `authority` VALUES ('125', '1', '3114');

-- ----------------------------
-- Table structure for `contact_resolve`
-- ----------------------------
DROP TABLE IF EXISTS `contact_resolve`;
CREATE TABLE `contact_resolve` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `resolve_name` varchar(255) DEFAULT NULL COMMENT '拆解名称',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改 时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) DEFAULT '2' COMMENT '状态：0：无效；1：有效（拆解完成）；2：暂存（未生效）；3：供应商确认中；4：供应商确认完成；5:已生成采购单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='合同拆解主表';

-- ----------------------------
-- Records of contact_resolve
-- ----------------------------
INSERT INTO `contact_resolve` VALUES ('3', '24', '机电拆解', 'admin', '2018-03-05 00:25:59', 'admin', '2018-03-05 00:25:59', null, '2');
INSERT INTO `contact_resolve` VALUES ('5', '24', '机电拆解2', 'admin', '2018-03-05 00:32:36', 'admin', '2018-03-08 13:52:34', null, '2');
INSERT INTO `contact_resolve` VALUES ('6', '24', '机电拆解3', 'admin', '2018-03-05 00:40:12', 'admin', '2018-03-14 01:24:02', null, '4');
INSERT INTO `contact_resolve` VALUES ('7', '25', '机电', 'admin', '2018-03-19 15:43:15', 'system', '2018-03-19 22:26:48', null, '4');
INSERT INTO `contact_resolve` VALUES ('8', '25', '机械', 'admin', '2018-03-19 15:56:38', 'me', '2018-03-19 16:38:37', null, '2');
INSERT INTO `contact_resolve` VALUES ('9', '27', '机电拆解', 'system', '2018-03-19 22:06:01', 'system', '2018-03-19 22:06:01', null, '1');
INSERT INTO `contact_resolve` VALUES ('10', '30', '拆分11', 'system', '2018-05-12 11:48:07', 'system', '2018-05-12 14:23:31', null, '2');
INSERT INTO `contact_resolve` VALUES ('11', '30', '拆分22', 'system', '2018-05-12 11:48:07', 'system', '2018-05-12 11:51:36', null, '0');
INSERT INTO `contact_resolve` VALUES ('12', '30', '拆分3', 'system', '2018-05-12 11:48:07', 'system', '2018-05-13 03:50:27', null, '3');
INSERT INTO `contact_resolve` VALUES ('13', '30', '拆分5', 'system', '2018-05-12 11:51:55', 'system', '2018-05-12 23:19:50', null, '5');
INSERT INTO `contact_resolve` VALUES ('14', '22', '12拆分1', 'system', '2018-05-12 13:50:58', 'system', '2018-05-12 16:39:31', null, '1');
INSERT INTO `contact_resolve` VALUES ('15', '22', '12拆分2', 'system', '2018-05-12 13:50:58', 'system', '2018-05-13 10:18:34', null, '1');

-- ----------------------------
-- Table structure for `contract_file`
-- ----------------------------
DROP TABLE IF EXISTS `contract_file`;
CREATE TABLE `contract_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `upload_file_id` bigint(20) NOT NULL COMMENT '上传文件id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '附件名称',
  `file_url` varchar(255) DEFAULT NULL COMMENT '附件存放地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `file_type` varchar(255) NOT NULL COMMENT '文件类型：main：合同文本，attachment：附件',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='合同附件表';

-- ----------------------------
-- Records of contract_file
-- ----------------------------
INSERT INTO `contract_file` VALUES ('1', '16', '12', '博士.png', '/upload/博士.png', null, '', '1');
INSERT INTO `contract_file` VALUES ('2', '17', '12', '博士.png', '/upload/博士.png', null, '', '1');
INSERT INTO `contract_file` VALUES ('3', '18', '12', '博士.png', '/upload/博士.png', null, '', '1');
INSERT INTO `contract_file` VALUES ('4', '19', '12', '博士.png', '/upload/博士.png', null, '', '1');
INSERT INTO `contract_file` VALUES ('5', '20', '12', '博士.png', '/upload/博士.png', null, '', '1');
INSERT INTO `contract_file` VALUES ('6', '21', '12', '博士.png', '/upload/博士.png', null, '', '1');
INSERT INTO `contract_file` VALUES ('7', '22', '17', '0.jpg', '/upload/0.jpg', null, '', '1');
INSERT INTO `contract_file` VALUES ('8', '22', '18', 'QQ图片20170511171448.png', '/upload/QQ图片20170511171448.png', null, 'main', '1');
INSERT INTO `contract_file` VALUES ('9', '22', '19', '博士.png', '/upload/博士.png', null, 'main', '1');
INSERT INTO `contract_file` VALUES ('10', '22', '20', '博士2.png', '/upload/博士2.png', null, 'main', '1');
INSERT INTO `contract_file` VALUES ('11', '23', '23', '博士.png', '/upload/博士.png', '2017-11-22 19:47:34', 'main', '1');
INSERT INTO `contract_file` VALUES ('12', '23', '24', '博士1.png', '/upload/博士1.png', '2017-11-22 19:47:40', 'main', '1');
INSERT INTO `contract_file` VALUES ('13', '23', '25', '博士2.png', '/upload/博士2.png', '2017-11-22 19:47:40', 'main', '1');
INSERT INTO `contract_file` VALUES ('14', '23', '26', '0.jpg', '/upload/0.jpg', '2017-11-22 19:49:31', 'main', '0');
INSERT INTO `contract_file` VALUES ('15', '23', '27', 'QQ图片20170511171448.png', '/upload/QQ图片20170511171448.png', '2017-11-23 11:24:03', 'main', '1');
INSERT INTO `contract_file` VALUES ('16', '24', '28', '博士.png', '/upload/博士.png', '2017-11-23 11:41:35', 'main', '0');
INSERT INTO `contract_file` VALUES ('17', '24', '34', '博士2.png', '/upload/博士2.png', '2017-12-03 14:26:13', 'main', '0');
INSERT INTO `contract_file` VALUES ('18', '24', '36', '0.jpg', '/upload/7a6b4ab4bc388ca3143f3abb085472c2', '2017-12-08 15:49:02', 'main', '1');
INSERT INTO `contract_file` VALUES ('19', '24', '37', 'untitled.png', '/upload/991507d4941d412ec42f83b4486a33ab', '2017-12-08 15:49:07', 'main', '1');
INSERT INTO `contract_file` VALUES ('20', '25', '41', '0.jpg', '/upload/135a262de29bf4aa5f9bd93927551562', '2018-03-19 14:00:50', 'main', '1');
INSERT INTO `contract_file` VALUES ('21', '25', '42', '博士.png', '/upload/ea59f4cc0388c60fac31be52e3f590aa', '2018-03-19 14:00:50', 'main', '1');
INSERT INTO `contract_file` VALUES ('22', '26', '43', 'QQ图片20170511171448.png', '/upload/2adb0e7e73dedbab20cedf9401c12b7f', '2018-03-19 17:49:57', 'main', '1');
INSERT INTO `contract_file` VALUES ('23', '26', '44', '博士.png', '/upload/c6fbe0e3515ab4284fd94dd11efaeaec', '2018-03-19 17:50:04', 'main', '1');
INSERT INTO `contract_file` VALUES ('24', '27', '45', 'QQ图片20170511171448.png', '/upload/c2d7e0f6a1b78bc4d7609d9c76011e30', '2018-03-19 21:00:37', 'main', '1');
INSERT INTO `contract_file` VALUES ('25', '27', '47', '博士1.png', '/upload/33bd375ccaf41d5a1b5e1165ce57ba90', '2018-03-19 21:00:47', 'main', '1');
INSERT INTO `contract_file` VALUES ('26', '30', '54', '0.jpg', '/upload/4f6a9fa199047c0d9e66c118f7846c79', '2018-03-29 00:27:29', 'main', '1');
INSERT INTO `contract_file` VALUES ('27', '30', '55', 'QQ图片20170511171448.png', '/upload/21c5c5adece121e4d188d02d4748bab1', '2018-03-29 00:27:29', 'main', '0');
INSERT INTO `contract_file` VALUES ('28', '30', '56', '博士1.png', '/upload/52b253d0ab9e1f9b7c8980efb4b86af3', '2018-03-29 00:27:41', 'attachment', '1');
INSERT INTO `contract_file` VALUES ('29', '30', '57', '博士2.png', '/upload/22c5b10de80791e9aea21fd65c1b37a9', '2018-03-29 00:27:41', 'attachment', '0');
INSERT INTO `contract_file` VALUES ('30', '30', '58', '俩树叶.png', '/upload/6d9c9daf98c2b21fb705ee3f76da6f53', '2018-03-29 00:51:07', 'main', '1');
INSERT INTO `contract_file` VALUES ('31', '30', '59', '博士.png', '/upload/acb34052adfe3c0d9487bcf9c13a5a66', '2018-03-29 00:51:17', 'attachment', '1');
INSERT INTO `contract_file` VALUES ('32', '31', '60', '博士.png', '/upload/3764b90d0f81e5fab29d9878fb58fba0', '2018-05-13 08:33:26', 'main', '1');
INSERT INTO `contract_file` VALUES ('33', '31', '62', '俩树叶.png', '/upload/8dee7c93713598059b39fdba3fe96b38', '2018-05-13 08:33:36', 'attachment', '1');

-- ----------------------------
-- Table structure for `contract_goods`
-- ----------------------------
DROP TABLE IF EXISTS `contract_goods`;
CREATE TABLE `contract_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `resolve_id` bigint(20) NOT NULL COMMENT '合同拆解主表id',
  `goods_id` bigint(20) NOT NULL COMMENT '原材料id',
  `goods_num` int(11) NOT NULL COMMENT '原材料数量',
  `price` decimal(19,4) DEFAULT NULL COMMENT '单价（当时采购单价）',
  `price_total` decimal(19,4) DEFAULT NULL COMMENT '合计金额（当时采购合计金额）',
  `supplier_ids_advise` varchar(255) DEFAULT NULL COMMENT '推荐的供应商id（多个的情况用英文逗点“,”分割）',
  `supplier_names_advise` varchar(255) DEFAULT NULL COMMENT '推荐的供应商名字（多个的情况用英文逗点“,”分割）',
  `supplier_id_reality` bigint(20) DEFAULT NULL COMMENT '实际的供应商id',
  `supplier_name_reality` varchar(255) DEFAULT NULL COMMENT '实际的供应商名字',
  `create_user` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '最后修改者',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `reality_user` varchar(255) DEFAULT NULL COMMENT '采购确认人',
  `reality_time` datetime DEFAULT NULL COMMENT '采购确认时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `complete` bigint(4) NOT NULL DEFAULT '0' COMMENT '配货状态：0：未配齐；1：已配齐',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效；',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='合同包含的原材料表';

-- ----------------------------
-- Records of contract_goods
-- ----------------------------
INSERT INTO `contract_goods` VALUES ('1', '24', '3', '3', '1', null, null, '2', '供应商2', null, null, 'admin', '2018-03-05 00:25:59', 'admin', '2018-03-05 00:25:59', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('2', '24', '3', '1', '1', null, null, '2,3', '供应商2,供应商3', null, null, 'admin', '2018-03-05 00:25:59', 'admin', '2018-03-05 00:25:59', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('3', '24', '3', '2', '1', null, null, '5,1,3,4', '供应商5,供应商1,供应商3,供应商4', null, null, 'admin', '2018-03-05 00:25:59', 'admin', '2018-03-05 00:25:59', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('7', '24', '5', '3', '1', null, null, '5,1,2,4', '供应商5,供应商1,供应商2,供应商4', null, null, 'admin', '2018-03-05 00:32:36', 'admin', '2018-03-05 00:32:36', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('8', '24', '5', '2', '1', null, null, '5,1,3', '供应商5,供应商1,供应商3', null, null, 'admin', '2018-03-05 00:32:36', 'admin', '2018-03-05 00:32:36', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('9', '24', '5', '1', '1', null, null, '2,3', '供应商2,供应商3', null, null, 'admin', '2018-03-05 00:32:36', 'admin', '2018-03-05 00:32:36', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('10', '24', '6', '1', '3', '69.0000', null, '2,3,4', '供应商2,供应商3,供应商4', '3', '供应商3', 'admin', '2018-03-05 00:40:12', 'admin', '2018-03-14 01:23:26', 'admin', '2018-03-14 01:24:02', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('11', '24', '6', '2', '2', '1.4000', null, '5,1,4', '供应商5,供应商1,供应商4', null, null, 'admin', '2018-03-08 15:45:07', 'admin', '2018-03-08 15:45:07', null, null, null, '0', '0');
INSERT INTO `contract_goods` VALUES ('12', '24', '6', '3', '6', '80.0000', null, '5,2,4', '供应商5,供应商2,供应商4', '2', '供应商2', 'admin', '2018-03-08 15:55:14', 'admin', '2018-03-14 01:23:26', 'admin', '2018-03-14 01:24:02', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('13', '24', '6', '2', '1', '78.0000', null, '5,1,3,4', '供应商5,供应商1,供应商3,供应商4', '3', '供应商3', 'admin', '2018-03-14 01:23:21', 'admin', '2018-03-14 01:23:26', 'admin', '2018-03-14 01:24:02', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('14', '25', '7', '1', '1', '50.0000', null, '2,3', '供应商2,供应商3', '3', '供应商3', 'admin', '2018-03-19 15:43:15', 'admin', '2018-03-19 15:43:15', 'system', '2018-03-19 22:26:48', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('15', '25', '7', '4', '3', '66.0000', null, '6', '供应商6', '6', '供应商6', 'admin', '2018-03-19 15:43:15', 'admin', '2018-03-19 15:43:15', 'system', '2018-03-19 22:26:48', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('16', '25', '8', '2', '7', null, null, '5,1,6', '供应商5,供应商1,供应商6', null, null, 'admin', '2018-03-19 15:56:38', 'me', '2018-03-19 16:38:37', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('17', '25', '8', '1', '8', null, null, '5,2,3', '供应商5,供应商2,供应商3', null, null, 'admin', '2018-03-19 15:56:38', 'me', '2018-03-19 16:38:37', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('18', '27', '9', '2', '1', null, null, '1,6,3', '供应商1,供应商6,供应商3', null, null, 'system', '2018-03-19 22:06:01', 'system', '2018-03-19 22:06:01', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('19', '27', '9', '1', '1', null, null, '5,7,2,4', '供应商5,供应商222,供应商2,供应商4', null, null, 'system', '2018-03-19 22:06:01', 'system', '2018-03-19 22:06:01', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('20', '30', '13', '1', '22', '50.0000', null, null, null, '3', '供应商3', 'system', '2018-05-12 13:26:51', 'system', '2018-05-12 14:23:52', 'system', '2018-05-12 23:19:50', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('21', '30', '13', '3', '3', '80.0000', null, null, null, '2', '供应商2', 'system', '2018-05-12 13:26:51', 'system', '2018-05-12 14:23:52', 'system', '2018-05-12 23:19:50', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('22', '30', '13', '4', '9', '66.0000', null, null, null, '6', '供应商6', 'system', '2018-05-12 13:26:51', 'system', '2018-05-12 14:23:52', 'system', '2018-05-12 23:19:50', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('23', '30', '13', '2', '2', '78.0000', null, null, null, '3', '供应商3', 'system', '2018-05-12 13:26:51', 'system', '2018-05-12 14:23:52', 'system', '2018-05-12 23:19:50', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('24', '30', '10', '4', '9', null, null, null, null, null, null, 'system', '2018-05-12 14:23:31', 'system', '2018-05-12 14:23:31', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('25', '30', '10', '2', '12', null, null, null, null, null, null, 'system', '2018-05-12 14:23:31', 'system', '2018-05-12 14:23:31', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('26', '30', '10', '1', '6', null, null, null, null, null, null, 'system', '2018-05-12 14:23:31', 'system', '2018-05-12 14:23:31', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('27', '30', '12', '3', '3', '80.0000', null, null, null, '2', '供应商2', 'system', '2018-05-12 14:23:46', 'system', '2018-05-12 16:40:32', 'system', '2018-05-13 03:50:27', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('28', '30', '12', '2', '4', null, null, null, null, null, null, 'system', '2018-05-12 14:23:46', 'system', '2018-05-12 14:23:46', null, null, null, '0', '0');
INSERT INTO `contract_goods` VALUES ('29', '22', '14', '4', '9', null, null, null, null, null, null, 'system', '2018-05-12 16:39:31', 'system', '2018-05-12 16:39:31', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('30', '22', '14', '2', '12', null, null, null, null, null, null, 'system', '2018-05-12 16:39:31', 'system', '2018-05-12 16:39:31', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('31', '22', '14', '1', '6', null, null, null, null, null, null, 'system', '2018-05-12 16:39:31', 'system', '2018-05-12 16:39:31', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('32', '30', '12', '4', '9', '4.0000', null, null, null, '7', '供应商222', 'system', '2018-05-12 16:40:32', 'system', '2018-05-12 16:40:32', 'system', '2018-05-13 03:50:27', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('33', '30', '12', '2', '12', '78.0000', null, null, null, '3', '供应商3', 'system', '2018-05-12 16:40:32', 'system', '2018-05-12 16:40:32', 'system', '2018-05-13 03:50:27', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('34', '30', '12', '1', '6', '2.0000', null, null, null, '7', '供应商222', 'system', '2018-05-12 16:40:32', 'system', '2018-05-12 16:40:32', 'system', '2018-05-13 03:50:27', null, '0', '1');
INSERT INTO `contract_goods` VALUES ('35', '22', '15', '3', '3', null, null, null, null, null, null, 'system', '2018-05-12 18:52:10', 'system', '2018-05-12 18:53:17', null, null, null, '0', '0');
INSERT INTO `contract_goods` VALUES ('36', '22', '15', '2', '4', null, null, null, null, null, null, 'system', '2018-05-12 18:52:10', 'system', '2018-05-12 18:53:17', null, null, null, '0', '0');
INSERT INTO `contract_goods` VALUES ('37', '22', '15', '1', '1', null, null, null, null, null, null, 'system', '2018-05-13 10:18:34', 'system', '2018-05-13 10:18:34', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('38', '22', '15', '4', '9', null, null, null, null, null, null, 'system', '2018-05-13 10:18:34', 'system', '2018-05-13 10:18:34', null, null, null, '0', '1');
INSERT INTO `contract_goods` VALUES ('39', '22', '15', '2', '12', null, null, null, null, null, null, 'system', '2018-05-13 10:18:34', 'system', '2018-05-13 10:18:34', null, null, null, '0', '1');

-- ----------------------------
-- Table structure for `contract_goods_receiving`
-- ----------------------------
DROP TABLE IF EXISTS `contract_goods_receiving`;
CREATE TABLE `contract_goods_receiving` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `goods_id` bigint(20) NOT NULL COMMENT '原材料id',
  `goods_num` int(11) NOT NULL COMMENT '原材料数量',
  `supplier_id` bigint(20) NOT NULL COMMENT '供应商id',
  `consignee` varchar(255) DEFAULT NULL COMMENT '收货人',
  `reception_time` datetime DEFAULT NULL COMMENT '验收时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `purchase_order_id` bigint(20) DEFAULT NULL COMMENT '采购订单id',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='采购验收单明细表';

-- ----------------------------
-- Records of contract_goods_receiving
-- ----------------------------
INSERT INTO `contract_goods_receiving` VALUES ('1', '24', '1', '1', '3', null, null, 'admin', '2018-03-14 14:22:11', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('2', '24', '2', '1', '3', null, null, 'admin', '2018-03-14 14:22:11', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('3', '24', '1', '1', '3', null, null, 'admin', '2018-03-14 14:22:51', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('4', '24', '1', '-1', '3', null, null, 'admin', '2018-03-14 14:23:15', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('5', '24', '1', '1', '3', null, null, 'system', '2018-03-19 22:27:45', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('6', '30', '1', '1', '3', null, null, 'system', '2018-05-12 23:29:54', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('7', '30', '4', '1', '6', null, null, 'system', '2018-05-13 08:17:19', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('8', '30', '3', '1', '2', null, null, 'system', '2018-05-13 08:17:38', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('9', '30', '2', '1', '3', null, null, 'system', '2018-05-13 08:18:08', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('10', '30', '1', '1', '3', null, null, 'system', '2018-05-13 08:18:08', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('11', '30', '1', '1', '3', null, null, 'system', '2018-05-13 08:29:23', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('12', '30', '2', '1', '3', null, null, 'system', '2018-05-13 08:29:23', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('13', '30', '3', '1', '2', null, null, 'system', '2018-05-13 08:29:40', null, '1');
INSERT INTO `contract_goods_receiving` VALUES ('14', '30', '1', '1', '3', null, null, 'system', '2018-05-13 10:29:44', null, '1');

-- ----------------------------
-- Table structure for `contract_goods_temp`
-- ----------------------------
DROP TABLE IF EXISTS `contract_goods_temp`;
CREATE TABLE `contract_goods_temp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `goods_id` bigint(20) NOT NULL COMMENT '原材料id',
  `goods_num` int(11) NOT NULL COMMENT '原材料数量',
  `supplier_ids` varchar(255) DEFAULT NULL COMMENT '推荐的供应商id（多个的情况用英文逗点“,”分割）',
  `group_id` bigint(20) DEFAULT NULL COMMENT '项目指标id（非空时标识是从项目指标中选择的原材料）',
  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同包含的原材料临时表（页面数据量大时，可以暂存）';

-- ----------------------------
-- Records of contract_goods_temp
-- ----------------------------

-- ----------------------------
-- Table structure for `contract_info`
-- ----------------------------
DROP TABLE IF EXISTS `contract_info`;
CREATE TABLE `contract_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `father_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '本补充合同所依附的主合同id（0：表示本合同不是补充合同，即是“主合同”）',
  `code` varchar(255) NOT NULL COMMENT '合同编号',
  `first_party` varchar(255) NOT NULL COMMENT '甲方（单位）',
  `first_address` varchar(255) NOT NULL COMMENT '甲方地址',
  `first_linkman` varchar(255) NOT NULL COMMENT '甲方联系人',
  `first_phone` varchar(255) NOT NULL COMMENT '甲方联系电话',
  `second_party` varchar(255) NOT NULL COMMENT '乙方（单位）',
  `second_address` varchar(255) NOT NULL COMMENT '乙方地址',
  `second_linkman` varchar(255) NOT NULL COMMENT '乙方联系人',
  `second_phone` varchar(255) NOT NULL COMMENT '乙方联系电话',
  `contract_date` date NOT NULL COMMENT '签约时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '最后修改者',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注信息',
  `complete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '配货状态：0：未配齐；1：已配齐',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '合同状态（0：待审核；1:最终审核通过；2：审核未通过；3：完成；4：合同取消；5:一级审核通过；6:可拆解状态；7:拆解进行中；8:合同拆解完成；9:供应商确认中；10:供应商确认完成）',
  `auditor` varchar(255) DEFAULT NULL COMMENT '审批人',
  `auditing_time` datetime DEFAULT NULL COMMENT '审批时间',
  `auditing_remark` varchar(255) DEFAULT NULL COMMENT '审批意见',
  `total_amount` decimal(19,4) DEFAULT NULL COMMENT '合同总金额',
  `contract_type` int(11) DEFAULT NULL COMMENT '合同类型：0:非标准合同；1:标准合同',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='合同表';

-- ----------------------------
-- Records of contract_info
-- ----------------------------
INSERT INTO `contract_info` VALUES ('1', '0', '1111', '1', '1', '1', '1', '1', '1', '1', '1', '2017-11-05', '1', '2017-11-05 15:12:33', '1', '2017-11-05 15:12:40', '1', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('2', '0', '2222', '2', '2', '2', '2', '2', '2', '2', '2', '2017-11-05', 'admin', '2017-11-05 23:01:15', 'admin', '2017-11-05 23:01:15', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('3', '0', '2111', '2', '2', '2', '2', '2', '2', '2', '2', '2017-11-05', 'admin', '2017-11-05 23:02:04', 'admin', '2017-11-05 23:02:04', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('4', '0', '3111', '3', '3', '3', '3', '3', '3', '3', '3', '2017-11-07', 'admin', '2017-11-06 10:40:57', 'admin', '2017-11-06 10:40:57', '3', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('5', '0', '3', '3', '3', '3', '3', '3', '3', '3', '3', '2017-11-07', 'admin', '2017-11-06 10:44:19', 'admin', '2017-11-06 10:44:19', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('6', '0', '3', '3', '3', '3', '3', '3', '3', '3', '3', '2017-11-07', 'admin', '2017-11-06 10:50:10', 'admin', '2017-11-06 10:50:10', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('7', '0', '3', '3', '3', '3', '3', '3', '3', '3', '3', '2017-11-07', 'admin', '2017-11-06 10:53:26', 'admin', '2017-11-06 10:53:26', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('8', '0', '3', '3', '3', '3', '3', '3', '3', '3', '3', '2017-11-07', 'admin', '2017-11-06 10:54:48', 'admin', '2017-11-06 10:54:48', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('9', '0', '3', '3', '3', '3', '3', '3', '3', '3', '3', '2017-11-07', 'admin', '2017-11-06 10:55:24', 'admin', '2017-11-06 10:55:24', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('10', '0', '4', '4', '4', '4', '4', '4', '4', '44', '4', '2017-11-06', 'admin', '2017-11-06 11:54:18', 'admin', '2017-11-06 11:54:18', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('11', '0', '4', '4', '4', '4', '4', '4', '4', '4', '4', '2017-11-06', 'admin', '2017-11-06 13:59:13', 'admin', '2017-11-06 13:59:13', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('12', '0', '4', '4', '4', '4', '4', '4', '4', '4', '4', '2017-11-06', 'admin', '2017-11-06 13:59:47', 'admin', '2017-11-06 13:59:47', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('13', '0', '4', '4', '4', '4', '4', '4', '4', '4', '4', '2017-11-06', 'admin', '2017-11-06 19:25:35', 'admin', '2017-11-06 19:25:35', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('14', '0', '4', '4', '4', '4', '4', '4', '4', '4', '4', '2017-11-06', 'admin', '2017-11-06 19:26:51', 'admin', '2017-11-06 19:26:51', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('15', '0', '12', '甲方', '地址', '人', '12121212', '乙方', '地址', '人', '2323', '2017-11-09', 'admin', '2017-11-09 10:30:24', 'admin', '2017-11-09 10:30:24', '无备注', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('16', '0', '001', '北京甲方', '北京朝阳区', '甲方人', '11111', '上海乙方', '上海浦东', '乙方人', '22222', '2017-11-19', 'admin', '2017-11-19 18:06:46', 'admin', '2017-11-19 18:06:46', '测试备注内容', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('17', '0', '001', '北京甲方', '北京朝阳区', '甲方人', '11111', '上海乙方', '上海浦东', '乙方人', '22222', '2017-11-19', 'admin', '2017-11-19 18:12:46', 'admin', '2017-11-19 18:12:46', '测试备注内容', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('18', '0', '001', '北京甲方', '北京朝阳区', '甲方人', '11111', '上海乙方', '上海浦东', '乙方人', '22222', '2017-11-19', 'admin', '2017-11-19 18:14:45', 'admin', '2017-11-19 18:14:45', '测试备注内容', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('19', '0', '001', '北京甲方', '北京朝阳区', '甲方人', '11111', '上海乙方', '上海浦东', '乙方人', '22222', '2017-11-19', 'admin', '2017-11-19 18:16:15', 'admin', '2017-11-19 18:16:15', '测试备注内容', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('20', '0', '001', '北京甲方', '北京朝阳区', '甲方人', '11111', '上海乙方', '上海浦东', '乙方人', '22222', '2017-11-19', 'admin', '2017-11-19 18:17:26', 'admin', '2017-11-19 18:17:26', '测试备注内容', '0', '2', 'admin', '2018-03-19 15:31:13', '不同意', null, null);
INSERT INTO `contract_info` VALUES ('21', '4', '001', '北京甲方', '北京朝阳区', '甲方人', '11111', '上海乙方', '上海浦东', '乙方人', '22222', '2017-11-19', 'admin', '2017-11-19 18:18:51', 'admin', '2017-11-19 18:18:51', '测试备注内容', '0', '1', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('22', '0', '112233', '12', '12', '12', '12', '12', '12', '12', '12', '2017-11-22', 'admin', '2017-11-22 14:30:00', 'admin', '2017-11-22 14:30:00', '12', '0', '8', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('23', '0', '1233', '1233', '1233', '1233', '1233', '1233', '1233', '1233', '1233', '2017-11-20', 'admin', '2017-11-22 19:47:45', 'admin', '2017-11-23 11:24:07', '33333', '0', '1', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('24', '23', '拆分001', '1324', '134', '143', '143', '1234', '134', '1234', '1324', '2017-11-23', 'admin', '2017-11-23 11:41:40', 'admin', '2017-12-08 15:49:16', '1324', '0', '1', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('25', '0', '新增合同001', '佛界', '西天', '如来', '18888888888', '仙界', '天堂', '玉皇', '16666666666', '2018-03-01', 'admin', '2018-03-19 14:00:55', 'admin', '2018-03-19 14:00:55', '', '0', '6', 'admin', '2018-03-19 15:18:12', '我已同意', null, null);
INSERT INTO `contract_info` VALUES ('26', '0', '测试01', '甲甲甲', '北京南', '甲', '13288888888', '乙乙乙', '上海北', '乙', '15266666666', '2018-03-01', 'system', '2018-03-19 17:50:08', 'system', '2018-03-19 17:50:08', '测试合同', '0', '0', null, null, null, null, null);
INSERT INTO `contract_info` VALUES ('27', '0', '合同2018007', '甲方', '大立科技唠嗑', '张总', '13333333333', '乙方', '阿萨德发的', '更改', '16800000000', '2018-03-06', 'system', '2018-03-19 21:00:55', 'system', '2018-03-19 21:00:55', '', '0', '1', 'system', '2018-03-19 21:03:26', '同意', null, null);
INSERT INTO `contract_info` VALUES ('28', '0', '文本分开', '甲', '甲地', '甲人', '11111111111', '乙', '乙地', '乙人', '12121212121', '2018-03-01', 'system', '2018-03-29 00:27:46', 'system', '2018-03-29 00:27:46', '记录', '0', '1', 'system', '2018-05-12 00:13:19', '同意了', null, null);
INSERT INTO `contract_info` VALUES ('29', '0', '文本分开2', '甲', '甲地', '甲人', '11111111111', '乙', '乙地', '乙人', '12121212121', '2018-03-01', 'system', '2018-03-29 00:35:58', 'system', '2018-03-29 00:35:58', '记录', '0', '5', 'ceshi1', '2018-05-12 00:31:05', '一级同意2', null, null);
INSERT INTO `contract_info` VALUES ('30', '0', '文本分开3', '甲', '甲地', '甲人', '11111111111', '乙', '乙地', '乙人', '12121212121', '2018-03-01', 'system', '2018-03-29 00:45:42', 'system', '2018-05-10 00:55:50', '记录', '0', '9', 'ceshi1', '2018-05-12 00:30:42', '一级同意', '100.0000', '1');
INSERT INTO `contract_info` VALUES ('31', '0', '多个', '我', ' 规范', '的复华', '11111', '个', '按规定', '电饭锅', '22222', '2018-05-06', 'system', '2018-05-13 08:33:40', 'system', '2018-05-13 08:33:40', '', '0', '6', null, null, null, '1000.0000', '0');

-- ----------------------------
-- Table structure for `contract_pay_standard`
-- ----------------------------
DROP TABLE IF EXISTS `contract_pay_standard`;
CREATE TABLE `contract_pay_standard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` date DEFAULT NULL COMMENT '起始时间',
  `end_date` date DEFAULT NULL COMMENT '截止日期',
  `pay_no1` decimal(19,4) DEFAULT NULL COMMENT '第1笔款',
  `pay_no2` decimal(19,4) DEFAULT NULL COMMENT '第2笔款',
  `pay_no3` decimal(19,4) DEFAULT NULL COMMENT '第3笔款',
  `pay_no4` decimal(19,4) DEFAULT NULL COMMENT '第4笔款',
  `pay_no5` decimal(19,4) DEFAULT NULL COMMENT '第5笔款',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract_pay_standard
-- ----------------------------
INSERT INTO `contract_pay_standard` VALUES ('1', '2018-05-01', '2018-05-12', '30.0000', '50.0000', '10.0000', '10.0000', '0.0000');
INSERT INTO `contract_pay_standard` VALUES ('2', '2018-03-05', '2018-03-06', '100.0000', '0.0000', '0.0000', '0.0000', '0.0000');
INSERT INTO `contract_pay_standard` VALUES ('3', '2018-03-07', '2018-03-08', '100.0000', '0.0000', '0.0000', '0.0000', '0.0000');
INSERT INTO `contract_pay_standard` VALUES ('4', '2018-03-09', '2018-03-09', '100.0000', '0.0000', '0.0000', '0.0000', '0.0000');

-- ----------------------------
-- Table structure for `goods_group`
-- ----------------------------
DROP TABLE IF EXISTS `goods_group`;
CREATE TABLE `goods_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码（编号）',
  `name` varchar(255) NOT NULL COMMENT '项目指标（即一组物品）名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='项目指标表（即一组选好的原材料组合）';

-- ----------------------------
-- Records of goods_group
-- ----------------------------
INSERT INTO `goods_group` VALUES ('1', '1001', '指标1', '测试', '1');
INSERT INTO `goods_group` VALUES ('2', '1002', '项目2', '', '1');

-- ----------------------------
-- Table structure for `goods_group_detail`
-- ----------------------------
DROP TABLE IF EXISTS `goods_group_detail`;
CREATE TABLE `goods_group_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL COMMENT '项目指标id',
  `goods_id` bigint(20) NOT NULL COMMENT '原材料id',
  `goods_num` int(11) NOT NULL COMMENT '原材料数量',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '默认的供应商id',
  `status` int(10) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='项目指标明细表';

-- ----------------------------
-- Records of goods_group_detail
-- ----------------------------
INSERT INTO `goods_group_detail` VALUES ('1', '1', '4', '9', null, '1');
INSERT INTO `goods_group_detail` VALUES ('2', '1', '2', '12', null, '1');
INSERT INTO `goods_group_detail` VALUES ('3', '1', '1', '6', null, '0');
INSERT INTO `goods_group_detail` VALUES ('4', '2', '3', '3', null, '1');
INSERT INTO `goods_group_detail` VALUES ('5', '2', '2', '4', null, '1');

-- ----------------------------
-- Table structure for `goods_info`
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码（编号）',
  `name` varchar(255) NOT NULL COMMENT '原材料名称',
  `standard` varchar(255) NOT NULL COMMENT '规格',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `sort_num` bigint(20) NOT NULL COMMENT '序号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='原材料表';

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES ('1', '001', '名称1', '盒', '2017-11-25 01:16:28', '备注', '0', '1');
INSERT INTO `goods_info` VALUES ('2', '002', '名称2', '米', '2017-11-25 01:16:35', '', '1', '1');
INSERT INTO `goods_info` VALUES ('3', '003', '名称3', '个', '2017-11-26 16:36:50', '', '0', '1');
INSERT INTO `goods_info` VALUES ('4', '004', '材料4', '根', '2018-03-17 10:46:35', '', '2', '1');
INSERT INTO `goods_info` VALUES ('5', '111', '改了', '根', '2018-03-19 20:51:42', '', '0', '1');

-- ----------------------------
-- Table structure for `goods_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `goods_supplier`;
CREATE TABLE `goods_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL COMMENT '原材料id',
  `supplier_id` bigint(20) NOT NULL COMMENT '供应商id',
  `price` decimal(19,4) NOT NULL COMMENT '价格',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='原材料-供应商关联表';

-- ----------------------------
-- Records of goods_supplier
-- ----------------------------
INSERT INTO `goods_supplier` VALUES ('17', '1', '5', '35.0000', '1');
INSERT INTO `goods_supplier` VALUES ('18', '3', '5', '45.0000', '1');
INSERT INTO `goods_supplier` VALUES ('19', '2', '5', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('20', '2', '1', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('21', '1', '1', '0.0000', '0');
INSERT INTO `goods_supplier` VALUES ('22', '3', '1', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('23', '2', '2', '0.0000', '0');
INSERT INTO `goods_supplier` VALUES ('24', '1', '2', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('25', '3', '2', '80.0000', '1');
INSERT INTO `goods_supplier` VALUES ('26', '2', '3', '78.0000', '1');
INSERT INTO `goods_supplier` VALUES ('27', '1', '3', '50.0000', '1');
INSERT INTO `goods_supplier` VALUES ('28', '3', '3', '0.0000', '0');
INSERT INTO `goods_supplier` VALUES ('29', '2', '4', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('30', '1', '4', '79.0000', '1');
INSERT INTO `goods_supplier` VALUES ('31', '3', '4', '1.8000', '1');
INSERT INTO `goods_supplier` VALUES ('32', '4', '6', '66.0000', '1');
INSERT INTO `goods_supplier` VALUES ('33', '2', '6', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('34', '4', '7', '4.0000', '1');
INSERT INTO `goods_supplier` VALUES ('35', '2', '7', '0.0000', '1');
INSERT INTO `goods_supplier` VALUES ('36', '1', '7', '2.0000', '1');
INSERT INTO `goods_supplier` VALUES ('37', '5', '7', '0.0000', '1');

-- ----------------------------
-- Table structure for `module`
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '模块（菜单）名字',
  `url` varchar(255) DEFAULT NULL COMMENT '模块（菜单）地址',
  `level` int(11) NOT NULL COMMENT '模块（菜单）级别',
  `father_id` int(11) NOT NULL COMMENT '父模块id',
  `type` varchar(255) NOT NULL COMMENT '模块类型（url：链接；button：功能按钮）',
  `use_img` varchar(255) DEFAULT NULL COMMENT '菜单图片',
  `sort_num` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序编号（大号排前）',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31115 DEFAULT CHARSET=utf8 COMMENT='功能菜单表';

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('1', '系统管理', '', '1', '0', 'url', 'system', '0', '1');
INSERT INTO `module` VALUES ('2', '源数据管理', '', '1', '0', 'url', 'data', '0', '1');
INSERT INTO `module` VALUES ('3', '合同管理', '', '1', '0', 'url', 'store', '0', '1');
INSERT INTO `module` VALUES ('11', '系统设置', '', '2', '1', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('12', '人员管理', '', '2', '1', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('13', '待扩展功能', '', '2', '1', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('21', '运营资料设置', '', '2', '2', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('31', '合同管理', '', '2', '3', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('111', '合同规范', '', '3', '11', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('121', '权限管理', '', '3', '12', 'url', 'jurisdiction', '0', '1');
INSERT INTO `module` VALUES ('122', '待扩展功能', '', '3', '12', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('123', '待扩展功能', '', '3', '12', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('211', '基础数据管理', '', '3', '21', 'url', 'basicsetup', '0', '1');
INSERT INTO `module` VALUES ('311', '合同管理', '', '3', '31', 'url', 'memberfile', '0', '1');
INSERT INTO `module` VALUES ('1111', '标准合同付款规范', '/views/basic/contractpaystandard.jsp', '4', '111', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('1211', '角色管理', '/views/user/role.jsp', '4', '121', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('1212', '用户管理', '/views/user/user.jsp', '4', '121', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('2111', '材料管理', '/views/basic/goods.jsp', '4', '211', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('2112', '供应商管理', '/views/basic/supplier.jsp', '4', '211', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('2113', '项目指标管理', '/views/basic/goodsgroup.jsp', '4', '211', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('3111', '合同管理', '/views/contract/contract.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('3112', '合同分解', '/views/contract/contractresolve.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('3113', '技术拆解', '/views/contract/contractresolvedetail.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('3114', '采购管理', '/views/contract/contractreality.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('3115', '工厂收货', '/views/contract/goodsreceiving.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module` VALUES ('31111', '合同一级审批', 'contract/auditing', '5', '3111', 'button', null, '0', '1');
INSERT INTO `module` VALUES ('31112', '合同二级审批', 'contract/auditing2', '5', '3111', 'button', null, '0', '1');
INSERT INTO `module` VALUES ('31113', '查看合同文本', 'contract/main', '5', '3111', 'button', null, '0', '1');
INSERT INTO `module` VALUES ('31114', '查看合同附件', 'contract/attachment', '5', '3111', 'button', null, '0', '1');

-- ----------------------------
-- Table structure for `payment_agreed`
-- ----------------------------
DROP TABLE IF EXISTS `payment_agreed`;
CREATE TABLE `payment_agreed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `amount` decimal(19,4) NOT NULL COMMENT '支付金额',
  `payment_mode` varchar(255) NOT NULL COMMENT '支付方式',
  `payment_date` date NOT NULL COMMENT '支付日期',
  `create_user` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '最后修改者',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `status` int(10) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='合同约定付款表';

-- ----------------------------
-- Records of payment_agreed
-- ----------------------------
INSERT INTO `payment_agreed` VALUES ('1', '11', '51.0000', 'update11', '2017-11-20', 'admin', '2017-11-19 18:06:46', 'admin', '2017-11-19 18:06:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('2', '12', '52.0000', 'update12', '2017-11-21', 'admin', '2017-11-19 18:06:46', 'admin', '2017-11-19 18:06:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('3', '13', '53.0000', 'update13', '2017-11-22', 'admin', '2017-11-19 18:06:46', 'admin', '2017-11-19 18:06:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('4', '17', '100.0000', '现金', '2017-11-20', 'admin', '2017-11-19 18:12:46', 'admin', '2017-11-19 18:12:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('5', '17', '200.0000', '支票', '2017-11-21', 'admin', '2017-11-19 18:12:46', 'admin', '2017-11-19 18:12:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('6', '17', '300.0000', '转账', '2017-11-22', 'admin', '2017-11-19 18:12:46', 'admin', '2017-11-19 18:12:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('7', '18', '100.0000', '现金', '2017-11-20', 'admin', '2017-11-19 18:14:45', 'admin', '2017-11-19 18:14:45', null, '1');
INSERT INTO `payment_agreed` VALUES ('8', '18', '200.0000', '支票', '2017-11-21', 'admin', '2017-11-19 18:14:45', 'admin', '2017-11-19 18:14:45', null, '1');
INSERT INTO `payment_agreed` VALUES ('9', '18', '300.0000', '转账', '2017-11-22', 'admin', '2017-11-19 18:14:45', 'admin', '2017-11-19 18:14:45', null, '1');
INSERT INTO `payment_agreed` VALUES ('10', '19', '100.0000', '现金', '2017-11-20', 'admin', '2017-11-19 18:16:15', 'admin', '2017-11-19 18:16:15', null, '1');
INSERT INTO `payment_agreed` VALUES ('11', '19', '200.0000', '支票', '2017-11-21', 'admin', '2017-11-19 18:16:15', 'admin', '2017-11-19 18:16:15', null, '1');
INSERT INTO `payment_agreed` VALUES ('12', '19', '300.0000', '转账', '2017-11-22', 'admin', '2017-11-19 18:16:15', 'admin', '2017-11-19 18:16:15', null, '1');
INSERT INTO `payment_agreed` VALUES ('13', '20', '100.0000', '现金', '2017-11-20', 'admin', '2017-11-19 18:17:26', 'admin', '2017-11-19 18:17:26', null, '1');
INSERT INTO `payment_agreed` VALUES ('14', '20', '200.0000', '支票', '2017-11-21', 'admin', '2017-11-19 18:17:26', 'admin', '2017-11-19 18:17:26', null, '1');
INSERT INTO `payment_agreed` VALUES ('15', '20', '300.0000', '转账', '2017-11-22', 'admin', '2017-11-19 18:17:26', 'admin', '2017-11-19 18:17:26', null, '1');
INSERT INTO `payment_agreed` VALUES ('16', '21', '100.0000', '现金', '2017-11-20', 'admin', '2017-11-19 18:18:51', 'admin', '2017-11-19 18:18:51', null, '1');
INSERT INTO `payment_agreed` VALUES ('17', '21', '200.0000', '支票', '2017-11-21', 'admin', '2017-11-19 18:18:51', 'admin', '2017-11-19 18:18:51', null, '1');
INSERT INTO `payment_agreed` VALUES ('18', '21', '300.0000', '转账', '2017-11-22', 'admin', '2017-11-19 18:18:51', 'admin', '2017-11-19 18:18:51', null, '1');
INSERT INTO `payment_agreed` VALUES ('19', '22', '12.0000', '12', '2017-11-22', 'admin', '2017-11-22 14:30:00', 'admin', '2017-11-22 14:30:00', null, '1');
INSERT INTO `payment_agreed` VALUES ('20', '23', '123.0000', '123', '2017-11-20', 'admin', '2017-11-22 19:47:45', 'admin', '2017-11-23 11:24:07', null, '1');
INSERT INTO `payment_agreed` VALUES ('21', '23', '234.0000', '234', '2017-12-01', 'admin', '2017-11-22 19:47:45', 'admin', '2017-11-23 11:24:07', null, '0');
INSERT INTO `payment_agreed` VALUES ('22', '23', '345.0000', '345', '2017-12-13', 'admin', '2017-11-22 19:47:45', 'admin', '2017-11-23 11:24:07', null, '1');
INSERT INTO `payment_agreed` VALUES ('23', '23', '456.0000', '456', '2017-12-22', 'admin', '2017-11-22 19:49:47', 'admin', '2017-11-23 11:24:07', null, '1');
INSERT INTO `payment_agreed` VALUES ('24', '23', '666.0000', '666', '2017-11-01', 'admin', '2017-11-22 20:06:15', 'admin', '2017-11-23 11:24:07', null, '0');
INSERT INTO `payment_agreed` VALUES ('25', '23', '777.0000', '777', '2017-11-15', 'admin', '2017-11-23 11:24:07', 'admin', '2017-11-23 11:24:07', null, '1');
INSERT INTO `payment_agreed` VALUES ('26', '24', '1234.0000', '1324', '2017-11-23', 'admin', '2017-11-23 11:41:40', 'admin', '2017-12-08 15:49:16', null, '1');
INSERT INTO `payment_agreed` VALUES ('27', '25', '900000.0000', '现金', '2018-03-01', 'admin', '2018-03-19 14:00:55', 'admin', '2018-03-19 14:00:55', null, '1');
INSERT INTO `payment_agreed` VALUES ('28', '25', '700000.0000', '刷卡', '2018-03-03', 'admin', '2018-03-19 14:00:55', 'admin', '2018-03-19 14:00:55', null, '1');
INSERT INTO `payment_agreed` VALUES ('29', '25', '600000.0000', '支票', '2018-03-04', 'admin', '2018-03-19 14:00:55', 'admin', '2018-03-19 14:00:55', null, '1');
INSERT INTO `payment_agreed` VALUES ('30', '26', '1000.0000', '现金', '2018-03-01', 'system', '2018-03-19 17:50:08', 'system', '2018-03-19 17:50:08', null, '1');
INSERT INTO `payment_agreed` VALUES ('31', '26', '2000.0000', '支票', '2018-03-02', 'system', '2018-03-19 17:50:08', 'system', '2018-03-19 17:50:08', null, '1');
INSERT INTO `payment_agreed` VALUES ('32', '26', '1500.0000', '转账', '2018-03-03', 'system', '2018-03-19 17:50:08', 'system', '2018-03-19 17:50:08', null, '1');
INSERT INTO `payment_agreed` VALUES ('33', '27', '1000.0000', '现金', '2018-03-07', 'system', '2018-03-19 21:00:55', 'system', '2018-03-19 21:00:55', null, '1');
INSERT INTO `payment_agreed` VALUES ('34', '27', '15500.0000', '支票', '2018-03-08', 'system', '2018-03-19 21:00:55', 'system', '2018-03-19 21:00:55', null, '1');
INSERT INTO `payment_agreed` VALUES ('35', '27', '300.0000', '转账', '2018-03-10', 'system', '2018-03-19 21:00:55', 'system', '2018-03-19 21:00:55', null, '1');
INSERT INTO `payment_agreed` VALUES ('36', '28', '100.0000', '现金', '2018-03-01', 'system', '2018-03-29 00:27:46', 'system', '2018-03-29 00:27:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('37', '28', '200.0000', '支票', '2018-03-02', 'system', '2018-03-29 00:27:46', 'system', '2018-03-29 00:27:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('38', '28', '120.0000', '转账', '2018-03-05', 'system', '2018-03-29 00:27:46', 'system', '2018-03-29 00:27:46', null, '1');
INSERT INTO `payment_agreed` VALUES ('39', '29', '100.0000', '现金', '2018-03-01', 'system', '2018-03-29 00:35:58', 'system', '2018-03-29 00:35:58', null, '1');
INSERT INTO `payment_agreed` VALUES ('40', '29', '200.0000', '支票', '2018-03-02', 'system', '2018-03-29 00:35:58', 'system', '2018-03-29 00:35:58', null, '1');
INSERT INTO `payment_agreed` VALUES ('41', '29', '120.0000', '转账', '2018-03-05', 'system', '2018-03-29 00:35:58', 'system', '2018-03-29 00:35:58', null, '1');
INSERT INTO `payment_agreed` VALUES ('42', '30', '30.0000', '现金', '2018-03-01', 'system', '2018-03-29 00:45:42', 'system', '2018-05-10 00:55:50', null, '1');
INSERT INTO `payment_agreed` VALUES ('43', '30', '50.0000', '支票', '2018-03-02', 'system', '2018-03-29 00:45:42', 'system', '2018-05-10 00:55:50', null, '1');
INSERT INTO `payment_agreed` VALUES ('44', '30', '20.0000', '转账', '2018-03-05', 'system', '2018-03-29 00:45:42', 'system', '2018-05-10 00:55:50', null, '1');
INSERT INTO `payment_agreed` VALUES ('45', '31', '1000.0000', '现金', '2018-05-01', 'system', '2018-05-13 08:33:40', 'system', '2018-05-13 08:33:40', null, '1');

-- ----------------------------
-- Table structure for `payment_reality`
-- ----------------------------
DROP TABLE IF EXISTS `payment_reality`;
CREATE TABLE `payment_reality` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `amount` decimal(19,4) NOT NULL COMMENT '支付金额',
  `payment_mode` varchar(255) NOT NULL COMMENT '支付方式',
  `payment_date` date NOT NULL COMMENT '支付日期',
  `create_user` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '最后修改者',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='合同实际付款表';

-- ----------------------------
-- Records of payment_reality
-- ----------------------------
INSERT INTO `payment_reality` VALUES ('1', '20', '100.0000', '现金', '2018-02-25', 'admin', '2018-03-17 01:11:22', 'admin', '2018-03-17 01:11:22', null, '1');
INSERT INTO `payment_reality` VALUES ('2', '20', '200.0000', '支票', '2018-02-27', 'admin', '2018-03-17 01:15:02', 'admin', '2018-03-17 01:15:02', null, '1');
INSERT INTO `payment_reality` VALUES ('3', '20', '100.0000', '转账', '2018-03-14', 'admin', '2018-03-17 01:49:04', 'admin', '2018-03-17 01:49:04', null, '1');
INSERT INTO `payment_reality` VALUES ('4', '20', '10.0000', '转账', '2018-03-15', 'admin', '2018-03-17 01:51:14', 'admin', '2018-03-17 01:51:14', null, '1');
INSERT INTO `payment_reality` VALUES ('5', '20', '10.0000', '转账', '2018-03-16', 'admin', '2018-03-17 01:52:51', 'admin', '2018-03-17 01:52:51', null, '1');
INSERT INTO `payment_reality` VALUES ('6', '20', '10.0000', '转账', '2018-03-17', 'admin', '2018-03-17 01:53:43', 'admin', '2018-03-17 01:53:43', null, '1');
INSERT INTO `payment_reality` VALUES ('7', '25', '900000.0000', '现金', '2018-03-01', 'admin', '2018-03-19 14:01:30', 'admin', '2018-03-19 14:01:30', null, '1');
INSERT INTO `payment_reality` VALUES ('8', '27', '1000.0000', '现金', '2018-03-19', 'system', '2018-03-19 21:05:04', 'system', '2018-03-19 21:05:04', null, '1');
INSERT INTO `payment_reality` VALUES ('9', '30', '30.0000', '现金', '2018-05-12', 'system', '2018-05-12 01:40:13', 'system', '2018-05-12 01:40:13', null, '1');
INSERT INTO `payment_reality` VALUES ('10', '25', '100.0000', '现金', '2018-05-01', 'system', '2018-05-12 13:42:36', 'system', '2018-05-12 13:42:36', null, '1');
INSERT INTO `payment_reality` VALUES ('11', '22', '1.0000', '现金', '2018-05-01', 'system', '2018-05-12 13:50:30', 'system', '2018-05-12 13:50:30', null, '1');
INSERT INTO `payment_reality` VALUES ('12', '31', '1000.0000', '现金', '2018-05-02', 'system', '2018-05-13 08:34:58', 'system', '2018-05-13 08:34:58', null, '1');

-- ----------------------------
-- Table structure for `purchase_order`
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE `purchase_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同id',
  `supplier_id` bigint(20) NOT NULL COMMENT '供应商id',
  `order_no` varchar(255) NOT NULL COMMENT '采购订单编号',
  `create_user` varchar(255) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int(10) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='采购订单表';

-- ----------------------------
-- Records of purchase_order
-- ----------------------------
INSERT INTO `purchase_order` VALUES ('22', '30', '3', 'GD15261642876263', 'system', '2018-05-13 06:31:27', '1');
INSERT INTO `purchase_order` VALUES ('23', '30', '2', 'GD15261642876262', 'system', '2018-05-13 06:31:27', '1');
INSERT INTO `purchase_order` VALUES ('24', '30', '6', 'GD15261642876266', 'system', '2018-05-13 06:31:27', '1');

-- ----------------------------
-- Table structure for `purchase_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order_detail`;
CREATE TABLE `purchase_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_order_id` bigint(20) NOT NULL COMMENT '采购订单id',
  `goods_id` bigint(20) NOT NULL COMMENT '原材料id',
  `price` decimal(19,4) NOT NULL COMMENT '本次采购的物品单价',
  `goods_total_num` int(11) NOT NULL COMMENT '原材料数量',
  `status` int(10) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='采购订单明细表';

-- ----------------------------
-- Records of purchase_order_detail
-- ----------------------------
INSERT INTO `purchase_order_detail` VALUES ('36', '22', '1', '50.0000', '22', '1');
INSERT INTO `purchase_order_detail` VALUES ('37', '22', '2', '78.0000', '2', '1');
INSERT INTO `purchase_order_detail` VALUES ('38', '23', '3', '80.0000', '3', '1');
INSERT INTO `purchase_order_detail` VALUES ('39', '24', '4', '66.0000', '9', '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:有效；0:无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '经理', '经理测试', 'admin', '2018-03-16 10:33:35', 'admin', '2018-03-17 10:38:03', '1');
INSERT INTO `role` VALUES ('2', '拆分部员工', '打算发大水', 'admin', '2018-03-16 12:16:51', 'admin', '2018-03-17 10:37:19', '1');
INSERT INTO `role` VALUES ('3', '采购部员工', '拉开空间环境和', 'admin', '2018-03-17 10:38:55', 'admin', '2018-03-17 10:38:55', '1');
INSERT INTO `role` VALUES ('4', '工厂收货', '阿萨德刚发给', 'admin', '2018-03-17 10:40:10', 'admin', '2018-03-17 10:40:10', '1');

-- ----------------------------
-- Table structure for `supplier_info`
-- ----------------------------
DROP TABLE IF EXISTS `supplier_info`;
CREATE TABLE `supplier_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '供应商名称',
  `phone` varchar(255) NOT NULL COMMENT '供应商电话',
  `address` varchar(255) NOT NULL COMMENT '供应商地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `sort_num` bigint(20) NOT NULL COMMENT '序号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='供应商信息表';

-- ----------------------------
-- Records of supplier_info
-- ----------------------------
INSERT INTO `supplier_info` VALUES ('1', '供应商1', '1234', '北京', '2017-11-29 20:56:01', '备注', '2', '1');
INSERT INTO `supplier_info` VALUES ('2', '供应商2', '12423', '上海', '2017-11-29 20:58:38', '', '0', '1');
INSERT INTO `supplier_info` VALUES ('3', '供应商3', '12324', '天津', '2017-11-29 20:59:15', '', '0', '1');
INSERT INTO `supplier_info` VALUES ('4', '供应商4', '1232432', '保定', '2017-11-30 17:32:24', '当官的发光飞碟', '0', '1');
INSERT INTO `supplier_info` VALUES ('5', '供应商5', '123214', '啊速度发多少', '2017-11-30 20:59:30', '', '4', '1');
INSERT INTO `supplier_info` VALUES ('6', '供应商6', '1866666666', '河北石家庄', '2018-03-17 10:47:53', '便宜', '2', '1');
INSERT INTO `supplier_info` VALUES ('7', '供应商222', '1866666666', '2发顺丰的', '2018-03-19 20:53:56', '', '2', '1');

-- ----------------------------
-- Table structure for `upload_file`
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `file_url` varchar(255) NOT NULL COMMENT '文件存放路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(4) NOT NULL COMMENT '0：无效；1：有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload_file
-- ----------------------------
INSERT INTO `upload_file` VALUES ('1', '博士.png', '/upload/博士.png', '2017-11-17 23:51:16', '1');
INSERT INTO `upload_file` VALUES ('2', '博士1.png', '/upload/博士1.png', '2017-11-17 23:52:02', '1');
INSERT INTO `upload_file` VALUES ('3', '博士2.png', '/upload/博士2.png', '2017-11-17 23:52:02', '1');
INSERT INTO `upload_file` VALUES ('4', '0.jpg', '/upload/0.jpg', '2017-11-19 13:58:41', '1');
INSERT INTO `upload_file` VALUES ('5', '博士.png', '/upload/博士.png', '2017-11-19 13:58:41', '1');
INSERT INTO `upload_file` VALUES ('6', '博士1.png', '/upload/博士1.png', '2017-11-19 13:58:41', '1');
INSERT INTO `upload_file` VALUES ('7', '博士2.png', '/upload/博士2.png', '2017-11-19 13:58:41', '1');
INSERT INTO `upload_file` VALUES ('8', '0.jpg', '/upload/0.jpg', '2017-11-19 13:59:41', '1');
INSERT INTO `upload_file` VALUES ('9', '博士.png', '/upload/博士.png', '2017-11-19 13:59:41', '1');
INSERT INTO `upload_file` VALUES ('10', '博士1.png', '/upload/博士1.png', '2017-11-19 13:59:41', '1');
INSERT INTO `upload_file` VALUES ('11', '博士2.png', '/upload/博士2.png', '2017-11-19 13:59:41', '1');
INSERT INTO `upload_file` VALUES ('12', '博士.png', '/upload/博士.png', '2017-11-19 17:58:38', '1');
INSERT INTO `upload_file` VALUES ('13', 'QQ图片20170511171448.png', '/upload/QQ图片20170511171448.png', '2017-11-20 00:38:18', '1');
INSERT INTO `upload_file` VALUES ('14', '博士.png', '/upload/博士.png', '2017-11-20 00:38:18', '1');
INSERT INTO `upload_file` VALUES ('15', '博士1.png', '/upload/博士1.png', '2017-11-20 00:38:18', '1');
INSERT INTO `upload_file` VALUES ('16', '博士2.png', '/upload/博士2.png', '2017-11-20 00:38:18', '1');
INSERT INTO `upload_file` VALUES ('17', '0.jpg', '/upload/0.jpg', '2017-11-22 14:29:53', '1');
INSERT INTO `upload_file` VALUES ('18', 'QQ图片20170511171448.png', '/upload/QQ图片20170511171448.png', '2017-11-22 14:29:53', '1');
INSERT INTO `upload_file` VALUES ('19', '博士.png', '/upload/博士.png', '2017-11-22 14:29:53', '1');
INSERT INTO `upload_file` VALUES ('20', '博士2.png', '/upload/博士2.png', '2017-11-22 14:29:53', '1');
INSERT INTO `upload_file` VALUES ('21', '博士.png', '/upload/博士.png', '2017-11-22 18:47:47', '1');
INSERT INTO `upload_file` VALUES ('22', 'untitled.png', '/upload/untitled.png', '2017-11-22 19:05:50', '1');
INSERT INTO `upload_file` VALUES ('23', '博士.png', '/upload/博士.png', '2017-11-22 19:47:34', '1');
INSERT INTO `upload_file` VALUES ('24', '博士1.png', '/upload/博士1.png', '2017-11-22 19:47:40', '1');
INSERT INTO `upload_file` VALUES ('25', '博士2.png', '/upload/博士2.png', '2017-11-22 19:47:40', '1');
INSERT INTO `upload_file` VALUES ('26', '0.jpg', '/upload/0.jpg', '2017-11-22 19:49:31', '1');
INSERT INTO `upload_file` VALUES ('27', 'QQ图片20170511171448.png', '/upload/QQ图片20170511171448.png', '2017-11-23 11:24:03', '1');
INSERT INTO `upload_file` VALUES ('28', '博士.png', '/upload/博士.png', '2017-11-23 11:41:35', '1');
INSERT INTO `upload_file` VALUES ('29', '博士.png', '/upload/博士.png', '2017-11-23 11:47:29', '1');
INSERT INTO `upload_file` VALUES ('30', '博士.png', '/upload/博士.png', '2017-11-23 11:52:10', '1');
INSERT INTO `upload_file` VALUES ('31', '博士.png', '/upload/博士.png', '2017-11-23 12:03:39', '1');
INSERT INTO `upload_file` VALUES ('32', '博士.png', '/upload/博士.png', '2017-11-23 12:03:48', '1');
INSERT INTO `upload_file` VALUES ('33', '博士.png', '/upload/博士.png', '2017-11-23 12:03:56', '1');
INSERT INTO `upload_file` VALUES ('34', '博士2.png', '/upload/博士2.png', '2017-12-03 14:26:13', '1');
INSERT INTO `upload_file` VALUES ('35', '博士.png', '/upload/b7c658b32b573fc16598509c524c8d03', '2017-12-08 14:43:19', '1');
INSERT INTO `upload_file` VALUES ('36', '0.jpg', '/upload/7a6b4ab4bc388ca3143f3abb085472c2', '2017-12-08 15:49:02', '1');
INSERT INTO `upload_file` VALUES ('37', 'untitled.png', '/upload/991507d4941d412ec42f83b4486a33ab', '2017-12-08 15:49:07', '1');
INSERT INTO `upload_file` VALUES ('38', '建设行业管理信息系统操作手册（SQLite版）V5.0_2013.doc', '/upload/c4a1c69d77e70ea42e6dd99085a32f7c', '2017-12-13 19:47:55', '1');
INSERT INTO `upload_file` VALUES ('39', '0.jpg', '/upload/71a197fb6b35a73feb607d82b43babb1', '2018-03-17 10:54:28', '1');
INSERT INTO `upload_file` VALUES ('40', '博士.png', '/upload/864322b80220ef0ae6b0681a8fcb9e10', '2018-03-17 10:54:36', '1');
INSERT INTO `upload_file` VALUES ('41', '0.jpg', '/upload/135a262de29bf4aa5f9bd93927551562', '2018-03-19 14:00:50', '1');
INSERT INTO `upload_file` VALUES ('42', '博士.png', '/upload/ea59f4cc0388c60fac31be52e3f590aa', '2018-03-19 14:00:50', '1');
INSERT INTO `upload_file` VALUES ('43', 'QQ图片20170511171448.png', '/upload/2adb0e7e73dedbab20cedf9401c12b7f', '2018-03-19 17:49:57', '1');
INSERT INTO `upload_file` VALUES ('44', '博士.png', '/upload/c6fbe0e3515ab4284fd94dd11efaeaec', '2018-03-19 17:50:04', '1');
INSERT INTO `upload_file` VALUES ('45', 'QQ图片20170511171448.png', '/upload/c2d7e0f6a1b78bc4d7609d9c76011e30', '2018-03-19 21:00:37', '1');
INSERT INTO `upload_file` VALUES ('46', '博士.png', '/upload/6ba616f8c4ac48e1df6e1bbc826d75fc', '2018-03-19 21:00:47', '1');
INSERT INTO `upload_file` VALUES ('47', '博士1.png', '/upload/33bd375ccaf41d5a1b5e1165ce57ba90', '2018-03-19 21:00:47', '1');
INSERT INTO `upload_file` VALUES ('48', '1.png.jpg', '/upload/aae5b7b059fd10aaaafac64d811e83ea', '2018-03-28 23:42:24', '1');
INSERT INTO `upload_file` VALUES ('49', '博士.png', '/upload/c175b60697b5ceaf1562d4c25f3830ef', '2018-03-29 00:13:00', '1');
INSERT INTO `upload_file` VALUES ('50', '博士1.png', '/upload/b5db8e056498896602841e1b762e2478', '2018-03-29 00:13:00', '1');
INSERT INTO `upload_file` VALUES ('51', '0.jpg', '/upload/8e3623eb05c7645ce9b8e1833dfdc56d', '2018-03-29 00:13:06', '1');
INSERT INTO `upload_file` VALUES ('52', 'QQ图片20170511171448.png', '/upload/76c69698788eca386446bcaa9d41d637', '2018-03-29 00:13:06', '1');
INSERT INTO `upload_file` VALUES ('53', '博士.png', '/upload/ec261051df5e2a6cc5dfa45edde1748d', '2018-03-29 00:13:50', '1');
INSERT INTO `upload_file` VALUES ('54', '0.jpg', '/upload/4f6a9fa199047c0d9e66c118f7846c79', '2018-03-29 00:27:29', '1');
INSERT INTO `upload_file` VALUES ('55', 'QQ图片20170511171448.png', '/upload/21c5c5adece121e4d188d02d4748bab1', '2018-03-29 00:27:29', '1');
INSERT INTO `upload_file` VALUES ('56', '博士1.png', '/upload/52b253d0ab9e1f9b7c8980efb4b86af3', '2018-03-29 00:27:41', '1');
INSERT INTO `upload_file` VALUES ('57', '博士2.png', '/upload/22c5b10de80791e9aea21fd65c1b37a9', '2018-03-29 00:27:41', '1');
INSERT INTO `upload_file` VALUES ('58', '俩树叶.png', '/upload/6d9c9daf98c2b21fb705ee3f76da6f53', '2018-03-29 00:51:07', '1');
INSERT INTO `upload_file` VALUES ('59', '博士.png', '/upload/acb34052adfe3c0d9487bcf9c13a5a66', '2018-03-29 00:51:17', '1');
INSERT INTO `upload_file` VALUES ('60', '博士.png', '/upload/3764b90d0f81e5fab29d9878fb58fba0', '2018-05-13 08:33:26', '1');
INSERT INTO `upload_file` VALUES ('61', '博士.png', '/upload/300e6a87c8c2935302938d8d75092c62', '2018-05-13 08:33:31', '1');
INSERT INTO `upload_file` VALUES ('62', '俩树叶.png', '/upload/8dee7c93713598059b39fdba3fe96b38', '2018-05-13 08:33:36', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '登录用户名',
  `password` varchar(255) NOT NULL COMMENT '登录密码',
  `name` varchar(255) NOT NULL COMMENT '员工姓名',
  `sex` tinyint(4) NOT NULL COMMENT '员工性别（0：女；1：男）',
  `phone` varchar(255) DEFAULT '' COMMENT '手机号',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `remark` varchar(255) DEFAULT '' COMMENT '备注信息',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：无效；1：有效',
  `is_notice` tinyint(4) DEFAULT '0' COMMENT '0：无；1：有（用于通知，标识是否有业务需要处理）',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='登录用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ceshi', '123456', '总经理', '0', '18888888888', '0', '1', '', '1', '0', null, null, 'admin', '2018-03-17 10:43:01');
INSERT INTO `user` VALUES ('2', 'ceshi2', '123456', '经理Two', '1', '15666666666', '0', '1', '', '1', '0', null, null, 'admin', '2018-03-17 10:42:38');
INSERT INTO `user` VALUES ('3', 'me', '123456', '我', '1', '11111111111', null, '2', '按时大大是个', '1', '0', 'admin', '2018-03-16 13:00:15', 'admin', '2018-03-16 13:00:29');
INSERT INTO `user` VALUES ('4', 'mee', '123456', '我我', '1', '111111', null, '2', '快乐健康了', '1', '0', 'admin', '2018-03-16 13:03:59', 'admin', '2018-03-16 13:03:59');
INSERT INTO `user` VALUES ('5', 'you', '123456', '采购人员', '0', '13888888888', null, '3', '', '1', '0', 'admin', '2018-03-17 10:41:39', 'admin', '2018-03-17 10:41:39');
INSERT INTO `user` VALUES ('6', 'he', '123456', '工厂他他', '0', '13333333333', null, '4', '', '1', '0', 'admin', '2018-03-17 10:43:55', 'admin', '2018-03-17 10:43:55');
INSERT INTO `user` VALUES ('7', 'ceshi1', '1234567', 'ceshi1', '0', '111111111', null, '1', '1212', '1', '0', 'system', '2018-05-10 23:24:27', 'system', '2018-05-10 23:24:53');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
