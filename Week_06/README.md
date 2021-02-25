## 第 12 节课作业实践
#### 2、（必做）：基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交DDL 的 SQL 文件到 Github（后面2周的作业依然要是用到这个表结构）。
- 用户表（ds_user_info）：
1. 与订单表关系：1->N
2. 表结构DDL：
```
CREATE TABLE `ds_user_info` (
  `u_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别：0-女；1-男',
  `birthday` int(11) DEFAULT NULL COMMENT '生日',
  `email` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '电话',
  `createtime` bigint(20) NOT NULL COMMENT '创建日期',
  `updatetime` bigint(20) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';
```
- 商品信息表（ds_product_info）：
1. 与商品库存表关系：1->1
2. 与订单明细表关系：1->N
3. 表结构DDL
```
CREATE TABLE `ds_product_info` (
  `p_id` bigint(20) NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '商品名称',
  `product_price` decimal(12,2) NOT NULL COMMENT '商品价格',
  `product_pic` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商品图片',
  `state` tinyint(4) NOT NULL COMMENT '商品状态：0-下架；1-在售',
  `createtime` bigint(20) NOT NULL COMMENT '创建时间',
  `updatetime` bigint(20) NOT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';
```
- 商品库存表（ds_product_inventory）：
1. 与商品信息表关系：1->1
2. 索引字段：商品id（p_id）
3. 表结构DDL
```
CREATE TABLE `ds_product_inventory` (
  `i_id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL COMMENT '商品id',
  `inventory` int(11) NOT NULL COMMENT '库存数量',
  `craetetime` bigint(20) NOT NULL COMMENT '创建时间',
  `updatetime` bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`i_id`),
  UNIQUE KEY `idx_p_id` (`p_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';
```
- 订单表（ds_order_info）
1. 与用户表关系：N->1
2. 与订单明细表关系：1->N
3. 索引字段：订单号（orderno），用户id（u_id）
4. 表结构DDL
```
CREATE TABLE `ds_order_info` (
  `o_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderno` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '订单号',
  `total_amount` decimal(12,2) NOT NULL COMMENT '订单总金额',
  `pay_amount` decimal(12,2) DEFAULT NULL COMMENT '实际支付金额',
  `u_id` bigint(20) NOT NULL COMMENT '所属用户id',
  `state` tinyint(1) NOT NULL COMMENT '订单状态',
  `pay_time` bigint(20) DEFAULT NULL COMMENT '支付时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '交易完成时间',
  `createtime` bigint(20) NOT NULL COMMENT '创建时间',
  `updatetime` bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`o_id`),
  UNIQUE KEY `idx_orderno` (`orderno`) USING BTREE,
  KEY `idx_u_id` (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
```
- 订单明细表（ds_order_item）
1. 与订单表关系：n->1
2. 与商品信息表关系：1->1
3. 索引字段：订单号（orderno）、商品id（p_id）
4. 表结构DDL
```
CREATE TABLE `ds_order_item` (
  `id_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderno` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '订单号',
  `p_id` bigint(20) NOT NULL COMMENT '商品id',
  `amount` decimal(12,2) NOT NULL COMMENT '订单明细金额',
  `createtime` bigint(20) NOT NULL COMMENT '创建日期',
  `updatetime` bigint(20) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id_id`),
  KEY `idx_orderno` (`orderno`) USING BTREE,
  KEY `idx_p_id` (`p_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细表';
```
