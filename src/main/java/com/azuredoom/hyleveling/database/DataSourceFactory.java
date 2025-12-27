package com.azuredoom.hyleveling.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DataSourceFactory {

    private DataSourceFactory() {}

    public static HikariDataSource create(String jdbcUrl, String username, String password) {
        var cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(username);
        cfg.setPassword(password);

        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(1);

        return new HikariDataSource(cfg);
    }
}

