package brainacad.org.DatabaseTest.QueryExecutor;

import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.sql.ResultSet;

import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryExecutorTest {
    private QueryExecutor queryExecutor;

    @BeforeAll
    void setUp() {
        // Ініціалізація бази даних
        DatabaseUtil.initDatabase("Cafe_Test");
        queryExecutor = new QueryExecutor();

        // Додати тестові дані
        String createTableQuery = """
            CREATE TABLE IF NOT EXISTS TestTable (
                Id SERIAL PRIMARY KEY,
                Name VARCHAR(50) NOT NULL,
                Age INT NOT NULL
            );
        """;
        queryExecutor.executeUpdate(createTableQuery);
    }

    @AfterAll
    void tearDown() {
        // Очистити базу даних
        DatabaseUtil.resetDatabase();
    }

    @BeforeEach
    void cleanUp() {
        // Очистити таблицю перед кожним тестом
        queryExecutor.executeUpdate("DELETE FROM TestTable");
    }

    @Test
    void testExecuteUpdate_InsertRow() {
        String insertQuery = "INSERT INTO TestTable (Name, Age) VALUES (?, ?)";
        int rowsAffected = queryExecutor.executeUpdate(insertQuery, "John Doe", 25);

        Assertions.assertEquals(1, rowsAffected, "Має бути додано 1 рядок.");
    }

    @Test
    void testExecuteInsertAndReturnId() {
        String insertQuery = "INSERT INTO TestTable (Name, Age) VALUES (?, ?) RETURNING Id";
        int generatedId = queryExecutor.executeInsertAndReturnId(insertQuery, "Jane Doe", 30);

        Assertions.assertTrue(generatedId > 0, "Згенерований ID має бути більшим за 0.");
    }

    @Test
    void testExecuteQuery_SelectRow() {
        // Додаємо дані
        queryExecutor.executeUpdate("INSERT INTO TestTable (Name, Age) VALUES (?, ?)", "Alice", 22);

        // Виконуємо SELECT
        String selectQuery = "SELECT * FROM TestTable WHERE Name = ?";
        try (ResultSet resultSet = queryExecutor.executeQuery(selectQuery, "Alice")) {
            Assertions.assertTrue(resultSet.next(), "Результат має містити хоча б 1 рядок.");
            Assertions.assertEquals("Alice", resultSet.getString("Name"), "Ім'я має відповідати.");
            Assertions.assertEquals(22, resultSet.getInt("Age"), "Вік має відповідати.");
        } catch (SQLException e) {
            Assertions.fail("Помилка під час виконання SELECT: " + e.getMessage());
        }
    }

    @Test
    void testExecuteQuery_NoResult() {
        String selectQuery = "SELECT * FROM TestTable WHERE Name = ?";
        try (ResultSet resultSet = queryExecutor.executeQuery(selectQuery, "NonExistent")) {
            Assertions.assertFalse(resultSet.next(), "Результат має бути порожнім.");
        } catch (SQLException e) {
            Assertions.fail("Помилка під час виконання SELECT: " + e.getMessage());
        }
    }
}

