package ru.tdd.backend.controller.runners;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class FlyWayRunner implements CommandLineRunner {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        DataSource dataSource = DataSourceBuilder
                .create()
                .url(url)
                .password(password)
                .username(username)
                .driverClassName(driver)
                .build();

        Flyway.configure()
                .dataSource(dataSource)
                .baselineVersion("0")
                .baselineOnMigrate(true)
                .load()
                .migrate();
    }
}
