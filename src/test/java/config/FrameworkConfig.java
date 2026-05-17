package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FrameworkConfig {
    private static final String DEFAULT_ENV = "qa";
    private static final Properties PROPERTIES = loadProperties();

    private FrameworkConfig() {
    }

    private static Properties loadProperties() {
        String env = System.getProperty("env", DEFAULT_ENV);
        String resourcePath = String.format("config/%s.properties", env);
        Properties properties = new Properties();

        try (InputStream inputStream = FrameworkConfig.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Config file not found: " + resourcePath);
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load config file: " + resourcePath, exception);
        }
    }

    public static String get(String key) {
        String override = System.getProperty(key);
        if (override != null && !override.isBlank()) {
            return override;
        }

        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing config value for key: " + key);
        }
        return value;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
