package Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private static final String connectionUrl;

    static {
        Properties prop = new Properties();
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find dbconfig.properties");
            }
            prop.load(input);
            connectionUrl = prop.getProperty("localconnection.url");
            if (connectionUrl == null || connectionUrl.isEmpty()) {
                throw new RuntimeException("Connection URL is not specified in dbconfig.properties");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error loading dbconfig.properties", ex);
        }
    }

    public static String getConnectionUrl() {
        return connectionUrl;
    }
}
