package com.strateknia.graphql;

import com.strateknia.graphql.database.intf.DatabaseManager;
import com.strateknia.server.DataMutationProxy;
import com.strateknia.server.DataQueryProxy;
import graphql.schema.idl.RuntimeWiring;
import lombok.experimental.SuperBuilder;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@SuperBuilder
public class OperationsSchemaBuilder extends SchemaBuilder {

    private final DatabaseManager databaseManager;

    @Override
    public RuntimeWiring getRunTimeWiring() {
        DataMutationProxy mutation = new DataMutationProxy(databaseManager);
        DataQueryProxy query = new DataQueryProxy(databaseManager);

        return newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("getPlanets", query::getAllPlanets))
                .type("Query", builder -> builder.dataFetcher("getPlanet", query::getPlanet))
                .type("Mutation", builder -> builder.dataFetcher("addPlanet",  mutation::addPlanet))
                .type("Mutation", builder -> builder.dataFetcher("updatePlanet",  mutation::updatePlanet))
                .type("Mutation", builder -> builder.dataFetcher("deletePlanet",  mutation::deletePlanet))
                .build();
    }
}
