/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : crm

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 02/06/2022 08:25:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_activity
-- ----------------------------
DROP TABLE IF EXISTS `tbl_activity`;
CREATE TABLE `tbl_activity`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `owner` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_date` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_date` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_activity
-- ----------------------------
INSERT INTO `tbl_activity` VALUES ('1d0d1f3c16374686949e9ebc05a62fqa', '40f6cdea0bd34aceb77492a1656d9fb3', '测试用例08', '2022-05-13', '2022-05-22', '5000', '测试备注功能111', '2022-05-13 16:16:54', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-13', '40f6cdea0bd34aceb77492a1656d9fb3');
INSERT INTO `tbl_activity` VALUES ('6f99630136104632a74d0ce81a03ec6f', '06f5fc056eac41558a964f96daa7f27c', '测试用例06', '2022-05-12', '2022-05-24', '4500', '修改成功测试用例', '2022-05-12 20:23:41', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-12', '40f6cdea0bd34aceb77492a1656d9fb3');
INSERT INTO `tbl_activity` VALUES ('823bd043989e482cbb202a75c004cc8c', '06f5fc056eac41558a964f96daa7f27c', '测试02', '2021-07-18', '2022-02-08', '5600', '测试中....', '2022-05-11 20:16:14', '40f6cdea0bd34aceb77492a1656d9fb3', NULL, NULL);
INSERT INTO `tbl_activity` VALUES ('9536384030c54ac390ebe0892f02849c', '40f6cdea0bd34aceb77492a1656d9fb3', '测试用例09', '2022-05-13', '2022-05-22', '4200', '测试导入功能', '2022-05-13 16:16:54', '40f6cdea0bd34aceb77492a1656d9fb3', NULL, NULL);
INSERT INTO `tbl_activity` VALUES ('fe85e6cd79e64305998e25074cba0e2a', '40f6cdea0bd34aceb77492a1656d9fb3', '测试用例07', '2022-05-12', '2022-05-18', '4000', '33333', '2022-05-12 20:24:23', '40f6cdea0bd34aceb77492a1656d9fb3', NULL, NULL);

-- ----------------------------
-- Table structure for tbl_activity_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_activity_remark`;
CREATE TABLE `tbl_activity_remark`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `note_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_activity_remark
-- ----------------------------
INSERT INTO `tbl_activity_remark` VALUES ('1111749f5a7a453cb5d42e7e161cfd6c', '测试鼠标悬停事件1111', '2022-05-14 10:30:04', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-14 16:37:43', '40f6cdea0bd34aceb77492a1656d9fb3', '1', '1d0d1f3c16374686949e9ebc05a62fqa');
INSERT INTO `tbl_activity_remark` VALUES ('536aac0b02814d3b9205c445c76f0d07', '测试模态窗口修改功能', '2022-05-14 11:27:51', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-14 16:33:18', '40f6cdea0bd34aceb77492a1656d9fb3', '1', '1d0d1f3c16374686949e9ebc05a62fqa');
INSERT INTO `tbl_activity_remark` VALUES ('85f03961e4004b7bb43c85278c458ece', '测试备注功能3333', '2022-05-14 09:48:35', '40f6cdea0bd34aceb77492a1656d9fb3', NULL, NULL, '0', '9536384030c54ac390ebe0892f02849c');
INSERT INTO `tbl_activity_remark` VALUES ('96601b7911c242ab887a756f41b69784', '222', '2022-05-22 15:14:59', '40f6cdea0bd34aceb77492a1656d9fb3', NULL, NULL, '0', '1d0d1f3c16374686949e9ebc05a62fqa');
INSERT INTO `tbl_activity_remark` VALUES ('e4f602be98564e1a883a292521e0653c', '测试备注功能2222', '2022-05-14 09:47:00', '40f6cdea0bd34aceb77492a1656d9fb3', NULL, NULL, '0', '9536384030c54ac390ebe0892f02849c');

-- ----------------------------
-- Table structure for tbl_clue
-- ----------------------------
DROP TABLE IF EXISTS `tbl_clue`;
CREATE TABLE `tbl_clue`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fullname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `appellation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `owner` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `website` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact_summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `next_contact_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_clue
-- ----------------------------
INSERT INTO `tbl_clue` VALUES ('a660ceb9e7d24ce6a2265160fb033aa0', '林黛玉', '67165c27076e4c8599f42de57850e39c', '06f5fc056eac41558a964f96daa7f27c', '微软', 'cto', '23746328@ww.com', '0511-4405222', 'http://www.google.com', '18043334677', '310e6a49bd8a4962b3f95a1d92eb76f4', '86c56aca9eef49058145ec20d5466c17', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-14 21:35:38', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-15 17:41:57', '测试创建功能', '测试创建功能', '2022-01-01', '中国');
INSERT INTO `tbl_clue` VALUES ('bf017a6ecee3423a8ab6062b551bcab4', '23123', '67165c27076e4c8599f42de57850e39c', '06f5fc056eac41558a964f96daa7f27c', '213123', 'cer', '234312312@ww.com', '0511-4405222', 'www.baidu.com', '13744455234', '06e3cbdf10a44eca8511dddfc6896c55', 'fd677cc3b5d047d994e16f6ece4d3d45', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 12:59:12', NULL, NULL, '1111', '1111', '2022-05-05', '123123');

-- ----------------------------
-- Table structure for tbl_clue_activity_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_clue_activity_relation`;
CREATE TABLE `tbl_clue_activity_relation`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `clue_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_clue_activity_relation
-- ----------------------------
INSERT INTO `tbl_clue_activity_relation` VALUES ('50126d6e35354974bb1524b0a55a26e6', 'a660ceb9e7d24ce6a2265160fb033aa0', '9536384030c54ac390ebe0892f02849c');
INSERT INTO `tbl_clue_activity_relation` VALUES ('8a92557225cb480bb463edd6dfc471e8', 'a660ceb9e7d24ce6a2265160fb033aa0', 'fe85e6cd79e64305998e25074cba0e2a');
INSERT INTO `tbl_clue_activity_relation` VALUES ('beb337ddca544f1dbcacf631609974f6', 'a660ceb9e7d24ce6a2265160fb033aa0', '823bd043989e482cbb202a75c004cc8c');

-- ----------------------------
-- Table structure for tbl_clue_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_clue_remark`;
CREATE TABLE `tbl_clue_remark`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `note_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clue_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_clue_remark
-- ----------------------------
INSERT INTO `tbl_clue_remark` VALUES ('822409eff3534a86b7b74d404d24cd5d', '2222', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 13:38:59', NULL, NULL, '0', 'bf017a6ecee3423a8ab6062b551bcab4');
INSERT INTO `tbl_clue_remark` VALUES ('bf402749fd5b425fa15cc07a9be4c90c', '111', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 13:35:35', NULL, NULL, '0', 'bf017a6ecee3423a8ab6062b551bcab4');

-- ----------------------------
-- Table structure for tbl_contacts
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contacts`;
CREATE TABLE `tbl_contacts`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `owner` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `customer_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fullname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `appellation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact_summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `next_contact_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_contacts
-- ----------------------------
INSERT INTO `tbl_contacts` VALUES ('54e9686cde8448129ab680b62c7daa42', '40f6cdea0bd34aceb77492a1656d9fb3', '4d03a42898684135809d380597ed3268', 'd283cb74c2934ae6bf1662851a666967', '李彦宏', '59795c49896947e1ab61b7312bd0597c', '32423432@ee.cn', '18043552344', 'ceo', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 12:19:40', NULL, NULL, '测试修改功能111111', '测试修改功能22222', '2022-05-05', '测试修改功能');
INSERT INTO `tbl_contacts` VALUES ('9ce3bf0c6fb84acfad6cddbfdcca255c', '40f6cdea0bd34aceb77492a1656d9fb3', '4d03a42898684135809d380597ed3268', '5543ffa5f48a45cd9beca73c4d669bb3', '贾宝玉', '31539e7ed8c848fc913e1c2c93d76fd1', '23423423@ww.com', '18045663444', 'CEO', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:44:36', NULL, NULL, '测试创建功能', '测试创建功能', '2022-05-05', '测试创建功能');

-- ----------------------------
-- Table structure for tbl_contacts_activity_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contacts_activity_relation`;
CREATE TABLE `tbl_contacts_activity_relation`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contacts_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_contacts_activity_relation
-- ----------------------------
INSERT INTO `tbl_contacts_activity_relation` VALUES ('124d510dde3348238f856dfc76396398', '9ce3bf0c6fb84acfad6cddbfdcca255c', '6f99630136104632a74d0ce81a03ec6f');
INSERT INTO `tbl_contacts_activity_relation` VALUES ('1e6bd73464864c98b8ed660a2eba926c', '54e9686cde8448129ab680b62c7daa42', '823bd043989e482cbb202a75c004cc8c');
INSERT INTO `tbl_contacts_activity_relation` VALUES ('8ac2d1f4e7be4013a6daf4e64aba1362', '9ce3bf0c6fb84acfad6cddbfdcca255c', '1d0d1f3c16374686949e9ebc05a62fqa');
INSERT INTO `tbl_contacts_activity_relation` VALUES ('bfabd38d1cb347248585fe32388d0825', '54e9686cde8448129ab680b62c7daa42', '9536384030c54ac390ebe0892f02849c');

-- ----------------------------
-- Table structure for tbl_contacts_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contacts_remark`;
CREATE TABLE `tbl_contacts_remark`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `note_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contacts_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_contacts_remark
-- ----------------------------
INSERT INTO `tbl_contacts_remark` VALUES ('42cb4cfa7c364e408ea12bfa9cf99f5c', '测试保存备注功能', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:33:44', NULL, NULL, '0', '9ce3bf0c6fb84acfad6cddbfdcca255c');
INSERT INTO `tbl_contacts_remark` VALUES ('5d593451eeee4c87b20cdc5882de3594', '测试保存备注功能111', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:36:03', NULL, NULL, '0', '9ce3bf0c6fb84acfad6cddbfdcca255c');
INSERT INTO `tbl_contacts_remark` VALUES ('9f130693af9149e6894fd00fe8ba2cb6', '测试保存备注功能2222', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:38:32', NULL, NULL, '0', '9ce3bf0c6fb84acfad6cddbfdcca255c');
INSERT INTO `tbl_contacts_remark` VALUES ('ada3d99dc96747ac86d72b0d38277e9e', '测试备注功能', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:18:56', NULL, NULL, '0', '9ce3bf0c6fb84acfad6cddbfdcca255c');

-- ----------------------------
-- Table structure for tbl_customer
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customer`;
CREATE TABLE `tbl_customer`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `owner` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `website` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact_summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `next_contact_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_customer
-- ----------------------------
INSERT INTO `tbl_customer` VALUES ('0f5c97b5eecb4f498e45b20b7eabf546', '40f6cdea0bd34aceb77492a1656d9fb3', '京东有限公司', NULL, NULL, '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 12:22:08', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_customer` VALUES ('2b07cb406b834b36b9510d360d430350', '40f6cdea0bd34aceb77492a1656d9fb3', 'Baidu', 'www.baidu.com', '0511-4405222', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-15 22:18:52', NULL, NULL, '测试修改功能22222', '2022-05-05', '测试修改功能111111', '测试修改功能');
INSERT INTO `tbl_customer` VALUES ('5543ffa5f48a45cd9beca73c4d669bb3', '40f6cdea0bd34aceb77492a1656d9fb3', 'Google', 'www.google.com', '0511-4405222', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:44:36', NULL, NULL, '测试创建功能', '2022-05-05', '测试创建功能', '测试创建功能');
INSERT INTO `tbl_customer` VALUES ('d283cb74c2934ae6bf1662851a666967', '40f6cdea0bd34aceb77492a1656d9fb3', 'Tencent', 'www.baidu.com', '0511-4405222', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 12:19:40', NULL, NULL, '测试修改功能22222', '2022-05-05', '测试修改功能111111', '测试修改功能');

-- ----------------------------
-- Table structure for tbl_customer_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customer_remark`;
CREATE TABLE `tbl_customer_remark`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `note_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `customer_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_customer_remark
-- ----------------------------
INSERT INTO `tbl_customer_remark` VALUES ('3f7b89037e0e42c5b07c8cfe7745a2d5', '测试保存备注功能', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:33:44', NULL, NULL, '0', '5543ffa5f48a45cd9beca73c4d669bb3');
INSERT INTO `tbl_customer_remark` VALUES ('4d25771ad77644cbbcc0dbba5c68cd1d', '测试保存备注功能2222', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:38:32', NULL, NULL, '0', '5543ffa5f48a45cd9beca73c4d669bb3');
INSERT INTO `tbl_customer_remark` VALUES ('6cda7a2ec502403589a1a146721603c3', '测试保存备注功能111', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:36:03', NULL, NULL, '0', '5543ffa5f48a45cd9beca73c4d669bb3');
INSERT INTO `tbl_customer_remark` VALUES ('a336420359654eb796c02895eb9af1a7', '测试备注功能', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 16:18:56', NULL, NULL, '0', '5543ffa5f48a45cd9beca73c4d669bb3');

-- ----------------------------
-- Table structure for tbl_dic_type
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dic_type`;
CREATE TABLE `tbl_dic_type`  (
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????????Ϊ?գ????ܺ??????ġ?',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_dic_type
-- ----------------------------
INSERT INTO `tbl_dic_type` VALUES ('appellation', '称呼', '');
INSERT INTO `tbl_dic_type` VALUES ('clueState', '线索状态', '');
INSERT INTO `tbl_dic_type` VALUES ('returnPriority', '回访优先级', '');
INSERT INTO `tbl_dic_type` VALUES ('returnState', '回访状态', '');
INSERT INTO `tbl_dic_type` VALUES ('source', '来源', '');
INSERT INTO `tbl_dic_type` VALUES ('stage', '阶段', '');
INSERT INTO `tbl_dic_type` VALUES ('transactionType', '交易类型', '');

-- ----------------------------
-- Table structure for tbl_dic_value
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dic_value`;
CREATE TABLE `tbl_dic_value`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????UUID',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????Ϊ?գ?????Ҫ??ͬһ???ֵ????????ֵ?ֵ?????ظ???????Ψһ?ԡ?',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????Ϊ?',
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????Ϊ?գ?????Ϊ?յ?ʱ????Ҫ???????????',
  `type_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_dic_value
-- ----------------------------
INSERT INTO `tbl_dic_value` VALUES ('06e3cbdf10a44eca8511dddfc6896c55', '虚假线索', '虚假线索', '4', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('0fe33840c6d84bf78df55d49b169a894', '销售邮件', '销售邮件', '8', 'source');
INSERT INTO `tbl_dic_value` VALUES ('12302fd42bd349c1bb768b19600e6b20', '交易会', '交易会', '11', 'source');
INSERT INTO `tbl_dic_value` VALUES ('1615f0bb3e604552a86cde9a2ad45bea', '最高', '最高', '2', 'returnPriority');
INSERT INTO `tbl_dic_value` VALUES ('176039d2a90e4b1a81c5ab8707268636', '教授', '教授', '5', 'appellation');
INSERT INTO `tbl_dic_value` VALUES ('1e0bd307e6ee425599327447f8387285', '将来联系', '将来联系', '2', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('2173663b40b949ce928db92607b5fe57', '丢失线索', '丢失线索', '5', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('2876690b7e744333b7f1867102f91153', '未启动', '未启动', '1', 'returnState');
INSERT INTO `tbl_dic_value` VALUES ('29805c804dd94974b568cfc9017b2e4c', '成交', '成交', '7', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('310e6a49bd8a4962b3f95a1d92eb76f4', '试图联系', '试图联系', '1', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('31539e7ed8c848fc913e1c2c93d76fd1', '博士', '博士', '4', 'appellation');
INSERT INTO `tbl_dic_value` VALUES ('37ef211719134b009e10b7108194cf46', '资质审查', '资质审查', '1', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('391807b5324d4f16bd58c882750ee632', '丢失的线索', '丢失的线索', '8', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('3a39605d67da48f2a3ef52e19d243953', '聊天', '聊天', '14', 'source');
INSERT INTO `tbl_dic_value` VALUES ('474ab93e2e114816abf3ffc596b19131', '低', '低', '3', 'returnPriority');
INSERT INTO `tbl_dic_value` VALUES ('48512bfed26145d4a38d3616e2d2cf79', '广告', '广告', '1', 'source');
INSERT INTO `tbl_dic_value` VALUES ('4d03a42898684135809d380597ed3268', '合作伙伴研讨会', '合作伙伴研讨会', '9', 'source');
INSERT INTO `tbl_dic_value` VALUES ('59795c49896947e1ab61b7312bd0597c', '先生', '先生', '1', 'appellation');
INSERT INTO `tbl_dic_value` VALUES ('5c6e9e10ca414bd499c07b886f86202a', '高', '高', '1', 'returnPriority');
INSERT INTO `tbl_dic_value` VALUES ('67165c27076e4c8599f42de57850e39c', '夫人', '夫人', '2', 'appellation');
INSERT INTO `tbl_dic_value` VALUES ('68a1b1e814d5497a999b8f1298ace62b', '因竞争丢失关闭', '因竞争丢失关闭', '9', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('6b86f215e69f4dbd8a2daa22efccf0cf', 'web调研', 'web调研', '13', 'source');
INSERT INTO `tbl_dic_value` VALUES ('72f13af8f5d34134b5b3f42c5d477510', '合作伙伴', '合作伙伴', '6', 'source');
INSERT INTO `tbl_dic_value` VALUES ('7c07db3146794c60bf975749952176df', '未联系', '未联系', '6', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('86c56aca9eef49058145ec20d5466c17', '内部研讨会', '内部研讨会', '10', 'source');
INSERT INTO `tbl_dic_value` VALUES ('9095bda1f9c34f098d5b92fb870eba17', '进行中', '进行中', '3', 'returnState');
INSERT INTO `tbl_dic_value` VALUES ('954b410341e7433faa468d3c4f7cf0d2', '已有业务', '已有业务', '1', 'transactionType');
INSERT INTO `tbl_dic_value` VALUES ('966170ead6fa481284b7d21f90364984', '已联系', '已联系', '3', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('96b03f65dec748caa3f0b6284b19ef2f', '推迟', '推迟', '2', 'returnState');
INSERT INTO `tbl_dic_value` VALUES ('97d1128f70294f0aac49e996ced28c8a', '新业务', '新业务', '2', 'transactionType');
INSERT INTO `tbl_dic_value` VALUES ('9ca96290352c40688de6596596565c12', '完成', '完成', '4', 'returnState');
INSERT INTO `tbl_dic_value` VALUES ('9e6d6e15232549af853e22e703f3e015', '需要条件', '需要条件', '7', 'clueState');
INSERT INTO `tbl_dic_value` VALUES ('9ff57750fac04f15b10ce1bbb5bb8bab', '需求分析', '需求分析', '2', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('a70dc4b4523040c696f4421462be8b2f', '等待某人', '等待某人', '5', 'returnState');
INSERT INTO `tbl_dic_value` VALUES ('a83e75ced129421dbf11fab1f05cf8b4', '推销电话', '推销电话', '2', 'source');
INSERT INTO `tbl_dic_value` VALUES ('ab8472aab5de4ae9b388b2f1409441c1', '常规', '常规', '5', 'returnPriority');
INSERT INTO `tbl_dic_value` VALUES ('ab8c2a3dc05f4e3dbc7a0405f721b040', '提案/报价', '提案/报价', '5', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('b924d911426f4bc5ae3876038bc7e0ad', 'web下载', 'web下载', '12', 'source');
INSERT INTO `tbl_dic_value` VALUES ('c13ad8f9e2f74d5aa84697bb243be3bb', '价值建议', '价值建议', '3', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('c83c0be184bc40708fd7b361b6f36345', '最低', '最低', '4', 'returnPriority');
INSERT INTO `tbl_dic_value` VALUES ('db867ea866bc44678ac20c8a4a8bfefb', '员工介绍', '员工介绍', '3', 'source');
INSERT INTO `tbl_dic_value` VALUES ('e44be1d99158476e8e44778ed36f4355', '确定决策者', '确定决策者', '4', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('e5f383d2622b4fc0959f4fe131dafc80', '女士', '女士', '3', 'appellation');
INSERT INTO `tbl_dic_value` VALUES ('e81577d9458f4e4192a44650a3a3692b', '谈判/复审', '谈判/复审', '6', 'stage');
INSERT INTO `tbl_dic_value` VALUES ('fb65d7fdb9c6483db02713e6bc05dd19', '在线商场', '在线商场', '5', 'source');
INSERT INTO `tbl_dic_value` VALUES ('fd677cc3b5d047d994e16f6ece4d3d45', '公开媒介', '公开媒介', '7', 'source');
INSERT INTO `tbl_dic_value` VALUES ('ff802a03ccea4ded8731427055681d48', '外部介绍', '外部介绍', '4', 'source');

-- ----------------------------
-- Table structure for tbl_tran
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tran`;
CREATE TABLE `tbl_tran`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `owner` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expected_date` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `customer_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contacts_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact_summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `next_contact_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_tran
-- ----------------------------
INSERT INTO `tbl_tran` VALUES ('69d1908bde544ed5be9c59bc63a9531a', '06f5fc056eac41558a964f96daa7f27c', '4100', '谷歌-测试创建交易', '2022-05-28', '5543ffa5f48a45cd9beca73c4d669bb3', 'ab8c2a3dc05f4e3dbc7a0405f721b040', '97d1128f70294f0aac49e996ced28c8a', '12302fd42bd349c1bb768b19600e6b20', '1d0d1f3c16374686949e9ebc05a62fqa', '9ce3bf0c6fb84acfad6cddbfdcca255c', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 11:50:22', NULL, NULL, '测试创建交易', '测试创建交易', '2022-05-20');
INSERT INTO `tbl_tran` VALUES ('daf8fb5a3bcc44368df8c780e85281f2', '06f5fc056eac41558a964f96daa7f27c', '3000', '京东-测试交易历史', '2022-05-27', '0f5c97b5eecb4f498e45b20b7eabf546', 'ab8c2a3dc05f4e3dbc7a0405f721b040', '954b410341e7433faa468d3c4f7cf0d2', '6b86f215e69f4dbd8a2daa22efccf0cf', '9536384030c54ac390ebe0892f02849c', '54e9686cde8448129ab680b62c7daa42', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 12:22:08', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 18:47:21', '测试交易历史以及创建新客户功能', '测试交易历史以及创建新客户功能', '2022-05-21');
INSERT INTO `tbl_tran` VALUES ('eb798626054543c6bb62a5debf8bc809', '06f5fc056eac41558a964f96daa7f27c', '1111', '百度-111', '2022-05-20', 'd283cb74c2934ae6bf1662851a666967', '29805c804dd94974b568cfc9017b2e4c', '954b410341e7433faa468d3c4f7cf0d2', '86c56aca9eef49058145ec20d5466c17', '1d0d1f3c16374686949e9ebc05a62fqa', '54e9686cde8448129ab680b62c7daa42', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-16 12:19:40', '40f6cdea0bd34aceb77492a1656d9fb3', '2022-05-17 15:25:43', '测试修改功能', '测试修改功能', '2022-05-21');

-- ----------------------------
-- Table structure for tbl_tran_history
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tran_history`;
CREATE TABLE `tbl_tran_history`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expected_date` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tran_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_tran_history
-- ----------------------------
INSERT INTO `tbl_tran_history` VALUES ('1f3bc25fb6d14d8195d0152a07388e01', 'e81577d9458f4e4192a44650a3a3692b', '3000', '2022-05-27', '2022-05-17 15:59:23', '40f6cdea0bd34aceb77492a1656d9fb3', 'daf8fb5a3bcc44368df8c780e85281f2');
INSERT INTO `tbl_tran_history` VALUES ('3e6887a32b9a4393ac5421ee2d20e35e', 'e44be1d99158476e8e44778ed36f4355', '3000', '2022-05-27', '2022-05-17 12:22:08', '40f6cdea0bd34aceb77492a1656d9fb3', 'daf8fb5a3bcc44368df8c780e85281f2');
INSERT INTO `tbl_tran_history` VALUES ('4c9ae50c0d3c49128a6809db477456a9', '29805c804dd94974b568cfc9017b2e4c', '1111', '2022-05-20', '2022-05-17 15:25:43', '40f6cdea0bd34aceb77492a1656d9fb3', 'eb798626054543c6bb62a5debf8bc809');
INSERT INTO `tbl_tran_history` VALUES ('e5bdb9f3a46a4f4bbda04378f07c372a', 'ab8c2a3dc05f4e3dbc7a0405f721b040', '3000', '2022-05-27', '2022-05-17 18:47:21', '40f6cdea0bd34aceb77492a1656d9fb3', 'daf8fb5a3bcc44368df8c780e85281f2');

-- ----------------------------
-- Table structure for tbl_tran_remark
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tran_remark`;
CREATE TABLE `tbl_tran_remark`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `note_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tran_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_tran_remark
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid\r\n            ',
  `login_act` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码不能采用明文存储，采用密文，MD5加密之后的数据',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expire_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失效时间为空的时候表示永不失效，失效时间为2018-10-10 10:10:10，则表示在该时间之前该账户可用。',
  `lock_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁定状态为空时表示启用，为0时表示锁定，为1时表示启用。',
  `deptno` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `allow_ips` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '允许访问的IP为空时表示IP地址永不受限，允许访问的IP可以是一个，也可以是多个，当多个IP地址的时候，采用半角逗号分隔。允许IP是192.168.100.2，表示该用户只能在IP地址为192.168.100.2的机器上使用。',
  `createTime` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edit_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('06f5fc056eac41558a964f96daa7f27c', 'ls', '李四', 'yf123', 'ls@163.com', '2022-05-25 21:06:24', '1', 'A001', '192.168.1.1,0:0:0:0:0:0:0:1', '2018-11-22 12:11:40', '李四', NULL, NULL);
INSERT INTO `tbl_user` VALUES ('40f6cdea0bd34aceb77492a1656d9fb3', 'zs', '张三', 'yf123', 'zs@qq.com', '2022-06-24 21:06:24', '1', 'A001', '192.168.1.1,192.168.1.2,127.0.0.1,localhost', '2018-11-22 11:37:34', '张三', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
