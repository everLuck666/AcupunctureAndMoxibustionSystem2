/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : xundian

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 06/02/2021 21:56:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` longtext,
  `image` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
BEGIN;
INSERT INTO `article` VALUES (4, '标题', '简介', '3B171549E0CD942E70F0D8A6DFD95ED9.jpg', '2.txt', '2021-01-29 20:08:07');
COMMIT;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `product_price` double(10,0) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES ('商品', '123', 1, '12021-02-06-11-09-46.png', '1');
INSERT INTO `goods` VALUES ('商品1', '商品1', 100, '12021-02-06-13-53-33.png', '0');
INSERT INTO `goods` VALUES ('艾灸仪', '商品', 1, '12021-02-06-11-09-46.png', '1');
INSERT INTO `goods` VALUES ('艾灸仪2.0', '升级版', 1, '12021-02-06-11-09-46.png', '1');
COMMIT;

-- ----------------------------
-- Table structure for medical_record
-- ----------------------------
DROP TABLE IF EXISTS `medical_record`;
CREATE TABLE `medical_record` (
  `user_id` varchar(255) DEFAULT NULL,
  `project_id` varchar(255) DEFAULT NULL,
  `total_time` varchar(255) DEFAULT NULL,
  `progress` varchar(255) DEFAULT NULL,
  `symptom_name` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medical_record
-- ----------------------------
BEGIN;
INSERT INTO `medical_record` VALUES ('1', '1', '3', '0', '咳嗽', '2021-01-29 20:16:45', NULL);
INSERT INTO `medical_record` VALUES ('o-7Fs5I9YQR50Yov70osMCUG-IxE', '1', '0', '0', '咳嗽', '2021-02-02 21:30:01', NULL);
INSERT INTO `medical_record` VALUES ('o-7Fs5O8Zaqu8gB1yjri-GCTC3vI', '1', '0', '0', '咳嗽', '2021-02-05 16:21:47', NULL);
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` varchar(255) NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `user_address` varchar(255) DEFAULT NULL,
  `order_amout` double(255,0) DEFAULT NULL,
  `order_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`,`product_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
INSERT INTO `orders` VALUES ('1234', 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '艾灸仪', '1', '1', '1', '1', 1, '2021-02-06 00:00:00', '1', '1');
INSERT INTO `orders` VALUES ('123ad23d', 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '艾灸仪2.0', '2', '2', '2', '2', 2, '2021-02-05 00:00:00', '1', '1');
INSERT INTO `orders` VALUES ('45984', 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '商品', '3', '3', '3', '3', 3, '2021-01-08 00:00:00', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for symptom
-- ----------------------------
DROP TABLE IF EXISTS `symptom`;
CREATE TABLE `symptom` (
  `symptom_id` varchar(255) DEFAULT NULL COMMENT '症状id',
  `reason` varchar(255) DEFAULT NULL COMMENT '病因',
  `symptom_name` varchar(255) DEFAULT NULL COMMENT '症状名称',
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of symptom
-- ----------------------------
BEGIN;
INSERT INTO `symptom` VALUES ('1', '着凉', '咳嗽', 'tian.jpg2021-01-29-21-02-19.jpg');
COMMIT;

-- ----------------------------
-- Table structure for treat
-- ----------------------------
DROP TABLE IF EXISTS `treat`;
CREATE TABLE `treat` (
  `treat_id` varchar(255) DEFAULT NULL,
  `symptom_id` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treat
-- ----------------------------
BEGIN;
INSERT INTO `treat` VALUES ('1', '1', '2021-01-29 21:02:20');
COMMIT;

-- ----------------------------
-- Table structure for treat_project
-- ----------------------------
DROP TABLE IF EXISTS `treat_project`;
CREATE TABLE `treat_project` (
  `treat_id` varchar(255) NOT NULL,
  `effect` varchar(255) DEFAULT NULL COMMENT '诊疗效果',
  `total_time` varchar(255) DEFAULT NULL COMMENT '总时间',
  `treat_describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `treat_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treat_project
-- ----------------------------
BEGIN;
INSERT INTO `treat_project` VALUES ('1', '可能导致头痛', '0', NULL, '针剂');
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, '123', '张三', '123', '广州');
INSERT INTO `user_info` VALUES (2, '123', '李四', '123', '深圳');
INSERT INTO `user_info` VALUES (3, '234', '扒拉', '132', '北京');
INSERT INTO `user_info` VALUES (4, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '张三', '12345', '广州');
INSERT INTO `user_info` VALUES (5, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '扒拉了', '12345', '深圳');
INSERT INTO `user_info` VALUES (6, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '肖像', '12345689', '潮州');
INSERT INTO `user_info` VALUES (7, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '肖像', '12345689', '潮州');
INSERT INTO `user_info` VALUES (8, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '肖像', '12345689', '潮州');
INSERT INTO `user_info` VALUES (9, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '肖像', '12345689', '潮州');
INSERT INTO `user_info` VALUES (10, 'o-7Fs5I9YQR50Yov70osMCUG-IxE', '肖像', '12345689', '潮州');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `identity` int DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('123456', '张三', '123456', 1, NULL);
INSERT INTO `users` VALUES ('5555', 'zhangsan ', '123456', 1, NULL);
INSERT INTO `users` VALUES ('55fff', '帅气的管理员', '1123', 1, NULL);
INSERT INTO `users` VALUES ('fff', '一只美女', ' 56465', 1, NULL);
INSERT INTO `users` VALUES ('ffffssaz', '一直帅哥', '555', 1, NULL);
INSERT INTO `users` VALUES ('o-7Fs5GQ3GO7bsGmj9hNPn5S4KYY', NULL, NULL, 0, NULL);
INSERT INTO `users` VALUES ('o-7Fs5I9YQR50Yov70osMCUG-IxE', NULL, NULL, 0, NULL);
INSERT INTO `users` VALUES ('o-7Fs5O8Zaqu8gB1yjri-GCTC3vI', NULL, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `videoName` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of video
-- ----------------------------
BEGIN;
INSERT INTO `video` VALUES ('时永华－中国著名油画大师作品.mp42021-01-26-15-59-19.mp4', 'static/video/时永华－中国著名油画大师作品.mp42021-01-26-15-59-19.mp4', '2021-01-26');
INSERT INTO `video` VALUES ('时永华－中国著名油画大师作品.mp4', 'static/video/时永华－中国著名油画大师作品.mp4', '2021-01-26 16:38:51');
COMMIT;

-- ----------------------------
-- Table structure for xue_treat
-- ----------------------------
DROP TABLE IF EXISTS `xue_treat`;
CREATE TABLE `xue_treat` (
  `xue_id` varchar(255) DEFAULT NULL,
  `day` int DEFAULT NULL,
  `treat_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xue_treat
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for xue_wei
-- ----------------------------
DROP TABLE IF EXISTS `xue_wei`;
CREATE TABLE `xue_wei` (
  `pointName` varchar(100) DEFAULT NULL,
  `temperature` varchar(100) DEFAULT NULL,
  `treatTime` int DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xue_wei
-- ----------------------------
BEGIN;
INSERT INTO `xue_wei` VALUES ('天地2', '123', 123, '1', '001.png2021-01-29-20-02-38.png');
INSERT INTO `xue_wei` VALUES ('天地', '123', 123, '2', '695bf5429472049b52c1e0de586f8a2511195a23.png@100w_100h.png');
COMMIT;

-- ----------------------------
-- View structure for finished_orders
-- ----------------------------
DROP VIEW IF EXISTS `finished_orders`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `finished_orders` AS select count(`orders`.`order_id`) AS `finishedOrders` from `orders` where (`orders`.`status` = '1');

-- ----------------------------
-- View structure for goods_allinfo
-- ----------------------------
DROP VIEW IF EXISTS `goods_allinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `goods_allinfo` AS select `a`.`product_name` AS `product_name`,`a`.`describe` AS `describe`,`a`.`product_price` AS `product_price`,`b`.`sales` AS `sales`,`a`.`image_url` AS `image_url`,`a`.`status` AS `status` from (`goods` `a` left join `goods_sales` `b` on((`a`.`product_name` = `b`.`product_name`))) union select `a`.`product_name` AS `product_name`,`a`.`describe` AS `describe`,`a`.`product_price` AS `product_price`,`b`.`sales` AS `sales`,`a`.`image_url` AS `image_url`,`a`.`status` AS `status` from (`goods_sales` `b` left join `goods` `a` on((`a`.`product_name` = `b`.`product_name`)));

-- ----------------------------
-- View structure for goods_sales
-- ----------------------------
DROP VIEW IF EXISTS `goods_sales`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `goods_sales` AS select `orders`.`product_name` AS `product_name`,sum(`orders`.`product_number`) AS `sales` from `orders` group by `orders`.`product_name`;

-- ----------------------------
-- View structure for waiting_orders
-- ----------------------------
DROP VIEW IF EXISTS `waiting_orders`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `waiting_orders` AS select count(`orders`.`order_id`) AS `waitingOrders` from `orders` where (`orders`.`status` = '0');

SET FOREIGN_KEY_CHECKS = 1;
