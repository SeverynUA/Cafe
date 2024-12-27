package brainacad.org.DatabaseTest.Requests.Drinks;

import brainacad.org.Database.DataServices.Drinks.DrinkTranslationsDbService;
import brainacad.org.Database.DataServices.Drinks.DrinksDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import brainacad.org.Models.Type_Drinks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DrinksDbServiceTest extends BaseTest {

    private final DrinksDbService drinksDbService = new DrinksDbService();
    private final QueryExecutor queryExecutor = new QueryExecutor();

    @Test
    void testAddDrink() {
        // Додаємо напій
        String type = "Hot drink";
        double price = 3.99;
        int drinkId = drinksDbService.addDrink(Type_Drinks.Hot_drink, price);

        assertTrue(drinkId > 0, "Напій має бути доданий із валідним ID");

        // Перевіряємо, чи напій доданий
        String sql = "SELECT * FROM Drinks WHERE Id = " + drinkId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Напій має існувати");
            assertEquals(type, resultSet.getString("Type"), "Тип має відповідати");
            assertEquals(price, resultSet.getDouble("Price"), "Ціна має відповідати");

        } catch (Exception e) {
            fail("Помилка під час перевірки доданого напою");
        }
    }

    @Test
    void testUpdateDrinkPrice() {
        // Додаємо напій
        double oldPrice = 2.99;
        int drinkId = drinksDbService.addDrink(Type_Drinks.Soft_drink, oldPrice);

        // Оновлюємо ціну напою
        double newPrice = 4.49;
        int rowsUpdated = drinksDbService.updateDrinkPrice(drinkId, newPrice);

        assertEquals(1, rowsUpdated, "Має бути оновлено 1 рядок");

        // Перевіряємо оновлення
        String sql = "SELECT Price FROM Drinks WHERE Id = " + drinkId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Напій має існувати");
            assertEquals(newPrice, resultSet.getDouble("Price"), "Ціна має бути оновлена");

        } catch (Exception e) {
            fail("Помилка під час перевірки оновлення напою");
        }
    }

    @Test
    void testDeleteDrink() {
        // Додаємо напій
        Type_Drinks drinkType = Type_Drinks.Alcohol_drink;
        double price = 5.49;
        int drinkId = drinksDbService.addDrink(drinkType, price);

        // Видаляємо напій
        int rowsDeleted = drinksDbService.deleteDrink(drinkId);

        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");

        // Перевіряємо, чи напій видалений
        String sql = "SELECT * FROM Drinks WHERE Id = " + drinkId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertFalse(resultSet.next(), "Напій не має існувати");

        } catch (Exception e) {
            fail("Помилка під час перевірки видалення напою");
        }
    }

    @Test
    void testUpdatePriceDrink_FilterType() {
        // Виклик методу
        double newPrice = 6.99;
        int rowsUpdated = drinksDbService.updatePriceDrink_FilterType(Type_Drinks.Soft_drink, newPrice);

        // Перевірка результату
        assertEquals(3, rowsUpdated, "Має бути оновлено 1 рядок для типу SoftDrink");

        // Перевірка оновленої ціни
        String sqlCheck = "SELECT Price FROM Drinks WHERE Type = ?::drinktype";
        try (ResultSet resultSet = queryExecutor.executeQuery(sqlCheck, "Soft drink")) {
            assertTrue(resultSet.next());
            assertEquals(newPrice, resultSet.getDouble("Price"), 0.01, "Ціна напою повинна бути оновлена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testShowAllDrinkTranslations() {

        // Виклик тестованого методу
        System.out.println("=== Тест: Виведення напоїв англійською мовою ===");
        drinksDbService.showAllDrinksWithNames("eu");

        System.out.println("=== Тест: Виведення напоїв французькою мовою ===");
        drinksDbService.showAllDrinksWithNames("fr");

        System.out.println("=== Тест: Виведення напоїв українською мовою ===");
        drinksDbService.showAllDrinksWithNames("ua");

        System.out.println("=== Тест: Виведення напоїв для мови, якої немає в перекладах ===");
        drinksDbService.showAllDrinksWithNames("de");
    }
}
