package com.phonestore.util;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(ConfigurationUtil.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException("not found application.properties in resources");

        }
    }

    private ConfigurationUtil() {}

    public static String getValueConfig(String key) {
        String value = Optional.ofNullable((String) properties.get(key)).orElse("");
        return value.matches("^\\$\\{\\w+\\}") ? System.getenv(value.substring(2, value.length() - 1)) : value;
    }
}