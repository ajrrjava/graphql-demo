package com.strateknia.server;

import com.strateknia.graphql.common.model.Planet;
import com.strateknia.graphql.database.intf.DatabaseManager;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataQueryProxy extends AbstractDataFetcherProxy {
    private static final Logger log = LoggerFactory.getLogger(DataQueryProxy.class);

    public DataQueryProxy(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public Planet getPlanet(DataFetchingEnvironment environment) throws Exception {
        long planetId = getArgument(environment, "id", Long.class);

       return databaseManager.getPlanetManager().getPlanetById(planetId);
    }

    public List<Planet> getAllPlanets(DataFetchingEnvironment environment) throws Exception {
       return databaseManager.getPlanetManager().getAllPlanets();
    }
}
