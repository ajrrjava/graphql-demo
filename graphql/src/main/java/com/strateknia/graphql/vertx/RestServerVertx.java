package com.strateknia.graphql.vertx;

import com.strateknia.server.RestServer;
import com.strateknia.graphql.SchemaBuilder;
import com.strateknia.graphql.common.utils.ConfigManager;
import graphql.GraphQL;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Builder
public class RestServerVertx extends AbstractVerticle implements RestServer {
    private static final Logger log = LoggerFactory.getLogger(RestServerVertx.class);

    private final ConfigManager configManager;
    private final SchemaBuilder schemaBuilder;

    public static RestServer getServer(ConfigManager configManager, SchemaBuilder schemaBuilder) {
        Vertx vertx = Vertx.vertx();
        RestServerVertx restServerVertx = RestServerVertx.builder()
                .configManager(configManager)
                .schemaBuilder(schemaBuilder)
                .build();

        vertx.deployVerticle(restServerVertx).onComplete(handler -> {
            if (handler.succeeded()) {
                log.info("Verticle deployed");
            } else {
                log.error("Caught: ", handler.cause());
            }
        });
        return restServerVertx;
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.PUT);

        router.route().handler(CorsHandler.create("*")
                .allowedHeaders(allowedHeaders)
                .allowedMethods(allowedMethods));

        GraphQL graphQL = GraphQL.newGraphQL(schemaBuilder.getGraphQLSchema()).build();
        GraphQLHandler graphQLHandler = GraphQLHandler.create(graphQL);

        String route = configManager.getString("server.graphql.route",  "/graphql");
        router.route(route).handler(graphQLHandler);

        int port = configManager.getInt("server.port");
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port);
    }

    @Override
    public void stop() {
        vertx.close();
    }
}
