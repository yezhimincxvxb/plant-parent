CREATE TABLE `plant_user_message_tpl` (
  `action_code` varchar(255) NOT NULL DEFAULT '' COMMENT '模版方法值 ',
  `message_title` varchar(255) NOT NULL DEFAULT '',
  `message_tpl` varchar(255) NOT NULL DEFAULT '' COMMENT '消息模板',
  `is_open` tinyint(4) NOT NULL,
  PRIMARY KEY (`action_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `plant_user_message`
ADD COLUMN `title`  varchar(255) NOT NULL DEFAULT '' AFTER `id`;

ALTER TABLE `plant_seed_order_detail`
ADD COLUMN `pay_time`  datetime NULL DEFAULT NULL COMMENT '支付时间' AFTER `add_time`;


ALTER TABLE `plant_seed_order_detail`
ADD COLUMN `seq_no`  varchar(255) NULL DEFAULT NULL COMMENT '支付订单短信流水号' AFTER `pay_time`,
ADD COLUMN `account_pay_amount`  decimal(15,2) NOT NULL DEFAULT 0 COMMENT '余额支付金额' AFTER `seq_no`,
ADD COLUMN `car_pay_amount`  decimal(15,2) NOT NULL DEFAULT 0 COMMENT '卡支付金额' AFTER `account_pay_amount`;


ALTER TABLE `plant_seed_order_detail`
ADD COLUMN `close_time`  datetime NULL DEFAULT NULL COMMENT '关单时间' AFTER `car_pay_amount`,
ADD COLUMN `state`  tinyint(1) NULL DEFAULT 0 COMMENT '[0待支付，1已支付，2已取消]' AFTER `close_time`;


ALTER TABLE `plant_seed_order`
DROP COLUMN `order_number`,
DROP COLUMN `state`,
DROP COLUMN `add_time`,
CHANGE COLUMN `seed_id` `seed_type`  int(11) NOT NULL DEFAULT 0 COMMENT '种子类型' AFTER `id`;


ALTER TABLE `plant_user_bank`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT FIRST ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);


ALTER TABLE `plant_seed_type`
DROP COLUMN `parent_id`,
ADD COLUMN `plant_type`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '种植方式' AFTER `is_delete`,
ADD COLUMN `reap_type`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '结算方式' AFTER `plant_type`;

ALTER TABLE `plant_user_bank`
MODIFY COLUMN `bank_id`  varchar(255) NOT NULL DEFAULT '' COMMENT '所属银行' AFTER `bank_number`,
ADD COLUMN `card_type`  varchar(255) NOT NULL DEFAULT '' COMMENT '卡类型，1借记，2信用，空为未知' AFTER `bank_id`;

ALTER TABLE `plant_reap`
DROP COLUMN `seed_id`;


ALTER TABLE `plant_reap`
ADD COLUMN `seed_type`  int(11) NOT NULL DEFAULT 0 COMMENT '菌包类型' AFTER `order_number`;

ALTER TABLE `plant_block`
ADD COLUMN `pic_url`  varchar(255) NOT NULL DEFAULT '' AFTER `is_delete`,
ADD COLUMN `thumb_pic_url`  varchar(255) NOT NULL DEFAULT '' AFTER `pic_url`;


ALTER TABLE `plant_seed_type`
ADD COLUMN `pic_url`  varchar(255) NOT NULL COMMENT '类型背景图' AFTER `reap_type`,
ADD COLUMN `thumb_pic_url`  varchar(255) NOT NULL DEFAULT '' COMMENT '背景缩略图' AFTER `pic_url`;

ALTER TABLE `plant_money_withdraw`
MODIFY COLUMN `bank_number`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '到账银行卡号' AFTER `verify_mark`;

ALTER TABLE `plant_activity`
MODIFY COLUMN `content`  text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '活动内容' AFTER `pic_url`,
ADD COLUMN `link_url`  varchar(255) NULL DEFAULT NULL COMMENT '跳转链接' AFTER `pic_url`;

ALTER TABLE `plant_money_withdraw`
ADD COLUMN `seq_no`  varchar(255) NULL DEFAULT '' COMMENT '提现短信流水号' AFTER `state`;


ALTER TABLE `plant_money_withdraw`
ADD COLUMN `success_time`  datetime NULL COMMENT '成功时间' AFTER `state`;

ALTER TABLE `plant_payment_info`
ADD COLUMN `add_time`  datetime NOT NULL COMMENT '添加时间' AFTER `state`;

DROP TABLE  `plant_fertilizer`;
CREATE TABLE `plant_fertilizer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type_id` int(1) NOT NULL COMMENT '券类型',
  `amount_min` decimal(15,2) DEFAULT NULL COMMENT '券金额',
  `amount_max` decimal(15,2) DEFAULT NULL,
  `trigger_use_event` varchar(255) DEFAULT NULL COMMENT '券使用事件',
  `trigger_get_event` varchar(255) DEFAULT NULL COMMENT '券获取事件',
  `count` int(11) NOT NULL COMMENT '券数量',
  `per_count` int(11) NOT NULL DEFAULT '1' COMMENT '每次触发发多少张券给用户',
  `user_level` int(2) DEFAULT NULL COMMENT '券可使用的用户等级',
  `full_reduce_amount` decimal(15,2) DEFAULT NULL COMMENT '满减金额',
  `need_plant_amount` decimal(15,2) DEFAULT NULL COMMENT '种植金额',
  `adv_sale_rate` decimal(15,2) DEFAULT NULL COMMENT '优卖增息利率',
  `start_time` datetime NOT NULL COMMENT '券起始时间',
  `expire_time` datetime NOT NULL COMMENT '有效期',
  `use_no_limit` int(1) DEFAULT NULL COMMENT '是否无使用限制[1是，0否]',
  `use_in_seed_type` int(11) DEFAULT NULL COMMENT '在指定菌包种类使用',
  `use_in_block` int(11) DEFAULT NULL COMMENT '在指定大棚',
  `use_in_product` int(11) DEFAULT NULL COMMENT '在指定商品',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE  `plant_user_fertilizer`;
CREATE TABLE `plant_user_fertilizer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `fertilizer_id` int(11) NOT NULL DEFAULT '0' COMMENT '券id',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '[0未使用，1已使用，2已过期]',
  `use_order_number` varchar(255) DEFAULT NULL COMMENT '使用对应的流水号',
  `add_time` datetime DEFAULT NULL COMMENT '发放时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



ALTER TABLE `plant_mall_order_detail`
DROP COLUMN `fee_amount`;

ALTER TABLE `plant_mall_order`
CHANGE COLUMN `pay_sms_seq_no` `car_pay_amount`  decimal(15,2) NULL DEFAULT NULL COMMENT '卡支付金额' AFTER `cancel_reason`,
ADD COLUMN `account_pay_amount`  decimal(15,2) NULL COMMENT '余额支付金额' AFTER `car_pay_amount`,
ADD COLUMN `seq_no`  varchar(255) NULL COMMENT '支付短信流水' AFTER `account_pay_amount`;


ALTER TABLE `plant_mall_order`
ADD COLUMN `notice_time`  datetime NULL COMMENT '提醒时间' AFTER `is_notice`;

ALTER TABLE `plant_user_address`
ADD COLUMN `is_delete`  tinyint(1) NOT NULL DEFAULT 0  COMMENT '是否已删除' AFTER `add_time`;

ALTER TABLE `plant_seed_order_detail`
ADD COLUMN `reduce_pay_amount`  decimal(15,2) NULL DEFAULT 0 COMMENT '优惠金额' AFTER `car_pay_amount`;

ALTER TABLE `plant_mall_order`
ADD COLUMN `reduce_pay_amount`  decimal(15,2) NULL DEFAULT 0 COMMENT '优惠金额' AFTER `account_pay_amount`;

ALTER TABLE `plant_fertilizer`
ADD COLUMN `start_time`  datetime NOT NULL COMMENT '券起始时间' AFTER `adv_sale_rate`,
ADD COLUMN `use_in_seed_type`  int NULL COMMENT '在指定菌包种类使用' AFTER `use_no_limit`;

ALTER TABLE `plant_mall_order`
MODIFY COLUMN `buy_amount`  decimal(15,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '购买总价' AFTER `user_id`,
MODIFY COLUMN `fee_amount`  decimal(15,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '订单快递费' AFTER `buy_mark`,
MODIFY COLUMN `car_pay_amount`  decimal(15,2) UNSIGNED NULL DEFAULT NULL COMMENT '卡支付金额' AFTER `cancel_reason`,
MODIFY COLUMN `account_pay_amount`  decimal(15,2) UNSIGNED NULL DEFAULT NULL COMMENT '余额支付金额' AFTER `car_pay_amount`,
MODIFY COLUMN `reduce_pay_amount`  decimal(15,2) UNSIGNED NULL DEFAULT 0.00 COMMENT '优惠金额' AFTER `account_pay_amount`;

ALTER TABLE `plant_mall_order_detail`
MODIFY COLUMN `buy_count`  int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '购买数量' AFTER `product_id`,
MODIFY COLUMN `buy_amount`  decimal(15,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '购买总价' AFTER `buy_count`;


ALTER TABLE `plant_user_fertilizer`
ADD COLUMN `add_time`  datetime NULL COMMENT '发放时间' AFTER `use_order_number`;


CREATE TABLE `plant_apk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `download_url` varchar(255) NOT NULL DEFAULT '' COMMENT '下载链接',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否上架[0否，1是]',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



ALTER TABLE `moguying`.`plant_user_bank`
ADD COLUMN `bank_phone` varchar(11) NOT NULL DEFAULT '' COMMENT '银行预留手机号' AFTER `bank_number`;

ALTER TABLE `moguying`.`plant_money_withdraw`
ADD COLUMN `bank_phone` varchar(11) NULL DEFAULT '' COMMENT '到账银行预留手机号' AFTER `bank_number`;

ALTER TABLE `moguying`.`plant_article_type`
ADD COLUMN `order_number` int(255) NULL COMMENT '排序号' AFTER `is_delete`;

ALTER TABLE `moguying`.`plant_mall_order`
ADD UNIQUE INDEX `order_number`(`order_number`) USING BTREE,
ADD UNIQUE INDEX `user_id`(`user_id`) USING BTREE;


ALTER TABLE `moguying`.`plant_fertilizer`
ADD COLUMN `expire_days` int(11) NULL COMMENT '有效天数' AFTER `use_in_product`,
ADD COLUMN `is_single_trigger` int(1) NULL COMMENT '是否单次触发[0否，1是]' AFTER `expire_days`;

ALTER TABLE `moguying`.`plant_user_fertilizer`
ADD COLUMN `start_time` datetime(0) NULL COMMENT '券有效时间' AFTER `add_time`,
ADD COLUMN `end_time` datetime(0) NULL COMMENT '券有效时间' AFTER `start_time`;

ALTER TABLE `moguying`.`plant_fertilizer`
MODIFY COLUMN `start_time` datetime(0) NULL COMMENT '券起始时间' AFTER `adv_sale_rate`,
MODIFY COLUMN `expire_time` datetime(0) NULL COMMENT '有效期' AFTER `start_time`;

/******2019-09-10***/

CREATE TABLE `plant_admin_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '后台用户id',
  `message` varchar(255) DEFAULT NULL COMMENT '信息',
  `download_url` varchar(255) DEFAULT NULL COMMENT '下载地址',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '0未审核，1已审核，2已读',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `plant_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(255) NOT NULL COMMENT '后台用户id',
  `action_code` varchar(255) NOT NULL COMMENT '操作代码',
  `action_param` varchar(255) NOT NULL COMMENT '操作参数',
  `add_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `plant_admin_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_code` varchar(255) NOT NULL,
  `action_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `moguying`.`plant_seed_content`
CHANGE COLUMN `seed_id` `seed_type` int(11) NOT NULL DEFAULT 0 COMMENT '种子类型id' FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`seed_type`) USING BTREE;

CREATE TABLE `plant_reap_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) DEFAULT NULL,
  `invite_uid` int(11) DEFAULT NULL,
  `reap_id` int(11) DEFAULT NULL,
  `fee_amount` decimal(15,2) DEFAULT NULL,
  `is_first` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `moguying`.`plant_admin_user`
ADD COLUMN `bind_id` int(11) NULL COMMENT '绑定前端用户id' AFTER `role_id`;

ALTER TABLE `moguying`.`plant_admin_user`
ADD COLUMN `group_id` int(0) NULL COMMENT '分组id' AFTER `role_id`;


ALTER TABLE `moguying`.`plant_seed_days`
ADD COLUMN `first_plant_rate` decimal(15, 2) NULL COMMENT '首次种植' AFTER `mark`,
ADD COLUMN `plant_rate` decimal(15, 2) NULL AFTER `first_plant_rate`;


CREATE TABLE `plant_express_com` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司id',
  `com_name` varchar(255) NOT NULL COMMENT '公司名称',
  `com_code` varchar(255) NOT NULL COMMENT '公司编码',
	`com_phone` varchar(255) NOT NULL COMMENT '公司电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

ALTER TABLE `moguying`.`plant_mall_order`
ADD COLUMN `express_com_code` varchar(255) DEFAULT NULL COMMENT '快递公司编码' AFTER `express_order_number`;


ALTER TABLE `moguying`.`plant_fertilizer`
ADD COLUMN `cash_return_rate` decimal(15, 2) NULL COMMENT '返现比例' AFTER `adv_sale_rate`,
ADD COLUMN `cash_amount` decimal(15, 2) NULL COMMENT '固定金额' AFTER `cash_return_rate`;


ALTER TABLE `moguying`.`plant_fertilizer`
DROP COLUMN `full_reduce_amount`,
DROP COLUMN `need_plant_amount`,
DROP COLUMN `adv_sale_rate`,
DROP COLUMN `cash_return_rate`,
DROP COLUMN `cash_amount`,
ADD COLUMN `fertilizer_amount` decimal(15, 2) NULL COMMENT '券面额' AFTER `user_level`,
ADD COLUMN `fertilizer_amount_is_rate` int(1) NULL COMMENT '券面额是否为比率' AFTER `fertilizer_amount`;


ALTER TABLE `moguying`.`plant_user_fertilizer`
ADD COLUMN `fertilizer_amount` decimal(15, 2) NULL COMMENT '券金额' AFTER `fertilizer_id`;


/***卖场中心*****/
CREATE TABLE `plant_sale_coin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `affect_coin` int(11) NOT NULL,
  `affect_type` int(11) NOT NULL,
  `affect_detail_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/**权限相关**/

CREATE TABLE `plant_admin_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `moguying`.`plant_user`
ADD COLUMN `is_channel` int(1) NOT NULL DEFAULT 0 COMMENT '是否为渠道商[0否，1是]' AFTER `payment_state`;

ALTER TABLE `moguying`.`plant_seed_type`
ADD COLUMN `exchange_num` int(11) NOT NULL DEFAULT '0' COMMENT '兑换数' AFTER `thumb_pic_url`;

ALTER TABLE `moguying`.`plant_mall_product`
ADD COLUMN `consume_coins` int(11) NOT NULL DEFAULT '0' COMMENT '消耗蘑菇币' AFTER `delivery_info`;

ALTER TABLE `moguying`.`plant_reap`
ADD COLUMN `exchange_time` datetime DEFAULT NULL COMMENT '兑换时间' AFTER `sale_time`;

ALTER TABLE `moguying`.`plant_fertilizer`
ADD COLUMN `coin_fertilizer` int(11) NOT NULL DEFAULT '0' COMMENT '蘑菇币兑换券';

ALTER TABLE `moguying`.`plant_mall_order_detail`
ADD COLUMN `buy_coins` int(11) NOT NULL DEFAULT '0' COMMENT '订单蘑菇币';

ALTER TABLE `moguying`.`plant_mall_order`
ADD COLUMN `total_coins` int(11) NOT NULL DEFAULT '0' COMMENT '蘑菇币总数' AFTER `buy_amount`;

CREATE TABLE `plant_reap_fee_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invite_uid` int(11) NOT NULL DEFAULT '0' COMMENT '渠道id',
  `first_plant_rate` decimal(15,3) NOT NULL COMMENT '首种费用结算比率',
  `plant_rate` decimal(15,3) NOT NULL COMMENT '普种费用结算比率',
  `seed_type` int(11) NOT NULL COMMENT '菌包种类',
  `is_delete` tinyint(1) NOT NULL COMMENT '1已删除，0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/***砍价详情*****/
CREATE TABLE `plant_bargain_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `product_count` int(11) NOT NULL COMMENT '产品数量',
  `total_amount` decimal(15,2) NOT NULL COMMENT '产品总价',
  `bargain_amount` decimal(15,2) NOT NULL COMMENT '已砍价格',
  `left_amount` decimal(15,2) NOT NULL COMMENT '剩余价格',
  `total_count` int(11) NOT NULL COMMENT '需砍总数',
  `bargain_count` int(11) NOT NULL COMMENT '已砍次数',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否关单[0 未关单；1 已关单]',
  `add_time` datetime NOT NULL COMMENT '生成时间',
  `bargain_time` datetime NOT NULL COMMENT '帮砍时间',
  `close_time` datetime NOT NULL COMMENT '关单时间',
  `order_number` varchar(50) NOT NULL DEFAULT '' COMMENT '订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***帮砍日志*****/
CREATE TABLE `plant_bargain_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `share_id` int(11) NOT NULL COMMENT '分享者id',
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `detail_id` int(11) NOT NULL COMMENT '详情id',
  `help_amount` decimal(15,2) NOT NULL COMMENT '帮砍价格',
  `message` varchar(255) NOT NULL COMMENT '备注信息',
  `help_time` datetime NOT NULL COMMENT '帮砍时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***该产品需砍多少次*****/
ALTER TABLE `moguying`.`plant_mall_product`
ADD COLUMN `bargain_count` int(11) NOT NULL DEFAULT '0' COMMENT '砍价次数' AFTER `consume_coins`;


CREATE TABLE `plant_mall_product_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/***该产品砍价可得多少份*****/
ALTER TABLE `moguying`.`plant_mall_product`
ADD COLUMN `bargain_number` int(11) NOT NULL DEFAULT '0' COMMENT '砍价份数' AFTER `bargain_count`;

/***该产品砍价限量多少份*****/
ALTER TABLE `moguying`.`plant_mall_product`
ADD COLUMN `bargain_limit` int(11) NOT NULL DEFAULT '0' COMMENT '砍价限量' AFTER `bargain_number`;

ALTER TABLE `moguying`.`plant_mall_product`
ADD COLUMN `type_id` int(0) NULL COMMENT '商品类别' AFTER `id`;

/***该产品是否限量*****/
ALTER TABLE `moguying`.`plant_mall_product`
ADD COLUMN  `is_limit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否限量[0否，1是]' AFTER `bargain_limit`;


/** 是否新手适用 **/
ALTER TABLE `moguying`.`plant_seed_type`
ADD COLUMN `is_for_new` tinyint(1) NOT NULL DEFAULT 0 AFTER `exchange_num`;

/** 每种菌类产量 **/
ALTER TABLE `moguying`.`plant_seed_type`
ADD COLUMN `per_weigh` decimal(15, 2) NOT NULL COMMENT '每份产量重' AFTER `is_for_new`;

ALTER TABLE `moguying`.`plant_reap`
ADD COLUMN `plant_weigh` decimal(15, 2) NOT NULL COMMENT '出菇量(单位g)' AFTER `exchange_time`;

/** 砍价详情表，新增砍价口令 **/
ALTER TABLE `moguying`.`plant_bargain_detail`
ADD COLUMN `symbol` varchar(50) NOT NULL COMMENT '砍价口令' AFTER `close_time`;

/***帮砍日志*****/
CREATE TABLE `plant_bargain_rate` (
  `product_id` int(11) NOT NULL COMMENT '用户id',
  `own_rate` int(11) NOT NULL COMMENT '本人砍价系数',
  `new_rate` int(11) NOT NULL COMMENT '新用户系数',
  `old_rate` int(11) NOT NULL COMMENT '老用户系数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `moguying`.`plant_seed_type`
ADD COLUMN `group_id` int(0) NOT NULL COMMENT '种类分组' AFTER `class_name`;

CREATE TABLE `plant_seed_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分组名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


ALTER TABLE `moguying`.`plant_seed_type`
MODIFY COLUMN `is_for_new` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否新手体验' AFTER `exchange_num`,
ADD COLUMN `ex_mall_product` int(11) NULL COMMENT '菌包成品可兑换的商城物品id' AFTER `is_for_new`;

CREATE TABLE `plant_reap_weigh` (
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `total_weigh` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '总产量',
  `has_ex_weigh` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '已兑换产量',
  `available_weigh` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '可兑换产量',
  `has_profit` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '已领取收益',
  `available_profit` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '可领取收益',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


/** 商城订单表，新增券id **/
ALTER TABLE `moguying`.`plant_mall_order`
ADD COLUMN `fertilizer_id` int(11) DEFAULT NULL COMMENT '使用券id' AFTER `state`;