CREATE TABLE `beginning_mind`.permission
(
    id                 bigint UNSIGNED AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    delete_flag        tinyint(1)   DEFAULT 0                 NOT NULL COMMENT '删除标记',
    hidden_memo        varchar(255) DEFAULT ''                NOT NULL COMMENT '备注，对项目隐藏，仅在数据库中可见',
    inserted_at        datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '插入数据的时间',
    updated_at         datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致',
    mapping_method     varchar(255)                           NULL COMMENT '映射方法（HTTP方法，e.g.: POST）',
    mapping_path       varchar(255)                           NULL COMMENT '映射路由（控制器路由+方法路由，e.g.: /user/create）',
    display            varchar(255)                           NULL COMMENT '用于展示的名称',
    is_granted_for_all tinyint UNSIGNED                       NULL COMMENT '是否所有用户都拥有本权限',
    is_forbidden       tinyint UNSIGNED                       NULL COMMENT '是否所有用户都不拥有本权限'
)
    COMMENT '权限';

