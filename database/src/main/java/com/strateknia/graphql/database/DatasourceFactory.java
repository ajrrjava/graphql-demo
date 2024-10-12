package com.strateknia.graphql.database;

import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Builder;

import javax.sql.DataSource;

@Builder
public class DatasourceFactory {

    private final Config config;

    public DataSource getHikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(config.getString("db.url"));
        hikariConfig.setUsername(config.getString("db.user"));
        hikariConfig.setPassword(config.getString("db.password"));
        hikariConfig.setConnectionTimeout(config.getInt("db.loginTimeout"));
        hikariConfig.addDataSourceProperty("databaseName", config.getString("db.databaseName"));

        return new HikariDataSource(hikariConfig);
    }


}
