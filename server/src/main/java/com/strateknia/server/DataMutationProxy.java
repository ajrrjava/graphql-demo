package com.strateknia.server;

import com.strateknia.graphql.common.model.Planet;
import com.strateknia.graphql.database.intf.DatabaseManager;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataMutationProxy extends AbstractDataFetcherProxy {
    private static final Logger log = LoggerFactory.getLogger(DataMutationProxy.class);

    public DataMutationProxy(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public Boolean addPlanet(DataFetchingEnvironment environment) throws Exception {
        Planet planet = getArgument(environment, "planet", Planet.class);
        databaseManager.getPlanetManager().addPlanet(planet);
        return true;
    }

    public Boolean updatePlanet(DataFetchingEnvironment environment) throws Exception {
        Planet planet = getArgument(environment, "planet", Planet.class);
        databaseManager.getPlanetManager().updatePlanet(planet);
        return true;
    }

    public Boolean deletePlanet(DataFetchingEnvironment environment) throws Exception {
        long planetId = getArgument(environment, "id", Long.class);
        databaseManager.getPlanetManager().deletePlanet(planetId);
        return true;
    }
}
