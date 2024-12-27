package brainacad.org.DatabaseTest.Requests.Customers;

import brainacad.org.Database.DataServices.CustomersDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class CustomersDbServiceTest extends BaseTest {

    private final CustomersDbService customersDbService = new CustomersDbService();

    @Test
    void testAddCustomer() {
        // Додаємо клієнта
        String firstName = "John";
        String lastName = "Doe";
        String middleName = "Edward";
        String birthDate = "1990-05-15";
        String email = "johndoe@example.com";
        String phoneNumber = "1234567890";
        double sale = 10.5;

        int customerId = customersDbService.addCustomer(firstName, lastName, middleName, birthDate, email, phoneNumber, sale);

        assertTrue(customerId > 0, "Клієнт має бути доданий із валідним ID");

        // Перевіряємо, чи клієнт доданий
        String sql = "SELECT * FROM Customers WHERE Id = " + customerId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Клієнт має існувати");
            assertEquals(firstName, resultSet.getString("FirstName"), "Ім'я має відповідати");
            assertEquals(lastName, resultSet.getString("LastName"), "Прізвище має відповідати");
            assertEquals(email, resultSet.getString("Email"), "Email має відповідати");
            assertEquals(sale, resultSet.getDouble("Sale"), "Знижка має відповідати");

        } catch (Exception e) {
            fail("Помилка під час перевірки доданого клієнта");
        }
    }

    @Test
    void testUpdateCustomerSale() {
        // Додаємо клієнта
        String firstName = "Jane";
        String lastName = "Smith";
        String middleName = "Ann";
        String birthDate = "1995-08-20";
        String email = "janesmith@example.com";
        String phoneNumber = "0987654321";
        double oldSale = 15.0;
        int customerId = customersDbService.addCustomer(firstName, lastName, middleName, birthDate, email, phoneNumber, oldSale);

        // Оновлюємо знижку клієнта
        double newSale = 20.0;
        int rowsUpdated = customersDbService.updateCustomerSale(customerId, newSale);

        assertEquals(1, rowsUpdated, "Має бути оновлено 1 рядок");

        // Перевіряємо оновлення
        String sql = "SELECT Sale FROM Customers WHERE Id = " + customerId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertTrue(resultSet.next(), "Клієнт має існувати");
            assertEquals(newSale, resultSet.getDouble("Sale"), "Знижка має бути оновлена");

        } catch (Exception e) {
            fail("Помилка під час перевірки оновлення клієнта");
        }
    }

    @Test
    void testDeleteCustomer() {
        // Додаємо клієнта
        String firstName = "Emily";
        String lastName = "Brown";
        String middleName = "Rose";
        String birthDate = "1988-02-10";
        String email = "emilybrown@example.com";
        String phoneNumber = "555666777";
        double sale = 5.0;
        int customerId = customersDbService.addCustomer(firstName, lastName, middleName, birthDate, email, phoneNumber, sale);

        // Видаляємо клієнта
        int rowsDeleted = customersDbService.deleteCustomer(customerId);

        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");

        // Перевіряємо, чи клієнт видалений
        String sql = "SELECT * FROM Customers WHERE Id = " + customerId;
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            assertFalse(resultSet.next(), "Клієнт не має існувати");

        } catch (Exception e) {
            fail("Помилка під час перевірки видалення клієнта");
        }
    }

    @Test
    void testShowAllCustomers() {
        // Додаємо кілька клієнтів
        customersDbService.addCustomer("Alice", "White", "Lynn", "1992-11-12", "alicewhite@example.com", "123123123", 10.0);
        customersDbService.addCustomer("Bob", "Green", "Michael", "1985-06-25", "bobgreen@example.com", "321321321", 15.0);

        // Виводимо всіх клієнтів
        System.out.println("Список клієнтів:");
        customersDbService.showAllCustomers();
    }
}

