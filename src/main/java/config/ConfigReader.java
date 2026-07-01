package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties não encontrado no classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl()         { return get("base.url");         }
    public static String getUsername()        { return get("username");         }
    public static String getPassword()        { return get("password");         }
    public static String getLockedUsername()  { return get("locked.username");  }
    public static String getInvalidUsername() { return get("invalid.username"); }
    public static String getInvalidPassword() { return get("invalid.password"); }
}
