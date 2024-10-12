package com.strateknia.graphql;

import com.strateknia.database.MigrationManager;
import com.strateknia.flyway.FlywayManager;
import com.strateknia.graphql.common.utils.ConfigManager;
import com.strateknia.graphql.database.DatasourceFactory;
import com.strateknia.graphql.database.intf.DatabaseManager;
import com.strateknia.graphql.database.jdbi.managers.JdbiManager;
import com.strateknia.graphql.jetty.RestServerJetty;
import com.strateknia.server.AbstractApplicationServer;
import com.strateknia.server.RestServer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@SuperBuilder
public class GraphQLServer extends AbstractApplicationServer {
    private static final Logger log = LoggerFactory.getLogger(GraphQLServer.class);

    public static void main(String[] args) throws IOException {
        String env = null == System.getenv("ENV") ? "dev" : System.getenv("ENV");
        Path propertiesFile = Paths.get("conf", env, "server.properties");

        Config config = ConfigFactory.parseFile(propertiesFile.toFile());
        ConfigManager configManager = ConfigManager.builder().config(config).build();
        DataSource ds = DatasourceFactory.builder().config(config).build().getHikariDataSource();

        MigrationManager migrationManager = new FlywayManager();

        DatabaseManager databaseManager = JdbiManager.builder()
                .dataSource(ds)
                .migrationManager(migrationManager)
                .build();

        SchemaBuilder schemaBuilder = OperationsSchemaBuilder.builder()
                .configManager(configManager)
                .databaseManager(databaseManager)
                .build();

        //RestServer server = RestServerVertx.getServer(configManager, schemaBuilder);
        RestServer server = RestServerJetty.getServer(configManager, schemaBuilder);

        GraphQLServer graphQLServer = GraphQLServer.builder()
                .configManager(configManager)
                .databaseManager(databaseManager)
                .server(server)
                .build();

        graphQLServer.setup();
        graphQLServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(graphQLServer::stop));
    }

    @Override
    public void setup() {
        databaseManager.setup();
    }

    @Override
    public void start() {
        try {
            log.info("Starting server...");
            // TODO Remove UID generator used for testing here
            //log.info(UUID.randomUUID().toString());
            log.info(UUID.randomUUID().toString());
            server.start();
        } catch (Exception e) {
            log.error("Caught: ", e);
        }
    }

    @Override
    public void stop() {
        try {
            log.info("Shutting down the server...");
            server.stop();
            databaseManager.close();
            log.info("Done.");
        } catch (Exception e) {
            log.error("Caught: ", e);
        }
    }
}
