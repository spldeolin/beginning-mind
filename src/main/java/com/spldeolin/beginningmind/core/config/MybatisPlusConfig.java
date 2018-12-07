package com.spldeolin.beginningmind.core.config;

import java.time.LocalDateTime;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;

/**
 * @author Deolin 2018/11/10
 */
@MapperScan("com.spldeolin.beginningmind.core.dao")
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private SnowFlakeService snowFlakeService;

    /**
     * 启用乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 启用逻辑删除
     */
    @Bean
    public LogicSqlInjector logicSqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 启用分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

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
