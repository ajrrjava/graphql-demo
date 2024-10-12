package com.strateknia.graphql.database.jdbi.dao;

import com.strateknia.graphql.common.model.Planet;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PlanetsDao {

    @SqlQuery("SELECT id, name, description, diameter, type FROM PRODUCTION.planets WHERE id = :planetId")
    @RegisterRowMapper(PlanetMapper.class)
    Planet getPlanetById(@Bind("planetId") long planetId);

    @SqlQuery("SELECT id, name, description, diameter, type FROM PRODUCTION.planets")
    @RegisterRowMapper(PlanetMapper.class)
    List<Planet> getAllPlanets();

    @SqlUpdate("INSERT INTO PRODUCTION.planets (name, description, diameter, type) VALUES (:name, :description, :diameter, :type)")
    Integer addPlanet(
            @Bind("name") String name,
            @Bind("description") String description,
            @Bind("diameter") double diameter,
            @Bind("type") String type
    );

    @SqlUpdate("UPDATE PRODUCTION.planets SET name = :name, description = :description, diameter = :diameter, type = :type WHERE id = :id")
    Integer updatePlanet(
            @Bind("id") long id,
            @Bind("name") String name,
            @Bind("description") String description,
            @Bind("diameter") double diameter,
            @Bind("type") String type
    );

    @SqlUpdate("DELETE FROM PRODUCTION.planets WHERE id = :id")
    Integer deletePlanet(@Bind("id") long id);

    class PlanetMapper implements RowMapper<Planet> {
        @Override
        public Planet map(ResultSet rs, StatementContext ctx) throws SQLException {
            return Planet.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .diameter(rs.getDouble("diameter"))
                    .type(rs.getString("type"))
                    .build();
        }
    }
}
