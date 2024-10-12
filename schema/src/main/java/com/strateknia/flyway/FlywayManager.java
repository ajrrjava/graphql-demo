package com.strateknia.flyway;

import com.strateknia.database.MigrationManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;


public class FlywayManager implements MigrationManager {
    private static final Logger log = LoggerFactory.getLogger(FlywayManager.class);

    private final String[] schemas = new String[]{"PRODUCTION"};
    private final Location[] locations = new Location[]{new Location("classpath:db/operations")};
    private String test;

    @Override
    public void migrate(DataSource dataSource) {
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(dataSource);
        conf.setSchemas(schemas);
        conf.setLocations(locations);

        log.debug("Migrating database");

        Flyway flyway = new Flyway(conf);
        flyway.migrate();

        log.debug("Migration completed successfully!");
    }
}
