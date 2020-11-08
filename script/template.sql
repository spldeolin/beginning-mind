CREATE TABLE `beginning_mind`.__template
(
    id          bigint UNSIGNED AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    delete_flag tinyint(1)   DEFAULT 0                 NOT NULL COMMENT '删除标记',
    hidden_memo varchar(255) DEFAULT ''                NOT NULL COMMENT '备注，对项目隐藏，仅在数据库中可见',
    inserted_at datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '插入数据的时间',
    updated_at  datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致'
)
    COMMENT '建表语句';

INSERT INTO `beginning_mind`.__template (id, delete_flag, hidden_memo, inserted_at, updated_at) VALUES (1, 1, '', '2018-11-12 13:24:43', '2018-11-26 07:20:50');