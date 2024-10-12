package com.strateknia.graphql.database.jdbi.managers;

import com.strateknia.graphql.common.model.Planet;
import com.strateknia.graphql.database.intf.PlanetManager;
import com.strateknia.graphql.database.jdbi.BaseJdbi;
import com.strateknia.graphql.database.jdbi.dao.PlanetsDao;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class PlanetsJdbi extends BaseJdbi implements PlanetManager {

    public PlanetsJdbi(Jdbi jdbi) {
        super(jdbi);
    }

    public List<Planet> getAllPlanets() {
        return withHandle(h -> {
            PlanetsDao dao = h.attach(PlanetsDao.class);
            return dao.getAllPlanets();
        });
    }

    public Planet getPlanetById(long planetId) {
        return withHandle(h -> {
            PlanetsDao dao = h.attach(PlanetsDao.class);
            return dao.getPlanetById(planetId);
        });
    }

    public Integer addPlanet(Planet planet) {
        return withHandle(h -> {
            PlanetsDao dao = h.attach(PlanetsDao.class);
            return dao.addPlanet(
                    planet.getName(),
                    planet.getDescription(),
                    planet.getDiameter(),
                    planet.getType()
            );
        });
    }

    public Integer updatePlanet(Planet planet) {
        return withHandle(h -> {
            PlanetsDao dao = h.attach(PlanetsDao.class);
            return dao.updatePlanet(
                    planet.getId(),
                    planet.getName(),
                    planet.getDescription(),
                    planet.getDiameter(),
                    planet.getType()
            );
        });
    }

    public Integer deletePlanet(long planetId) {
        return withHandle(h -> {
            PlanetsDao dao = h.attach(PlanetsDao.class);
            return dao.deletePlanet(planetId);
        });
    }
}
