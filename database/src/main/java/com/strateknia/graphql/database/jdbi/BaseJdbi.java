package com.strateknia.graphql.database.jdbi;

import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseJdbi {
    private static final Logger log = LoggerFactory.getLogger(BaseJdbi.class);

    private final Jdbi jdbi;

    protected BaseJdbi(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public <T> T withHandle(HandleCallback<T, ?> callback) {
        return jdbi.withHandle(h -> {
            try {
                return callback.withHandle(h);
            } catch (Exception e) {
                log.error("Caught: ", e);
                return null;
            }
        });
    }

    public <T> T inTransaction(HandleCallback<T, ?> callback) {
        return jdbi.withHandle(h -> {
            h.begin();
            try {
                T result = callback.withHandle(h);
                h.commit();
                return result;
            } catch(Exception e) {
                log.error("Caught: ", e);
                h.rollback();
                return null;
            }
        });
    }
}
