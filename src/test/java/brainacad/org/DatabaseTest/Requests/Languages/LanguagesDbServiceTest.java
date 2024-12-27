package brainacad.org.DatabaseTest.Requests.Languages;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import brainacad.org.Database.DataServices.LanguagesDbService;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LanguagesDbServiceTest extends BaseTest {

    private final LanguagesDbService languagesDbService = new LanguagesDbService();

    @Test
    public void testAddLanguage() {
        LanguagesDbService service = new LanguagesDbService();
        String affectedRows = service.addLanguage("de", "German");
        assertEquals("de", affectedRows, "Має бути змінено 1 рядок");
    }

    @Test
    void testUpdateLanguageName() {
        // Додаємо мову
        String code = "it";
        String name = "Italian";
        languagesDbService.addLanguage(code, name);

        // Оновлюємо назву мови
        String newName = "Italiano";
        int rowsUpdated = languagesDbService.updateLanguageName(code, newName);
        assertEquals(1, rowsUpdated, "Має бути оновлено 1 рядок");

        // Перевіряємо оновлення
        String sql = "SELECT * FROM Languages WHERE Code = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             ResultSet resultSet = new QueryExecutor().executeQuery(sql, code)) {

            assertTrue(resultSet.next(), "Мова має існувати");
            assertEquals(newName, resultSet.getString("Name"), "Назва має бути оновлена");

        } catch (Exception e) {
            fail("Помилка під час перевірки оновлення мови");
        }
    }

    @Test
    void testDeleteLanguage() {
        // Додаємо мову
        String code = "pt";
        String name = "Portuguese";
        languagesDbService.addLanguage(code, name);

        // Видаляємо мову
        int rowsDeleted = languagesDbService.deleteLanguage(code);
        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");

        // Перевіряємо, чи мова видалена
        String sql = "SELECT * FROM Languages WHERE Code = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             ResultSet resultSet = new QueryExecutor().executeQuery(sql, code)) {

            assertFalse(resultSet.next(), "Мова не має існувати");

        } catch (Exception e) {
            fail("Помилка під час перевірки видалення мови");
        }
    }

    @Test
    void testShowAllLanguages()
    {
        // Виводимо всі мови
        System.out.println("Список мов:");
        languagesDbService.showAllLanguages();
    }
}
