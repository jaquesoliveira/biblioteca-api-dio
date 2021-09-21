package com.dio.api.biblioteca.config;


import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.heroku.postgres.connection.HerokuPostgresConnection;

import lombok.Getter;

@Getter
@Configuration
@ConfigurationProperties("spring.datasource")
public class BDHerokuConfig {

    private String url;
    private String driverClassName;
    private String userName;
    private String password;

    @Bean
    public DataSource getDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(HerokuPostgresConnection.DRIVER_CLASS_NAME);
        dataSourceBuilder.url(HerokuPostgresConnection.URL);
        dataSourceBuilder.username(HerokuPostgresConnection.USER_NAME);
        dataSourceBuilder.password(HerokuPostgresConnection.PASSWORD);
        return dataSourceBuilder.build();
    }
}
