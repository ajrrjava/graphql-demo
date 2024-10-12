package com.strateknia.graphql.common.utils;

import java.util.Objects;
import java.util.Properties;

public class PropertiesUtil {

    public static String getProperty(Properties properties, String key) {
        return Objects.requireNonNull(properties.getProperty(key));
    }

    public static int getIntProperty(Properties properties, String key) {
        return Integer.parseInt(Objects.requireNonNull(properties.getProperty(key)));
    }
}
