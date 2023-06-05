DROP DATABASE IF EXISTS cov19_manage_sys;
CREATE DATABASE cov19_manage_sys;
USE cov19_manage_sys;

/*
 Navicat Premium Data Transfer

 Source Server         : mysql_cov19_manag_web
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : cov19_manage_sys

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 23/12/2022 02:08:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for approval_process
-- ----------------------------
DROP TABLE IF EXISTS `approval_process`;
CREATE TABLE `approval_process`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `day` int NOT NULL,
  -- `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `node_status` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  CONSTRAINT `appr_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of approval_process
-- ----------------------------
INSERT INTO `approval_process` VALUES (1, 2, '返乡', '北京市', 2, 1, '2022-11-27 10:13:32', '2022-11-27 10:13:34');
INSERT INTO `approval_process` VALUES (2, 2, '疫情居家', '西北工业大学海天苑', 9, 1, '2022-11-27 04:05:06', '2022-11-27 05:40:01');
INSERT INTO `approval_process` VALUES (3, 2, '疫情居家', '西北工业大学海天苑', 66, 2, '2022-11-27 04:07:34', NULL);
INSERT INTO `approval_process` VALUES (4, 6, '疫情居家', '西北工业大学海天苑', 66, 3, '2022-11-27 04:11:08', NULL);
INSERT INTO `approval_process` VALUES (5, 5, '疫情居家', '西北工业大学海天苑', 55, 4, '2022-11-27 04:11:15', '2022-11-27 05:40:46');
INSERT INTO `approval_process` VALUES (6, 5, '疫情居家', '西北工业大学海天苑', 333, 5, '2022-11-27 04:11:22', NULL);
INSERT INTO `approval_process` VALUES (7, 6, '疫情居家', '西北工业大学海天苑', 6, 1, '2022-11-27 04:12:00', NULL);
INSERT INTO `approval_process` VALUES (8, 1, '疫情居家', '西北工业大学星天苑', 6, 1, '2022-11-28 04:12:00', '2022-11-28 04:12:01');

-- ----------------------------
-- Table structure for china_total
-- ----------------------------
DROP TABLE IF EXISTS `china_total`;
CREATE TABLE `china_total`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `confirm` int NULL DEFAULT NULL,
  `input` int NULL DEFAULT NULL,
  `heal` int NULL DEFAULT NULL,
  `dead` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of china_total
-- ----------------------------
INSERT INTO `china_total` VALUES (1, 8938818, 27082, 374877, 29889,  '2022-11-23 12:31:45');
INSERT INTO `china_total` VALUES (2, 8961750, 27165, 377014, 29932,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (3, 8961750, 27165, 377014, 29932,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (4, 8961750, 27165, 377014, 29932,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (5, 8961750, 27165, 377014, 29932,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (6, 8961750, 27165, 377014, 29932,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (7, 8961750, 27165, 377014, 29932,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (8, 8981987, 27227, 379092, 30010,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (9, 8981987, 27227, 379092, 30010,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (10, 8981987, 27227,  379092, 30010,  '2022-11-25 00:42:32');
INSERT INTO `china_total` VALUES (11, 8981987, 27227,  379092, 30010,  '2022-11-25 02:47:33');
INSERT INTO `china_total` VALUES (12, 8981987, 27227,  379092, 30010,  '2022-11-25 02:47:33');
INSERT INTO `china_total` VALUES (13, 8981987, 27227,  379092, 30010,  '2022-11-25 02:47:33');
INSERT INTO `china_total` VALUES (14, 8981987, 27227,  379092, 30010,  '2022-11-25 02:47:33');
INSERT INTO `china_total` VALUES (15, 8981987, 27227,  379092, 30010,  '2022-11-25 02:47:33');
INSERT INTO `china_total` VALUES (16, 8981987, 27227,  379092, 30010,  '2022-11-25 02:47:33');
INSERT INTO `china_total` VALUES (17, 8981987, 27227,  379092, 30010,  '2022-11-25 04:17:36');
INSERT INTO `china_total` VALUES (18, 8981987, 27227,  379092, 30010,  '2022-11-25 04:17:36');
INSERT INTO `china_total` VALUES (19, 8981987, 27227,  379092, 30010,  '2022-11-25 04:17:36');
INSERT INTO `china_total` VALUES (20, 8981987, 27227,  379092, 30010,  '2022-11-25 06:47:38');
INSERT INTO `china_total` VALUES (21, 8981987, 27227,  379092, 30010,  '2022-11-25 10:22:42');
INSERT INTO `china_total` VALUES (22, 8981987, 27227,  379092, 30010,  '2022-11-25 10:22:42');
INSERT INTO `china_total` VALUES (23, 8981987, 27227,  379092, 30010,  '2022-11-25 11:27:44');
INSERT INTO `china_total` VALUES (24, 8981987, 27227,  379092, 30010,  '2022-11-25 11:27:44');
INSERT INTO `china_total` VALUES (25, 8981987, 27227,  379092, 30010,  '2022-11-25 11:27:44');
INSERT INTO `china_total` VALUES (26, 8981987, 27227,  379092, 30010,  '2022-11-25 11:52:41');
INSERT INTO `china_total` VALUES (27, 8981987, 27227,  379092, 30010,  '2022-11-25 11:52:41');
INSERT INTO `china_total` VALUES (28, 8981987, 27227,  379092, 30010,  '2022-11-25 11:52:41');
INSERT INTO `china_total` VALUES (29, 8981987, 27227,  379092, 30010,  '2022-11-25 11:52:41');
INSERT INTO `china_total` VALUES (30, 8981987, 27227,  379092, 30010,  '2022-11-25 11:52:41');
INSERT INTO `china_total` VALUES (31, 8981987, 27227,  379092, 30010,  '2022-11-25 11:52:41');
INSERT INTO `china_total` VALUES (32, 8981987, 27227,  379092, 30010,  '2022-11-26 00:47:58');
INSERT INTO `china_total` VALUES (33, 9000592, 27296,  381286, 30082,  '2022-11-26 02:37:59');
INSERT INTO `china_total` VALUES (34, 9000592, 27296,  381286, 30082,  '2022-11-26 02:37:59');
INSERT INTO `china_total` VALUES (35, 9000592, 27296,  381286, 30082,  '2022-11-26 02:37:59');
INSERT INTO `china_total` VALUES (36, 9000592, 27296,  381286, 30082,  '2022-11-27 00:33:28');
INSERT INTO `china_total` VALUES (37, 9018455, 27357,  383678, 30121,  '2022-11-27 00:33:28');
INSERT INTO `china_total` VALUES (38, 9018455, 27357,  383678, 30121,  '2022-11-27 00:33:28');
INSERT INTO `china_total` VALUES (39, 9018455, 27357,  383678, 30121,  '2022-11-27 02:23:30');
INSERT INTO `china_total` VALUES (40, 9018455, 27357,  383678, 30121,  '2022-11-27 02:23:30');
INSERT INTO `china_total` VALUES (41, 9018455, 27357,  383678, 30121,  '2022-11-27 02:23:30');
INSERT INTO `china_total` VALUES (42, 9018455, 27357,  383678, 30121,  '2022-11-27 02:23:30');
INSERT INTO `china_total` VALUES (43, 9018455, 27357,  383678, 30121,  '2022-11-27 03:38:32');
INSERT INTO `china_total` VALUES (44, 9018455, 27357,  383678, 30121,  '2022-11-27 03:38:32');
INSERT INTO `china_total` VALUES (45, 9018455, 27357,  383678, 30121,  '2022-11-27 03:38:32');
INSERT INTO `china_total` VALUES (46, 9018455, 27357,  383678, 30121,  '2022-11-27 04:43:33');
INSERT INTO `china_total` VALUES (47, 9018455, 27357,  383678, 30121,  '2022-11-27 04:43:33');
INSERT INTO `china_total` VALUES (48, 9018455, 27357,  383678, 30121,  '2022-11-27 04:43:33');
INSERT INTO `china_total` VALUES (49, 9018455, 27357,  383678, 30121,  '2022-11-27 04:43:33');

-- ----------------------------
-- Table structure for provin_data
-- ----------------------------
DROP TABLE IF EXISTS `provin_data`;
CREATE TABLE `provin_data`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `value` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of provin_data
-- ----------------------------
INSERT INTO `provin_data` VALUES (1, '台湾', 8226373, '2022-11-27 01:21:37');
INSERT INTO `provin_data` VALUES (2, '香港', 346747, '2022-11-27 01:21:38');
INSERT INTO `provin_data` VALUES (3, '湖北', 88, '2022-11-27 01:05:41');
INSERT INTO `provin_data` VALUES (4, '上海', 145, '2022-11-27 00:12:32');
INSERT INTO `provin_data` VALUES (5, '吉林', 66, '2022-11-27 01:21:37');
INSERT INTO `provin_data` VALUES (6, '广东', 10970, '2022-11-27 01:05:41');
INSERT INTO `provin_data` VALUES (7, '北京', 3776, '2022-11-27 01:21:38');
INSERT INTO `provin_data` VALUES (8, '海南', 76, '2022-11-27 01:05:40');
INSERT INTO `provin_data` VALUES (9, '四川', 1799, '2022-11-27 01:40:39');
INSERT INTO `provin_data` VALUES (10, '内蒙古', 576, '2022-11-27 01:24:37');
INSERT INTO `provin_data` VALUES (11, '河南', 2837, '2022-11-27 01:21:37');
INSERT INTO `provin_data` VALUES (12, '福建', 544, '2022-11-27 02:18:43');
INSERT INTO `provin_data` VALUES (13, '重庆', 2358, '2022-11-27 01:40:39');
INSERT INTO `provin_data` VALUES (14, '陕西', 555, '2022-11-27 01:21:38');
INSERT INTO `provin_data` VALUES (15, '黑龙江', 680, '2022-11-27 00:47:37');
INSERT INTO `provin_data` VALUES (16, '浙江', 430, '2022-11-27 01:40:39');
INSERT INTO `provin_data` VALUES (17, '山东', 505, '2022-11-26 23:41:32');
INSERT INTO `provin_data` VALUES (18, '云南', 913, '2022-11-27 02:44:41');
INSERT INTO `provin_data` VALUES (19, '山西', 2126, '2022-11-27 01:05:41');
INSERT INTO `provin_data` VALUES (20, '江苏', 398, '2022-11-27 01:40:39');
INSERT INTO `provin_data` VALUES (21, '河北', 621, '2022-11-27 00:26:32');
INSERT INTO `provin_data` VALUES (22, '天津', 164, '2022-11-27 02:44:40');
INSERT INTO `provin_data` VALUES (23, '新疆', 609, '2022-11-27 00:57:34');
INSERT INTO `provin_data` VALUES (24, '广西', 36, '2022-11-27 01:21:38');
INSERT INTO `provin_data` VALUES (25, '辽宁', 244, '2022-11-27 01:21:38');
INSERT INTO `provin_data` VALUES (26, '湖南', 193, '2022-11-27 02:18:44');
INSERT INTO `provin_data` VALUES (27, '安徽', 26, '2022-11-27 00:47:37');
INSERT INTO `provin_data` VALUES (28, '甘肃', 92, '2022-11-27 02:44:41');
INSERT INTO `provin_data` VALUES (29, '江西', 25, '2022-11-27 02:18:43');
INSERT INTO `provin_data` VALUES (30, '西藏', 32, '2022-11-27 02:44:40');
INSERT INTO `provin_data` VALUES (31, '贵州', 207, '2022-11-27 02:44:42');
INSERT INTO `provin_data` VALUES (32, '澳门', 2, '2022-11-27 01:21:37');
INSERT INTO `provin_data` VALUES (33, '青海', 51, '2022-11-27 02:44:40');
INSERT INTO `provin_data` VALUES (34, '宁夏', 9, '2022-11-27 01:21:37');

-- ----------------------------
-- Table structure for cov_news
-- ----------------------------
DROP TABLE IF EXISTS `cov_news`;
CREATE TABLE `cov_news`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `publishby` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cov_news
-- ----------------------------
INSERT INTO `cov_news` VALUES (1, '新冠政策改变！', '逐步放开管理', '2022-12-07 08:25:53', '国务院');
INSERT INTO `cov_news` VALUES (2, '多地预测疫情高峰', '春运后或出现新一轮疫情爆发', '2022-12-22 15:28:01', '网易');
INSERT INTO `cov_news` VALUES (3, '疫情冲击城市已开始恢复', '疫情冲击城市已开始恢复', '2022-12-22 17:05:59', '巨丰财经');

-- ----------------------------
-- Table structure for health_clock
-- ----------------------------
DROP TABLE IF EXISTS `health_clock`;
CREATE TABLE `health_clock`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `morning_temp` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `afternoon_temp` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `night_temp` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `recent_zone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `risk_zone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `health_status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `uid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  CONSTRAINT `healt_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of health_clock
-- ----------------------------
INSERT INTO `health_clock` VALUES (1, '36.5', '36.5', '36.5', '山东', '高风险', '健康', '2022-11-26 20:06:12', 2);
INSERT INTO `health_clock` VALUES (2, '36.5', '36.5', '36.5', '北京', '低风险', '健康', '2022-11-26 20:06:50', 3);
INSERT INTO `health_clock` VALUES (3, '36.5', '36.5', '36.5', '山东', '高风险', '健康', '2022-11-26 20:06:12', 4);
INSERT INTO `health_clock` VALUES (4, '36.5', '36.5', '36.5', '北京', '低风险', '健康', '2022-11-26 20:06:50', 1);
INSERT INTO `health_clock` VALUES (5, '36.5', '36.5', '36.5', '山东', '高风险', '健康', '2022-11-26 20:06:12', 5);
INSERT INTO `health_clock` VALUES (6, '36.5', '36.5', '36.5', '北京', '低风险', '健康', '2022-11-26 20:06:50', 6);
INSERT INTO `health_clock` VALUES (7, '36.5', '36.5', '36.5', '山东', '高风险', '健康', '2022-11-26 20:06:12', 7);
INSERT INTO `health_clock` VALUES (8, '36.5', '36.5', '36.5', '北京', '低风险', '健康', '2022-11-26 20:06:50', 7);
-- ----------------------------
-- Table structure for hesuan
-- ----------------------------
DROP TABLE IF EXISTS `hesuan`;
CREATE TABLE `hesuan`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `result` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `result_time` datetime NOT NULL,
  `uid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  CONSTRAINT `hesuan_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hesuan
-- ----------------------------
INSERT INTO `hesuan` VALUES (1, '混检', '阴性', '2022-12-11 09:59:56', '2022-12-11 09:59:59', 1);
INSERT INTO `hesuan` VALUES (2, '单检', '阴性', '2022-12-10 16:00:00', '2022-12-10 19:00:00', 2);
INSERT INTO `hesuan` VALUES (3, '单检', '阳性', '2022-12-10 16:00:00', '2022-12-10 19:00:00', 3);
INSERT INTO `hesuan` VALUES (4, '混检', '阴性', '2022-12-11 09:59:56', '2022-12-11 09:59:59', 4);
INSERT INTO `hesuan` VALUES (5, '单检', '阴性', '2022-12-10 16:00:00', '2022-12-10 19:00:00', 5);
INSERT INTO `hesuan` VALUES (6, '混检', '阳性', '2022-12-10 16:00:00', '2022-12-10 19:00:00', 6);
INSERT INTO `hesuan` VALUES (7, '单检', '阴性', '2022-12-20 16:00:00', '2022-12-20 17:00:00', 7);
INSERT INTO `hesuan` VALUES (8, '混检', '阳性', '2022-12-21 16:00:00', '2022-12-22 19:00:00', 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '基本菜单',
  `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `href` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `open` int NOT NULL DEFAULT 1,
  `available` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
  -- INDEX `menu_ibfk_1_idx`(`pid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '疫闻速递', '&#xe62d;', NULL, 0, 1);
INSERT INTO `menu` VALUES (2, 0, '校园防疫', '&#xe679;', NULL, 0, 1);
INSERT INTO `menu` VALUES (3, 0, '系统管理', '&#xe716;', NULL, 0, 1);
INSERT INTO `menu` VALUES (4, 2, '校园请假审批', '&#xe66c;', '/approval/toApproval', 0, 1);
INSERT INTO `menu` VALUES (5, 2, '校园核酸检测', '&#xe642;', '/hesuan/toHeSuan', 0, 1);
INSERT INTO `menu` VALUES (6, 2, '校园疫苗接种', '&#xe6b1;', '/vaccine/toVaccine', 0, 1);
INSERT INTO `menu` VALUES (7, 2, '健康打卡记录', '&#xe62a;', '/toHealthClock', 0, 1);
INSERT INTO `menu` VALUES (8,3, '菜单管理', '&#xe649;', '/menu/toMenu', 0, 1);
INSERT INTO `menu` VALUES (9, 3, '角色管理', '&#xe6af;', '/role/toRole', 0, 1);
INSERT INTO `menu` VALUES (10, 3, '用户管理', '&#xe613;', '/user/toUser', 0, 1);
INSERT INTO `menu` VALUES (11, 1, '疫情新闻管理', '&#xe7ae;', '/news/toNews', 0, 1);
INSERT INTO `menu` VALUES (12, 1, '疫情风险地区查询', '&#xe6b2;', '/zone/toZone', 0, 1);
INSERT INTO `menu` VALUES (13, 3, '学院管理', '&#xe62e;', '/xueyuan/toXueYuan', 0, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `level` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '拥有所有的权限','管理员级');
INSERT INTO `role` VALUES (2, '教师', '拥有增删改查和老师层请假审批权','教师级');
INSERT INTO `role` VALUES (3, '学生', '拥有查询权','学生级');
INSERT INTO `role` VALUES (4, '学院工作人员', '拥有增删改查和学院层请假审批权','院级');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `rid` int NULL DEFAULT NULL,
  `mid` int NULL DEFAULT NULL,
  INDEX `rid`(`rid` ASC) USING BTREE,
  INDEX `mid`(`mid` ASC) USING BTREE,
  CONSTRAINT `role_me_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_me_ibfk_2` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 1);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 2);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 3);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 4);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 5);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 6);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 7);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 8);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 9);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 10);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 11);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 12);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (1, 13);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 1);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 12);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 2);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 4);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 5);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 6);
INSERT INTO `cov19_manage_sys`.`role_menu` (`rid`, `mid`) VALUES (2, 7);


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `campus_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `identi_card` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT "123456",
  `sex` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT "/images/login.jpg",
  `phone` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  
  `xue_yuan_id` int NULL DEFAULT NULL,
  `rid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `campus_id_UNIQUE` (`campus_id` ASC) USING BTREE,
  INDEX `xue_yuan_id`(`xue_yuan_id` ASC) USING BTREE,
  INDEX `rid`(`rid` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`xue_yuan_id`) REFERENCES `xue_yuan` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '370203212315155167','123456', '男', 25, '北京市通州区', '/images/login.jpg', '18333603846', 1, 1);
INSERT INTO `user` VALUES (2, '2020303150', '郭同学','370203212315155168', '123456', '男', 20, '山东省青岛市', '/images/login.jpg', '18089253467', 1, 3);
INSERT INTO `user` VALUES (3, '2002303150', '李老师','370203212315155169', '123456', '男', 40, '陕西省西安市', '/images/login.jpg', '1808928977', 1, 2);
INSERT INTO `user` VALUES (4, '2001303157', '王辅导员','370203212315155170', '123456', '男', 35, '陕西省西安市', '/images/login.jpg', '180892877', 1, 4);
INSERT INTO `user` VALUES (5, '2020303159', '张同学','370203212315155178', '123456', '女', 20, '山东省济南市', '/images/login.jpg', '18089253997', 2, 3);
INSERT INTO `user` VALUES (6, '2020303160', '何同学','370203212315155158', '123456', '女', 20, '山东省威海市', '/images/login.jpg', '18089253947', 2, 3);
INSERT INTO `user` VALUES (7, '2002303151', '乔老师','370203212315155869', '123456', '男', 45, '陕西省西安市', '/images/login.jpg', '1808928977', 2, 2);
-- ----------------------------
-- Table structure for vaccine
-- ----------------------------
DROP TABLE IF EXISTS `vaccine`;
CREATE TABLE `vaccine`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `zhenci` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `pici` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `changjia` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `danwei` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `uid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  CONSTRAINT `vacc_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vaccine
-- ----------------------------
INSERT INTO `vaccine` VALUES (1, '三针', '第一批次', '北京科兴', '西工大校医院', '2022-11-02 16:00:00', 2);
INSERT INTO `vaccine` VALUES (2, '三针', '第二批次', '北京科兴', '西工大校医院', '2022-11-10 16:00:00', 3);
INSERT INTO `vaccine` VALUES (3, '三针', '第一批次', '北京科兴', '西工大校医院', '2022-11-02 16:00:00', 1);
INSERT INTO `vaccine` VALUES (4, '二针', '第三批次', '北京科兴', '西工大校医院', '2022-11-10 16:00:00', 4);
INSERT INTO `vaccine` VALUES (5, '三针', '第一批次', '北京科兴', '西工大校医院', '2022-11-02 16:00:00', 5);
INSERT INTO `vaccine` VALUES (6, '二针', '第二批次', '北京科兴', '西工大校医院', '2022-11-10 16:00:00', 6);
INSERT INTO `vaccine` VALUES (7, '三针', '第二批次', '北京科兴', '西工大校医院', '2022-11-10 16:00:00', 7);

-- ----------------------------
-- Table structure for xue_yuan
-- ----------------------------
DROP TABLE IF EXISTS `xue_yuan`;
CREATE TABLE `xue_yuan`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xue_yuan
-- ----------------------------
INSERT INTO `xue_yuan` VALUES (1, '计算机学院');
INSERT INTO `xue_yuan` VALUES (2, '航空学院');
INSERT INTO `xue_yuan` VALUES (3, '软件学院');

SET FOREIGN_KEY_CHECKS = 1;
