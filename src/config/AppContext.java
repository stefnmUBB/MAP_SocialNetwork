package config;

import java.util.Properties;

public class AppContext {
    public static final Properties PROPERTIES = Config.getProperties();

    public static final String USERS_FILE_NAME = PROPERTIES.getProperty("USERS");
    public static final String FRIENDSHIPS_FILE_NAME = PROPERTIES.getProperty("FRIENDSHIPS");
}
