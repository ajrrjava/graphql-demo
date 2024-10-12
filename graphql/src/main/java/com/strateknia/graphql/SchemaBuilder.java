package com.strateknia.graphql;

import com.strateknia.graphql.common.utils.ConfigManager;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;

@SuperBuilder
public abstract class SchemaBuilder {

    private final ConfigManager configManager;

    public final GraphQLSchema getGraphQLSchema() {
        String schemaName = configManager.getString("server.graphql.schema", "/schema.graphqls");
        InputStream schema = SchemaBuilder.class.getResourceAsStream(schemaName);

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = getRunTimeWiring();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    public abstract RuntimeWiring getRunTimeWiring();
}
