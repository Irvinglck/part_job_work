package com.lck.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    @Bean(destroyMethod = "", name = "EmbeddedDataSource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        //sqlite文件路径，可以是绝对路径也可以是相对路径
        dataSourceBuilder.url("jdbc:sqlite:D:\\\\gogledownload\\sqlite\\hospital_entry.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        return  dataSourceBuilder.build();
    }
}