package com.strateknia.graphql.database.jdbi.managers;

import com.strateknia.database.MigrationManager;
import com.strateknia.graphql.database.intf.DatabaseManager;
import com.strateknia.graphql.database.intf.PlanetManager;
import lombok.Builder;
import lombok.Getter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

@Builder
@Getter
public class JdbiManager implements DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(JdbiManager.class);

    private final Jdbi jdbi;
    private final DataSource dataSource;
    private final MigrationManager migrationManager;
    private final PlanetManager planetManager;

    @Override
    public void setup() {
        migrationManager.migrate(dataSource);
    }

    @Override
    public void close() {
        jdbi.useHandle(handle -> {
            try {
                handle.close();
            } catch (Exception e) {
                log.error("Caught: ", e);
            }
        });
    }

    public static class JdbiManagerBuilder {

        /**
         * Method used to create all Jdbi managers by supplying a datasource
         * @param dataSource Datasource
         */
        public JdbiManagerBuilder dataSource(DataSource dataSource) {
            Jdbi jdbi = Jdbi.create(dataSource);
            jdbi.installPlugin(new SqlObjectPlugin());
            this.dataSource = dataSource;
            planetManager = new PlanetsJdbi(jdbi);
            return this;
        }
    }
}
