CREATE TABLE`tb_order` (
                           `id`bigint(20) unsignedNOTNULL AUTO_INCREMENT COMMENT'主键ID',
                           `order_code`varchar(128) COLLATE utf8mb4_bin DEFAULTNULLCOMMENT'订单编码',
                           `status`smallint(3) DEFAULTNULLCOMMENT'订单状态',
                           `name`varchar(64) COLLATE utf8mb4_bin DEFAULTNULLCOMMENT'订单名称',
                           `price`decimal(12,2) DEFAULTNULLCOMMENT'价格',
                           `delete_flag`tinyint(2) NOTNULLDEFAULT'0'COMMENT'删除标记，0未删除  1已删除',
                           `create_time`timestampNOTNULLDEFAULTCURRENT_TIMESTAMPONUPDATECURRENT_TIMESTAMPCOMMENT'创建时间',
                           `update_time`timestampNOTNULLDEFAULT'0000-00-00 00:00:00'COMMENT'更新时间',
                           `create_user_code`varchar(32) COLLATE utf8mb4_bin DEFAULTNULLCOMMENT'创建人',
                           `update_user_code`varchar(32) COLLATE utf8mb4_bin DEFAULTNULLCOMMENT'更新人',
                           `version`int(11) NOTNULLDEFAULT'0'COMMENT'版本号',
                           `remark`varchar(64) COLLATE utf8mb4_bin DEFAULTNULLCOMMENT'备注',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6DEFAULTCHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单表';

/*Data for the table `tb_order` */

insertinto`tb_order`(`id`,`order_code`,`status`,`name`,`price`,`delete_flag`,`create_time`,`update_time`,`create_user_code`,`update_user_code`,`version`,`remark`) values
    (2,'A111',1,'A','22.00',0,'2022-10-15 16:14:11','2022-10-02 21:29:14','zhangsan','zhangsan',0,NULL),
    (3,'A111',1,'订单A','22.00',0,'2022-10-02 21:53:13','2022-10-02 21:29:14','zhangsan','zhangsan',0,NULL),
    (4,'A111',1,'订单A','22.00',0,'2022-10-02 21:53:13','2022-10-02 21:29:14','zhangsan','zhangsan',0,NULL),
    (5,'A111',1,'订单A','22.00',0,'2022-10-03 09:08:30','2022-10-02 21:29:14','zhangsan','zhangsan',0,NULL);
