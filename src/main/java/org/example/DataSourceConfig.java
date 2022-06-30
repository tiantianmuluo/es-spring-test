package org.example;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "druid.aa")
    public DataSource dataSource1() {
//        return DruidDataSourceBuilder.create().build();
        return null;
    }

    @Bean
    @ConfigurationProperties(prefix = "druid.bb")
    public DataSource dataSource2() {
//        return DruidDataSourceBuilder.create().build();
        return null;
    }

    @Bean
    public Interceptor dynamicDataSourcePlugin() {
        return  new DynamicDataSourcePlugin();
    }
}
