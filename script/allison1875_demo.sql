CREATE TABLE `beginning_mind`.allison1875_demo
(
    id          bigint AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    delete_flag tinyint(1)   DEFAULT 0                 NOT NULL COMMENT '删除标记',
    hidden_memo varchar(255) DEFAULT ''                NOT NULL COMMENT '备注，对项目隐藏，仅在数据库中可见',
    inserted_at datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '插入数据的时间',
    updated_at  datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致',
    varchar1    varchar(255)                           NULL COMMENT '属性A',
    varchar2    varchar(2333)                          NULL COMMENT '属性B',
    char1       char(9)                                NULL COMMENT '属性C',
    char2       char(233)                              NULL COMMENT '属性D',
    text1       text                                   NULL COMMENT '属性E',
    text2       text                                   NULL COMMENT '属性F',
    tinyint1    tinyint                                NULL COMMENT '属性G',
    longtext2   longtext                               NULL COMMENT '属性H',
    longtext1   longtext                               NULL COMMENT '属性I',
    tinyint2    tinyint                                NULL COMMENT '属性J',
    `int1`      int                                    NULL COMMENT '属性K',
    `int2`      int                                    NULL COMMENT '属性L',
    boolean1    tinyint(1)                             NULL COMMENT '属性M',
    boolean2    tinyint(1)                             NULL COMMENT '属性N',
    datetime1   datetime                               NULL COMMENT '属性O',
    datetime2   datetime                               NULL COMMENT '属性P',
    decimal1    decimal                                NULL COMMENT '属性Q',
    decimal2    decimal                                NULL COMMENT '属性R'
)
    COMMENT '为Allison 1875准备的示例表';

