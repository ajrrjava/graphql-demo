package com.strateknia.graphql.database.intf;

public interface DatabaseManager {
    void setup();

    void close();

    PlanetManager getPlanetManager();
}
