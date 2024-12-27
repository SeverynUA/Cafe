package brainacad.org.DatabaseTest.Requests.Desserts;

import brainacad.org.Database.DataServices.Desserts.DessertsTranslationsDbService;
import brainacad.org.Database.DataServices.LanguagesDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class DessertsTranslationsDbServiceTest extends BaseTest {

    private final DessertsTranslationsDbService translationsService = new DessertsTranslationsDbService();

    public void addLanguageForTest(String code, String name)
    {
        LanguagesDbService languagesDbService = new LanguagesDbService();

        languagesDbService.addLanguage(code, name);
    }

    @Test
    void testAddDessertTranslation() {

        // Додаємо переклад десерту
        int dessertId = 1; // Припустимо, десерт уже існує
        String languageCode = "EN";
        String name = "Chocolate Cake";

        int translationId = translationsService.addDessertTranslation(dessertId, languageCode, name);

        assertTrue(translationId > 0, "Переклад має бути доданий із валідним ID");

        // Перевіряємо доданий переклад
        String sql = "SELECT * FROM Desserts_Translations WHERE Id = " + translationId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Переклад має існувати");
            assertEquals(dessertId, resultSet.getInt("DessertId"), "DessertId має відповідати");
            assertEquals(languageCode, resultSet.getString("LanguageCode"), "LanguageCode має відповідати");
            assertEquals(name, resultSet.getString("Name"), "Name має відповідати");

        } catch (Exception e) {
            fail("Помилка під час перевірки доданого перекладу");
        }
    }

    @Test
    void testUpdateDessertTranslation()
    {
        addLanguageForTest("EN", "English");
        // Додаємо переклад
        int dessertId = 1;
        String languageCode = "EN";
        String oldName = "Chocolate Cake";
        int translationId = translationsService.addDessertTranslation(dessertId, languageCode, oldName);

        // Оновлюємо переклад
        String newName = "Dark Chocolate Cake";
        int rowsUpdated = translationsService.updateDessertTranslation(translationId, newName);

        assertEquals(1, rowsUpdated, "Має бути оновлено 1 рядок");

        // Перевіряємо оновлення
        String sql = "SELECT Name FROM Desserts_Translations WHERE Id = " + translationId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Переклад має існувати");
            assertEquals(newName, resultSet.getString("Name"), "Назва має бути оновлена");

        } catch (Exception e) {
            fail("Помилка під час перевірки оновлення перекладу");
        }
    }
}
