package com.spldeolin.beginningmind.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.spldeolin.beginningmind.dao.bm2", sqlSessionTemplateRef = "bm2SessionTemplate")
public class DataSource2Config {

    @Bean(name = "bm2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.bm2")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "bm2SqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("bm2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/bm2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "bm2TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("bm2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "bm2SessionTemplate")
    public SqlSessionTemplate test1SqlSessionTemplate(
            @Qualifier("bm2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
