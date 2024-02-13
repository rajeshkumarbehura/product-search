package com.sellde.reward.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Database migration
 */
@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.url}")
    private String url;

    @Value("${spring.flyway.user}")
    private String user;

    @Value("${spring.flyway.password}")
    private String password;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .locations("db/migration")
                .sqlMigrationPrefix("m")
                .sqlMigrationSeparator("_")
                .table("migration")
                .baselineOnMigrate(true)
                .dataSource(url, user,password)
                .load();
    }

}