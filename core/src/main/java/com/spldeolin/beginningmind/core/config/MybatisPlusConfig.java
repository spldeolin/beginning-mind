package com.spldeolin.beginningmind.core.config;

import java.time.LocalDateTime;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;

/**
 * Mybatis Plus配置
 *
 * 启用乐观锁、逻辑删除、分页，注册通用字段补全策略
 *
 * @author Deolin 2018/11/10
 */
@MapperScan(basePackages = "com.spldeolin.beginningmind", markerInterface = BaseMapper.class)
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private SnowFlakeService snowFlakeService;

    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 通用字段补全
     */
    @Bean
    public MetaObjectHandler commonFieldFillHandler() {
        return new CommonFieldFillHandler(snowFlakeService);
    }

    private static class CommonFieldFillHandler implements MetaObjectHandler {

        private SnowFlakeService snowFlakeService;

        private CommonFieldFillHandler(SnowFlakeService snowFlakeService) {
            this.snowFlakeService = snowFlakeService;
        }

        @Override
        public void insertFill(MetaObject metaObject) {
            // 使用雪花算法生成ID
            this.setFieldValByName("id", snowFlakeService.nextId(), metaObject);
            // 使用当前时间
            LocalDateTime now = LocalDateTime.now();
            this.setFieldValByName("insertedAt", now, metaObject);
            this.setFieldValByName("updatedAt", now, metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            // 使用当前时间
            LocalDateTime now = LocalDateTime.now();
            this.setFieldValByName("updatedAt", now, metaObject);
        }

    }


}
