package com.strateknia.graphql.database.intf;

import com.strateknia.graphql.common.model.Planet;

import java.util.List;

public interface PlanetManager {
    List<Planet> getAllPlanets();

    Planet getPlanetById(long planetId);

    Integer addPlanet(Planet planet);

    Integer updatePlanet(Planet planet);

    Integer deletePlanet(long planetId);
}
