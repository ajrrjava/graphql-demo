package com.strateknia.server;

import com.strateknia.graphql.common.utils.ConfigManager;
import com.strateknia.graphql.database.intf.DatabaseManager;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class AbstractApplicationServer {

    protected final RestServer server;
    protected final DatabaseManager databaseManager;
    protected final ConfigManager configManager;

    public abstract void setup();

    public abstract void start();

    public abstract void stop();
}
