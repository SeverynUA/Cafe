package brainacad.org.DatabaseTest.Requests.Drinks;

import brainacad.org.Database.DataServices.Drinks.DrinkTranslationsDbService;
import brainacad.org.Database.DataServices.LanguagesDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DrinkTranslationsDbServiceTest extends BaseTest {

    private final DrinkTranslationsDbService translationsService = new DrinkTranslationsDbService();
    private final QueryExecutor queryExecutor = new QueryExecutor();

    public void addLanguageForTest(String code, String name)
    {
        LanguagesDbService languagesDbService = new LanguagesDbService();

        languagesDbService.addLanguage(code, name);
    }

    @Test
    void testAddDrinkTranslation()
    {
        // Додаємо переклад напою
        int drinkId = 1; // Припустимо, напій уже існує
        String languageCode = "EN";
        String name = "Espresso";

        int translationId = translationsService.addDrinkTranslation(drinkId, languageCode, name);

        assertTrue(translationId > 0, "Переклад має бути доданий із валідним ID");

        // Перевіряємо доданий переклад
        String sql = "SELECT * FROM DrinkTranslations WHERE Id = " + translationId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Переклад має існувати");
            assertEquals(drinkId, resultSet.getInt("DrinkId"), "DrinkId має відповідати");
            assertEquals(languageCode, resultSet.getString("LanguageCode"), "LanguageCode має відповідати");
            assertEquals(name, resultSet.getString("Name"), "Name має відповідати");

        } catch (Exception e) {
            fail("Помилка під час перевірки доданого перекладу");
        }
    }

    @Test
    void testUpdateDrinkTranslation() {
        // Додаємо переклад

        addLanguageForTest("EN", "English");

        int drinkId = 1;
        String languageCode = "EN";
        String oldName = "Latte";
        int translationId = translationsService.addDrinkTranslation(drinkId, languageCode, oldName);

        // Оновлюємо переклад
        String newName = "Cappuccino";
        int rowsUpdated = translationsService.updateDrinkTranslation(translationId, newName);

        assertEquals(1, rowsUpdated, "Має бути оновлено 1 рядок");

        // Перевіряємо оновлення
        String sql = "SELECT Name FROM DrinkTranslations WHERE Id = " + translationId;
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
