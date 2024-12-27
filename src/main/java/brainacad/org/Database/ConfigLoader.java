package brainacad.org.Database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader
{
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Конфігураційний файл не знайдено");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

