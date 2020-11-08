CREATE TABLE `beginning_mind`.user
(
    id            bigint UNSIGNED AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    delete_flag   tinyint(1)   DEFAULT 0                 NOT NULL COMMENT '删除标记',
    hidden_memo   varchar(255) DEFAULT ''                NOT NULL COMMENT '备注，对项目隐藏，仅在数据库中可见',
    inserted_at   datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '插入数据的时间',
    updated_at    datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致',
    serial_number varchar(255)                           NULL COMMENT '用户编号',
    name          varchar(255)                           NULL COMMENT '名字',
    mobile        char(20)                               NULL COMMENT '手机号',
    email         varchar(255)                           NULL COMMENT 'E-mail',
    password      char(128)                              NULL COMMENT '密码',
    salt          char(32)                               NULL COMMENT '盐',
    enable_sign   tinyint UNSIGNED                       NULL COMMENT '能否登录'
)
    COMMENT '用户';

INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (1, 0, '', '1970-01-01 00:00:00', '2019-06-26 17:16:15', '', 'adam', 'admin', '', '660afa204c78e8bdebc5323e7ff9168aec159e12ce56250b6c77c220fd20d8e89a6aa382de171135a0b7aeaa28d9a0c2ca5658b5ad12fc42ad4fec6568aa0db2', 'w!-Fz?bbdWQ>[VZNu9{&jG''Ort0s/i"C', 1);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (2, 0, '', '2018-08-07 08:33:52', '2019-06-26 17:16:15', '', '1', '', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (3, 0, '', '2018-08-07 08:34:34', '2019-06-26 17:16:15', '', '1', '', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (285221975101440, 0, '', '2018-11-13 08:20:56', '2019-06-26 17:16:15', '', '乐观锁', '1', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (287884624138240, 0, '', '2018-11-13 08:31:34', '2019-06-26 17:16:15', '', '汉字', '1', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (288782687539200, 0, '', '2018-11-13 08:35:08', '2019-06-26 17:16:15', '', '汉字', '1', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (289120698109952, 0, '', '2018-11-13 08:36:29', '2019-06-26 17:16:15', '', '汉字', '1', '1', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (290956356227072, 0, '', '2018-11-13 08:43:46', '2019-06-26 17:16:15', '', '汉字', '1', '2', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (303135042179072, 0, '', '2018-11-13 09:32:10', '2019-06-26 17:16:15', '', '汉字', '1', '2', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (400214552875008, 0, '', '2018-11-13 15:57:56', '2019-06-26 17:16:15', '', 'a1a@a', '0', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (400214720647168, 1, '', '2018-11-13 15:57:56', '2019-06-26 17:16:15', '', '批量1', '1111', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (400214720647169, 0, '', '2018-11-13 15:57:56', '2019-06-26 17:16:15', '', '批量2', '2', 'aa@a', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (400214724841472, 1, '', '2018-11-13 15:57:56', '2019-06-26 17:16:15', '', '批量3', '3', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (400214724841473, 0, '', '2018-11-13 15:57:56', '2019-06-26 17:16:15', '', '批量4', '4', '', '', '', 0);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (8999000162308096, 0, '', '2018-12-07 09:26:26', '2019-06-26 17:16:15', null, '汉字', '1', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (36286656290099200, 1, '', '2019-02-20 16:37:50', '2019-06-26 17:16:15', null, '批量0', '0', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (36286656566923264, 1, '', '2019-02-20 16:37:50', '2019-06-26 17:16:15', null, '批量1', '1', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (36286656571117568, 1, '', '2019-02-20 16:37:50', '2019-06-26 17:16:15', null, '批量2', '2', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (36286656575311872, 0, '', '2019-02-20 16:37:50', '2019-06-26 17:16:15', null, '批量3', '3', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (36286656579506176, 0, '', '2019-02-20 16:37:50', '2019-06-26 17:16:15', null, '批量4', '4', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (72171958074216448, 0, '', '2019-05-30 17:13:13', '2019-06-26 17:16:15', null, '汉字', '1', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (72172181794197504, 0, '', '2019-05-30 17:14:06', '2019-06-26 17:16:15', null, '批量0', '0', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (72172181831946240, 0, '', '2019-05-30 17:14:06', '2019-06-26 17:16:15', null, '批量1', '1', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (72172181836140544, 0, '', '2019-05-30 17:14:06', '2019-06-26 17:16:15', null, '批量2', '2', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (72172181840334848, 0, '', '2019-05-30 17:14:06', '2019-06-26 17:16:15', null, '批量3', '3', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (72172181848723456, 0, '', '2019-05-30 17:14:06', '2019-06-26 17:16:15', null, '批量4', '4', null, null, null, null);
INSERT INTO `beginning_mind`.user (id, delete_flag, hidden_memo, inserted_at, updated_at, serial_number, name, mobile, email, password, salt, enable_sign) VALUES (262674499420426240, 0, '', '2020-11-06 09:42:18', '2020-11-06 09:42:18', '262674499277819904', 'xBjond5Mo', 'PotkC6A', 'fmmfGv', 'fb59420cac6ce7adae2fb8a79f8cc9bb81789cdfcb16f9e778fce4cb60b67920697c872be731ceb08822d3187eba9de3bf3f25a2642bca2d0021a2fa776caedb', '&J!USLIU7x&1]pO0jSwzLSiSvz`u(:1r', 1);