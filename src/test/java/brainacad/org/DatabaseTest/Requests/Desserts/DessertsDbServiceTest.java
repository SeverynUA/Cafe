package brainacad.org.DatabaseTest.Requests.Desserts;
import brainacad.org.Database.DataServices.Desserts.DessertsDbService;
import brainacad.org.Database.DataServices.Desserts.DessertsTranslationsDbService;
import brainacad.org.Database.DataServices.Drinks.DrinkTranslationsDbService;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import java.sql.ResultSet;
import static org.junit.jupiter.api.Assertions.*;
import brainacad.org.Database.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;


public class DessertsDbServiceTest extends BaseTest
{
    private final QueryExecutor queryExecutor = new QueryExecutor();
    private final DessertsDbService dessertsDbService = new DessertsDbService();

    @Test
    void testAddDessert()
    {
        double price = 4.99;
        int dessertId = dessertsDbService.addDessert(price);

        assertTrue(dessertId > 0, "Десерт має бути доданий із валідним ID");

        String sql = "SELECT * FROM Desserts WHERE Id = " + dessertId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql))
        {

            assertTrue(resultSet.next(), "Десерт має існувати");
            assertEquals(price, resultSet.getDouble("Price"), "Ціна має відповідати");

        } catch (Exception e) {
            fail("Помилка під час перевірки доданого десерту");
        }
    }

    @Test
    void testUpdateDessertPrice()
    {
        double oldPrice = 3.99;
        int dessertId = dessertsDbService.addDessert(oldPrice);

        double newPrice = 5.99;
        int rowsUpdated = dessertsDbService.updateDessertPrice(dessertId, newPrice);

        assertEquals(1, rowsUpdated, "Має бути оновлено 1 рядок");

        String sql = "SELECT Price FROM Desserts WHERE Id = " + dessertId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql))
        {

            assertTrue(resultSet.next(), "Десерт має існувати");
            assertEquals(newPrice, resultSet.getDouble("Price"), "Ціна має бути оновлена");

        } catch (Exception e) {
            fail("Помилка під час перевірки оновлення десерту");
        }
    }

    @Test
    void testDeleteDessert()
    {
        double price = 2.99;
        int dessertId = dessertsDbService.addDessert(price);

        int rowsDeleted = dessertsDbService.deleteDessert(dessertId);

        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");

        String sql = "SELECT * FROM Desserts WHERE Id = " + dessertId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertFalse(resultSet.next(), "Десерт не має існувати");

        } catch (Exception e)
        {
            fail("Помилка під час перевірки видалення десерту");
        }
    }

    @Test
    void testShowAllDessertsWithNames()
    {
        int dessertId1 = queryExecutor.executeInsertAndReturnId(
                "INSERT INTO Desserts (Price) VALUES (?)", 5.99
        );
        int dessertId2 = queryExecutor.executeInsertAndReturnId(
                "INSERT INTO Desserts (Price) VALUES (?)", 3.49
        );

        DessertsTranslationsDbService dessertsTranslationsDbService = new DessertsTranslationsDbService();
        // Додавання тестових даних до таблиці Desserts_Translations
        dessertsTranslationsDbService.addDessertTranslation(dessertId1, "en", "Chocolate Muffin");
        dessertsTranslationsDbService.addDessertTranslation(dessertId2, "fr", "Gâteau au Chocolat");

        // Виклик тестованого методу
        System.out.println("=== Тест: Виведення десертів англійською мовою ===");
        dessertsDbService.showAllDessertsWithNames("en");

        System.out.println("=== Тест: Виведення десертів французькою мовою ===");
        dessertsDbService.showAllDessertsWithNames("fr");

        System.out.println("=== Тест: Виведення десертів українською мовою ===");
        dessertsDbService.showAllDessertsWithNames("ua");

        System.out.println("=== Тест: Виведення десертів для мови, якої немає в перекладах ===");
        dessertsDbService.showAllDessertsWithNames("de");
    }

}
