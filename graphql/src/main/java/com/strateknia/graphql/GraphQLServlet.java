package com.strateknia.graphql;

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.GraphQLSchema;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "GraphQLServlet", urlPatterns = {"graphql/*"}, loadOnStartup = 1)
public class GraphQLServlet extends GraphQLHttpServlet {

    @Override
    protected GraphQLConfiguration getConfiguration() {
        GraphQLSchema schema = (GraphQLSchema) getServletContext().getAttribute("GraphQLSchema");

        return GraphQLConfiguration.with(schema)
                //.with(queryInvoker)
                //.with(Arrays.asList(listener))
                .build();
    }
}
