package com.dio.api.biblioteca.config;

import conn.HerokuPostgresConnection;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
