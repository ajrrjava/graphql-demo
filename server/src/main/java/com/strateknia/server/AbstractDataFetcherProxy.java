package com.strateknia.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.strateknia.graphql.database.intf.DatabaseManager;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class AbstractDataFetcherProxy {
    private static final Logger log = LoggerFactory.getLogger(AbstractDataFetcherProxy.class);

    protected final DatabaseManager databaseManager;
    private final Gson gson;
    private final Type fooType = new TypeToken<>() {}.getType();

    public AbstractDataFetcherProxy(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.gson = new Gson();
    }

    protected <T> T getArgument(DataFetchingEnvironment environment, String argument, Class<T> clazz) {
        if(!environment.containsArgument(argument)) {
            throw new IllegalArgumentException("");
        }

        String json = gson.toJson(environment.getArgument(argument), fooType);
        return gson.fromJson(json, clazz);
    }
}
