/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : moguying

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-13 12:55:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for plant_activity
-- ----------------------------
DROP TABLE IF EXISTS `plant_activity`;
CREATE TABLE `plant_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '主标题',
  `sub_title` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '副标题',
  `open_time` datetime NOT NULL COMMENT '开始时间',
  `close_time` datetime NOT NULL COMMENT '结束时间',
  `pic_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '缩略图',
  `content` text COLLATE utf8_unicode_ci NOT NULL COMMENT '活动内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for plant_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `plant_admin_role`;
CREATE TABLE `plant_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `action_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `plant_admin_user`;
CREATE TABLE `plant_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '后台用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
  `is_locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定',
  `last_login_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '最后一次登录时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后一次登录ip',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_adv
-- ----------------------------
DROP TABLE IF EXISTS `plant_adv`;
CREATE TABLE `plant_adv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL DEFAULT '0' COMMENT '广告位置[类型]',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '广告描述',
  `pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '图片url',
  `thumb_pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '缩略图url',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `order_number` int(11) NOT NULL DEFAULT '50',
  `adv_url` varchar(255) NOT NULL DEFAULT '' COMMENT '广告跳转地址',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `close_time` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_adv_type
-- ----------------------------
DROP TABLE IF EXISTS `plant_adv_type`;
CREATE TABLE `plant_adv_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT '' COMMENT '位置名称',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `position_flag` varchar(255) DEFAULT '' COMMENT '位置代号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_article
-- ----------------------------
DROP TABLE IF EXISTS `plant_article`;
CREATE TABLE `plant_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '文章标题',
  `type_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章分类',
  `seo_key` varchar(255) NOT NULL DEFAULT '' COMMENT 'seo关键字',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `is_show` varchar(255) NOT NULL DEFAULT '1' COMMENT '是否显示[0否，1是]',
  `pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '文章导图',
  `thumb_pic_url` varchar(255) NOT NULL COMMENT '文章导图缩略图',
  `read_count` int(11) NOT NULL DEFAULT '0' COMMENT '阅读次数',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_article_content
-- ----------------------------
DROP TABLE IF EXISTS `plant_article_content`;
CREATE TABLE `plant_article_content` (
  `article_id` int(11) NOT NULL DEFAULT '0',
  `content` text NOT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_article_type
-- ----------------------------
DROP TABLE IF EXISTS `plant_article_type`;
CREATE TABLE `plant_article_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_banner
-- ----------------------------
DROP TABLE IF EXISTS `plant_banner`;
CREATE TABLE `plant_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'banner类型[1移动端,2PC端]',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT 'banner名',
  `pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'banner图片',
  `thumb_pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '缩略图',
  `jump_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'banner跳转地址',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示[0不显示，1显示]',
  `sort_order` tinyint(2) NOT NULL,
  `show_time` datetime DEFAULT NULL COMMENT '显示时间',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_block
-- ----------------------------
DROP TABLE IF EXISTS `plant_block`;
CREATE TABLE `plant_block` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(11) NOT NULL DEFAULT '0' COMMENT '土地编号',
  `seed_type` int(11) NOT NULL DEFAULT '0' COMMENT '种植菌类',
  `grow_days` int(5) NOT NULL DEFAULT '0' COMMENT '棚内培育菌包的生长周期',
  `interest_rates` decimal(15,2) NOT NULL,
  `level` tinyint(4) NOT NULL COMMENT '土地等级',
  `total_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '总额度',
  `per_price` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `total_count` int(11) NOT NULL DEFAULT '0' COMMENT '总份数',
  `left_count` int(11) NOT NULL DEFAULT '0' COMMENT '剩余额度',
  `has_count` int(11) NOT NULL DEFAULT '0' COMMENT '已用额度',
  `max_plant` int(11) NOT NULL DEFAULT '0' COMMENT '最大种植份数',
  `min_plant` int(11) NOT NULL DEFAULT '0' COMMENT '最低种植份数',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示[0不显示，1显示]',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态[0未开放，1已开放]',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除[0否，1是]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for plant_express
-- ----------------------------
DROP TABLE IF EXISTS `plant_express`;
CREATE TABLE `plant_express` (
  `id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL DEFAULT '0' COMMENT '收货地址id',
  `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '产品id',
  `product_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '产品类型[1菌包兑换，2采摘兑换，3商城商品]',
  `express_number` varchar(255) NOT NULL DEFAULT '0' COMMENT '快递单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_fertilizer
-- ----------------------------
DROP TABLE IF EXISTS `plant_fertilizer`;
CREATE TABLE `plant_fertilizer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '化肥名称',
  `add_rate` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '可增加收益率',
  `numbers` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_fertilizer_type
-- ----------------------------
DROP TABLE IF EXISTS `plant_fertilizer_type`;
CREATE TABLE `plant_fertilizer_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL DEFAULT '' COMMENT '肥料类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_mall_car
-- ----------------------------
DROP TABLE IF EXISTS `plant_mall_car`;
CREATE TABLE `plant_mall_car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
  `product_count` varchar(255) NOT NULL DEFAULT '0' COMMENT '购物车中的数量',
  `is_check` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否勾选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `plant_mall_order`;
CREATE TABLE `plant_mall_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(255) NOT NULL DEFAULT '' COMMENT '订单流水号',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `buy_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '购买总价',
  `buy_mark` varchar(255) NOT NULL DEFAULT '' COMMENT '买家备注',
  `fee_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '订单快递费',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态[0未支付，1已支付（待发货），2已发货（待收货），3已完成，4已关单,5已取消]',
  `address_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户收货地址id',
  `add_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `close_time` datetime DEFAULT NULL COMMENT '关单时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `is_notice` tinyint(1) DEFAULT '0' COMMENT '是否已提醒过发货',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认收货时间',
  `express_order_number` varchar(255) DEFAULT '' COMMENT '快递单号',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '取消订单原因',
  `pay_sms_seq_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_mall_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `plant_mall_order_detail`;
CREATE TABLE `plant_mall_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单流水号',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '产品id',
  `buy_count` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `buy_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '购买总价',
  `fee_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '快递费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_mall_product
-- ----------------------------
DROP TABLE IF EXISTS `plant_mall_product`;
CREATE TABLE `plant_mall_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址',
  `thumb_pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '缩略图地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '产品名称',
  `price` decimal(15,2) NOT NULL DEFAULT '0.00',
  `old_price` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '优惠前价格',
  `fee` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '快递费',
  `total_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总库存',
  `left_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '库存',
  `has_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '购买份数',
  `product_desc` text NOT NULL COMMENT '商品详情',
  `summary_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '商品摘要',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否上架[0否，1是]',
  `delivery_info` varchar(255) NOT NULL DEFAULT '' COMMENT '配送信息',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_mall_product_fee
-- ----------------------------
DROP TABLE IF EXISTS `plant_mall_product_fee`;
CREATE TABLE `plant_mall_product_fee` (
  `id` int(11) NOT NULL,
  `fee_type` tinyint(1) NOT NULL,
  `fee_amount` varchar(255) NOT NULL,
  `fee_rule` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_money_recharge
-- ----------------------------
DROP TABLE IF EXISTS `plant_money_recharge`;
CREATE TABLE `plant_money_recharge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `order_number` varchar(100) NOT NULL DEFAULT '0',
  `money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '充值订单状态[0充值中，1充值成功，2充值失败]',
  `recharge_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '充值时间',
  `to_account_time` datetime DEFAULT NULL COMMENT '到账时间',
  `to_account_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '到账金额',
  `fee` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '充值手续费',
  `pay_sms_sq_no` varchar(25) DEFAULT NULL COMMENT '第三方支付短信流水号',
  `source` varchar(20) NOT NULL DEFAULT 'PC' COMMENT '充值来源',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_number` (`order_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_money_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `plant_money_withdraw`;
CREATE TABLE `plant_money_withdraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(100) NOT NULL DEFAULT '0' COMMENT '提现流水号',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `withdraw_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `fee` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '手续费',
  `to_account_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '到账金额',
  `withdraw_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '提现时间',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  `verify_user` int(11) DEFAULT NULL COMMENT '审核用户',
  `verify_mark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `bank_number` varchar(30) NOT NULL COMMENT '到账银行卡号',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '提现状态[0未审核，1审核通过，2审核未通，3已到账]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_nav
-- ----------------------------
DROP TABLE IF EXISTS `plant_nav`;
CREATE TABLE `plant_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `jump_url` varchar(255) NOT NULL,
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示[0否，1是]',
  `is_blank` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否新窗口打开[0否，1是]',
  `order_number` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '类型[1种植平台，2商城平台]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `plant_payment_info`;
CREATE TABLE `plant_payment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(255) NOT NULL DEFAULT '' COMMENT '记录生成订单号',
  `request_action` varchar(255) NOT NULL DEFAULT '' COMMENT '请求方法',
  `payment_request` longtext NOT NULL COMMENT '请求参数',
  `payment_response` longtext NOT NULL COMMENT '请求响应',
  `notify_response` longtext,
  `sign_data` longtext NOT NULL COMMENT '签名数据',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '处理状态[1已处理，0未处理]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_phone_message
-- ----------------------------
DROP TABLE IF EXISTS `plant_phone_message`;
CREATE TABLE `plant_phone_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否校验[0未校验，1已校验]',
  `add_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_reap
-- ----------------------------
DROP TABLE IF EXISTS `plant_reap`;
CREATE TABLE `plant_reap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(255) NOT NULL DEFAULT '' COMMENT '操作流水',
  `order_id` int(11) NOT NULL DEFAULT '0' COMMENT '种植订单id',
  `seed_id` int(11) NOT NULL DEFAULT '0' COMMENT '种子id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `block_id` int(11) NOT NULL DEFAULT '0' COMMENT '棚区id',
  `plant_count` int(11) NOT NULL DEFAULT '0' COMMENT '种植份数',
  `plant_time` datetime DEFAULT NULL COMMENT '种植时间',
  `pre_profit` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '预计收益',
  `pre_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '预计收回种本成本',
  `pre_reap_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最后一次收获时间',
  `rec_profit` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实际收益',
  `rec_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实际收回成本',
  `rec_reap_time` datetime DEFAULT NULL COMMENT '实际最后一次收获时间',
  `reap_times` int(11) NOT NULL DEFAULT '0' COMMENT '收获次数',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '添加时间',
  `sale_time` datetime DEFAULT NULL COMMENT '出售时间',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '[0待采摘，1已采摘，2售卖中，3已售卖]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed`;
CREATE TABLE `plant_seed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '种子名称',
  `small_name` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '副标题 ',
  `per_price` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '总价',
  `total_interest` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '预计收益',
  `grow_days` int(4) NOT NULL DEFAULT '0' COMMENT '生长周期',
  `serial_number` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '种子编号',
  `interest_rates` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '利率',
  `max_plant` int(5) NOT NULL DEFAULT '0' COMMENT '最大可购买种植份数',
  `min_plant` int(1) NOT NULL DEFAULT '0' COMMENT '最小购买种植份数',
  `seed_type` int(11) NOT NULL DEFAULT '0' COMMENT '种子对应的品种',
  `total_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总份数',
  `left_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '剩余份数',
  `open_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '购买时间',
  `close_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '结束时间',
  `pic_ids` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片相册id，多个以,相隔',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '种子状态[0待审核，1可购买，2已售罄,3已取消]',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否上架[0未上架，1已上架]',
  `plant_level` tinyint(2) DEFAULT '0' COMMENT '在多少级别的棚区种植',
  `reap_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '收获类型[1,按月收获，2，到期收获]',
  `plant_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '种植类型[1:成交日种植，2:T+1种植，3:T+2种植]',
  `review_mark` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '审核备注',
  `add_uid` int(11) NOT NULL DEFAULT '0' COMMENT '添加的用户id',
  `review_uid` int(11) NOT NULL DEFAULT '0' COMMENT '审核用户',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '添加时间',
  `real_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实际订单金额',
  `real_interest` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实际订单收益',
  `reap_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '收获的成本',
  `reap_interest` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '收获的收益',
  `inner_count` int(11) NOT NULL DEFAULT '0' COMMENT '内购份数',
  `order_number` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `full_time` datetime DEFAULT NULL COMMENT '订单售罄时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for plant_seed_content
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_content`;
CREATE TABLE `plant_seed_content` (
  `seed_id` int(11) NOT NULL DEFAULT '0' COMMENT '种子id',
  `seed_introduce` text COMMENT '种子介绍',
  `contract_content` text COMMENT '合同',
  PRIMARY KEY (`seed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_days
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_days`;
CREATE TABLE `plant_seed_days` (
  `grow_days_name` varchar(255) NOT NULL DEFAULT '' COMMENT '生长周期名称',
  `grow_days` int(4) NOT NULL DEFAULT '0' COMMENT '生长天数',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`grow_days`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_exchange
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_exchange`;
CREATE TABLE `plant_seed_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seed_id` int(11) NOT NULL DEFAULT '0' COMMENT '种子id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '种植用户id',
  `exchange_count` int(11) NOT NULL DEFAULT '0' COMMENT '兑换份数',
  `express_id` int(11) NOT NULL DEFAULT '0' COMMENT '快递记录id',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '[0未收货，1已收货]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_inner_order
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_inner_order`;
CREATE TABLE `plant_seed_inner_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(100) NOT NULL DEFAULT '0' COMMENT '订单编号',
  `seed_id` int(11) NOT NULL DEFAULT '0' COMMENT '种子id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `block_id` int(11) NOT NULL DEFAULT '0' COMMENT '棚区id',
  `plant_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '购买种子份数',
  `plant_amount` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '购买总价',
  `plant_profit` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '种植利润',
  `plant_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '种子订单状态[0已购买未种植，1已种植,2已取消]',
  `plant_time` datetime DEFAULT NULL COMMENT '种植时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_order
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_order`;
CREATE TABLE `plant_seed_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(100) NOT NULL DEFAULT '0' COMMENT '订单编号',
  `seed_id` int(11) NOT NULL DEFAULT '0' COMMENT '种子id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `buy_count` int(11) NOT NULL DEFAULT '0' COMMENT '购买份数',
  `buy_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '购买总价',
  `plant_count` int(11) NOT NULL DEFAULT '0' COMMENT '种植份数',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态[0：可用，1：已用]',
  `add_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_order_detail`;
CREATE TABLE `plant_seed_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(100) NOT NULL DEFAULT '0' COMMENT '订单编号',
  `seed_id` int(11) NOT NULL DEFAULT '0' COMMENT '种子id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `buy_count` int(11) NOT NULL DEFAULT '0' COMMENT '购买份数',
  `buy_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '购买总价',
  `add_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_pic
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_pic`;
CREATE TABLE `plant_seed_pic` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `pic_name` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `pic_url_thumb` varchar(255) DEFAULT NULL COMMENT '缩略图url',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除[0未删除，1已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_seed_type
-- ----------------------------
DROP TABLE IF EXISTS `plant_seed_type`;
CREATE TABLE `plant_seed_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) NOT NULL DEFAULT '' COMMENT '种子种类名称',
  `grow_days` int(11) NOT NULL DEFAULT '0' COMMENT '生长周期',
  `per_price` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '每份价格',
  `interest_rates` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '利率',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父类id',
  `order_number` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除[0未删除，1已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user
-- ----------------------------
DROP TABLE IF EXISTS `plant_user`;
CREATE TABLE `plant_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL DEFAULT '0' COMMENT '用户手机号',
  `real_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `id_card` varchar(255) NOT NULL DEFAULT '0' COMMENT '身份证号码',
  `is_real_name` tinyint(1) NOT NULL DEFAULT '2' COMMENT '是否实名[0认证失败，1已认证，2未认证，3审核中]',
  `is_long_time` tinyint(1) NOT NULL DEFAULT '0' COMMENT '身份证是否长期有效【0非长期，1长期】',
  `id_expire_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '身份证有效期',
  `is_bind_card` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否绑卡',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
  `pay_password` varchar(255) NOT NULL DEFAULT '' COMMENT '支付密码',
  `avatars_id` int(11) NOT NULL DEFAULT '0' COMMENT '头像id',
  `reg_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '注册时间',
  `reg_ip` varchar(255) NOT NULL DEFAULT '0' COMMENT '注册ip',
  `reg_source` varchar(255) NOT NULL DEFAULT 'PC' COMMENT '注册来源',
  `invite_code` varchar(255) NOT NULL DEFAULT '' COMMENT '注册邀请码',
  `invite_uid` int(11) NOT NULL DEFAULT '0' COMMENT '邀请人用户id',
  `user_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态[0已冻结，1已使用]',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `payment_account` varchar(255) NOT NULL DEFAULT '' COMMENT '子商户号',
  `payment_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '子商户状态[0未注册，1注册]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_address
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_address`;
CREATE TABLE `plant_user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `province_name` varchar(11) NOT NULL DEFAULT '0' COMMENT '省份id',
  `city_name` varchar(11) NOT NULL DEFAULT '0' COMMENT '市id',
  `town_name` varchar(11) NOT NULL DEFAULT '0' COMMENT '县id',
  `receive_user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '收货人名',
  `receive_phone` varchar(11) NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `detail_address` varchar(255) NOT NULL COMMENT '地址详情',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_bank
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_bank`;
CREATE TABLE `plant_user_bank` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `order_number` varchar(255) NOT NULL DEFAULT 'PBC000000' COMMENT '第三方支付平台绑卡流水号',
  `bank_number` varchar(30) NOT NULL DEFAULT '0' COMMENT '银行卡号',
  `bank_id` int(5) NOT NULL DEFAULT '0' COMMENT '所属银行',
  `bank_address` varchar(255) NOT NULL DEFAULT '' COMMENT '支行地址',
  `add_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '绑卡时间',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '卡片状态[1绑卡成功，0绑卡失败]',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_inner
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_inner`;
CREATE TABLE `plant_user_inner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `user_phone` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`user_phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_invite
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_invite`;
CREATE TABLE `plant_user_invite` (
  `user_id` int(11) NOT NULL,
  `phone` varchar(11) NOT NULL DEFAULT '',
  `plant_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '种植金额',
  `invite_award` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '邀请奖励金额',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '邀请人id',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_login_log`;
CREATE TABLE `plant_user_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `login_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '登录时间',
  `login_ip` varchar(255) NOT NULL DEFAULT '0' COMMENT '登录ip',
  `login_type` varchar(255) NOT NULL DEFAULT 'PC' COMMENT '登录方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_message
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_message`;
CREATE TABLE `plant_user_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL DEFAULT '' COMMENT '消息内容',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读[0未读，1已读]',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除[0未删除，1已删除]',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_money
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_money`;
CREATE TABLE `plant_user_money` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `available_money` decimal(15,2) unsigned DEFAULT '0.00' COMMENT '可用金额',
  `freeze_money` decimal(15,2) unsigned DEFAULT '0.00' COMMENT '冻结金额',
  `collect_money` decimal(15,2) unsigned DEFAULT '0.00' COMMENT '待收金额',
  `collect_capital` decimal(15,2) unsigned DEFAULT '0.00' COMMENT '待收本金',
  `collect_interest` decimal(15,2) unsigned DEFAULT '0.00' COMMENT '待收利息',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plant_user_money_log
-- ----------------------------
DROP TABLE IF EXISTS `plant_user_money_log`;
CREATE TABLE `plant_user_money_log` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id ',
  `affect_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '影响金额',
  `available_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '可用金额',
  `freeze_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `collect_money` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '待收金额',
  `collect_capital` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '待收本金',
  `collect_interest` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '待收利息',
  `affect_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '操作类型',
  `affect_time` datetime(3) DEFAULT NULL,
  `affect_ip` varchar(255) DEFAULT '0' COMMENT '操作ip',
  `detail_id` varchar(255) NOT NULL DEFAULT '0' COMMENT '操作对应的详细id',
  `affect_info` varchar(255) NOT NULL DEFAULT '0' COMMENT '操作备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
DROP TRIGGER IF EXISTS `left_count`;
DELIMITER ;;
CREATE TRIGGER `left_count` BEFORE UPDATE ON `plant_block` FOR EACH ROW set NEW.left_count = NEW.total_count - NEW.has_count
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_count`;
DELIMITER ;;
CREATE TRIGGER `update_count` BEFORE UPDATE ON `plant_mall_product` FOR EACH ROW set new.left_count = old.total_count - new.has_count
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `collection_money`;
DELIMITER ;;
CREATE TRIGGER `collection_money` BEFORE UPDATE ON `plant_user_money` FOR EACH ROW set NEW.collect_money = NEW.collect_capital + NEW.collect_interest
;;
DELIMITER ;
