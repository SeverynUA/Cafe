package brainacad.org.DatabaseTest.DatabaseUtil;

import brainacad.org.Database.ConfigLoader;
import brainacad.org.Database.DatabaseUtil;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseUtilTest {
    private static final String TEST_DB_NAME = "Cafe_Test";

    @BeforeAll
    void setUp() {
        DatabaseUtil.initDatabase(TEST_DB_NAME);
    }

    @AfterAll
    void tearDown() {
        DatabaseUtil.resetDatabase();
    }

    @Test
    void testDatabaseInitialization() {
        Assertions.assertDoesNotThrow(() -> DatabaseUtil.initDatabase(TEST_DB_NAME),
                "Ініціалізація бази даних має проходити без винятків.");
    }

    @Test
    void testExecuteSQLFile() {
        Assertions.assertDoesNotThrow(() -> DatabaseUtil.executeSQLFile(),
                "Виконання SQL-файлу має бути успішним.");
    }

    @Test
    void testResetDatabase() {
        Assertions.assertDoesNotThrow(DatabaseUtil::resetDatabase,
                "Скидання бази даних має проходити без винятків.");
    }

    @Test
    public void testConfigProperties() {
        // Перевірка URL
        System.out.println();

        String url = ConfigLoader.getProperty("db.url");
        assertNotNull(url, "URL не повинен бути null");
        assertFalse(url.isBlank(), "URL не повинен бути порожнім");
        System.out.println("URL: " + url);

        // Перевірка USER
        String user = ConfigLoader.getProperty("db.username");
        assertNotNull(user, "USER не повинен бути null");
        assertFalse(user.isBlank(), "USER не повинен бути порожнім");
        System.out.println("USER: " + user);

        // Перевірка PASSWORD
        String password = ConfigLoader.getProperty("db.password");
        assertNotNull(password, "PASSWORD не повинен бути null");
        assertFalse(password.isBlank(), "PASSWORD не повинен бути порожнім");
        System.out.println("PASSWORD: " + password);

        // Перевірка SQL_FILE_PATH
        String sqlFilePath = ConfigLoader.getProperty("db.sql_file_path");
        assertNotNull(sqlFilePath, "SQL_FILE_PATH не повинен бути null");
        assertFalse(sqlFilePath.isBlank(), "SQL_FILE_PATH не повинен бути порожнім");
        System.out.println("SQL_FILE_PATH: " + sqlFilePath);

        System.out.println();
    }
}

