package com.strateknia.enfoqus;

import com.strateknia.graphql.database.DatasourceFactory;
import com.strateknia.graphql.database.intf.DatabaseManager;
import com.strateknia.graphql.database.jdbi.managers.JdbiManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class DatabaseTest {

    private static DatabaseManager databaseManager;
    private static Jdbi jdbi;

    private static final String databaseName = String.format("enfoqus-%s", UUID.randomUUID());

    @BeforeAll
    public static void initialize() {
        String env = null == System.getenv("ENV") ? "test" : System.getenv("ENV");
        Path propertiesFile = Paths.get("conf", env, "server.properties");

        Config config = ConfigFactory.parseFile(propertiesFile.toFile());
        DataSource ds = DatasourceFactory.builder().config(config).build().getHikariDataSource();

        jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.useHandle(handle -> {
            handle.createScript("CREATE SCHEMA " + databaseName);
        });

        databaseManager = JdbiManager.builder().dataSource(ds).build();

    }

    @AfterAll
    public static void terminate() {
        jdbi.useHandle(handle -> {
            handle.createScript("DROP SCHEMA " + databaseName);
        });
    }
}
