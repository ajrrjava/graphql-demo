package com.strateknia.database;

import javax.sql.DataSource;

public interface MigrationManager {

    void migrate(DataSource dataSource);
}
