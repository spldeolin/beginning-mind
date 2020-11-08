CREATE TABLE `beginning_mind`.security_access_token
(
    id             bigint UNSIGNED AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    delete_flag    tinyint(1)   DEFAULT 0                  NOT NULL COMMENT '删除标记',
    hidden_memo    varchar(255) DEFAULT ''                 NOT NULL COMMENT '备注，对项目隐藏，仅在数据库中可见',
    inserted_at    datetime     DEFAULT CURRENT_TIMESTAMP  NOT NULL COMMENT '插入数据的时间',
    updated_at     datetime     DEFAULT CURRENT_TIMESTAMP  NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致',
    mapping_method varchar(255) COLLATE utf8mb4_general_ci NULL COMMENT '映射方法（HTTP方法，e.g.: POST）',
    mapping_path   varchar(255) COLLATE utf8mb4_general_ci NULL COMMENT '映射路由（控制器路由+方法路由，e.g.: /user/create）',
    token          varchar(30) COLLATE utf8mb4_general_ci  NULL COMMENT 'TOKEN（30位随机大小写字母+数字）'
)
    COMMENT '代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值';

INSERT INTO `beginning_mind`.security_access_token (id, delete_flag, hidden_memo, inserted_at, updated_at, mapping_method, mapping_path, token) VALUES (1, 0, '', '2019-02-23 13:10:00', '2019-06-26 17:14:54', 'post', '/test/requestTrackReport', 'ss');