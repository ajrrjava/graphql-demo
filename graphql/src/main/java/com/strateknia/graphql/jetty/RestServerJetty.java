package com.strateknia.graphql.jetty;

import com.strateknia.graphql.GraphQLServlet;
import com.strateknia.server.RestServer;
import com.strateknia.graphql.SchemaBuilder;
import com.strateknia.graphql.common.utils.ConfigManager;
import jakarta.servlet.DispatcherType;
import lombok.Builder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

@Builder
public class RestServerJetty implements RestServer {

    private final ConfigManager configManager;
    private final SchemaBuilder schemaBuilder;
    private Server server;

    public static RestServer getServer(ConfigManager configManager, SchemaBuilder schemaBuilder) {
        return RestServerJetty.builder()
                .configManager(configManager)
                .schemaBuilder(schemaBuilder)
                .build();
    }

    @Override
    public void start() throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        FilterHolder filter = new FilterHolder();
        filter.setName("cors");
        filter.setInitParameter("Access-Control-Allow-Origin", "http://localhost:8080");
        //filter.setInitParameter("allowedOrigins", "localhost:8080");
        //filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedMethods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
        filter.setInitParameter("allowedHeaders", "Access-Control-Allow-Origin, Origin, X-Requested-With, Content-Type, Accept");
        filter.setInitParameter("preflightMaxAge", "728000");
        filter.setInitParameter("allowCredentials", "true");
        filter.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());
        CrossOriginFilter corsFilter = new CrossOriginFilter();
        filter.setFilter(corsFilter);

        String route = configManager.getString("server.graphql.route",  "/graphql/*");

        contextHandler.addServlet(GraphQLServlet.class, route);
        contextHandler.setAttribute("GraphQLSchema", schemaBuilder.getGraphQLSchema());

        contextHandler.addFilter(filter, route, EnumSet.allOf(DispatcherType.class));

        int port = configManager.getInt("server.port");
        server = new Server(port);
        server.setHandler(contextHandler);

        server.start();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }
}
