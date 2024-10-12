package com.strateknia.graphql.common.utils;

import com.typesafe.config.Config;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

@Builder
@Getter
public class ConfigManager {

    private final Config config;

    public ConfigManager(Config config) {
        this.config = config;
    }

    public String getString(String path) {
        return getString(path, "");
    }

    public String getString(String path, String defaultValue) {
        return getConfig(config, path, defaultValue, config::getString);
    }

    public Integer getInt(String path) {
        return getInt(path, 0);
    }

    public Integer getInt(String path, Integer defaultValue) {
        return getConfig(config, path, defaultValue, config::getInt);
    }

    public Long getLong(String path) {
        return getLong(path, 0L);
    }

    public Long getLong(String path, Long defaultValue) {
        return getConfig(config, path, defaultValue, config::getLong);
    }

    private <T> T getConfig(Config config, String path, T defaultValue, Function<String, T> supplier) {
        if (config.hasPathOrNull(path)) {
            if (config.getIsNull(path)) {
                return defaultValue;
            } else {
                return supplier.apply(path);
            }
        } else {
            return defaultValue;
        }
    }
}
