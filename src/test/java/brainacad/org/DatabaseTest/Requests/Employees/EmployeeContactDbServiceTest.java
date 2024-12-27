package brainacad.org.DatabaseTest.Requests.Employees;

import brainacad.org.Database.DataServices.Employee.EmployeeContactDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeContactDbServiceTest extends BaseTest {

    private final EmployeeContactDbService employeeContactDbService = new EmployeeContactDbService();
    private final QueryExecutor queryExecutor = new QueryExecutor();

    @Test
    void testAddEmployeeContact() {
        int employeeId = 1;
        String phoneNumber = "123-456-7890";
        String email = "johndoe@example.com";

        int contactId = employeeContactDbService.addEmployeeContact(employeeId, phoneNumber, email);
        assertTrue(contactId > 0, "Контактні дані мають бути додані із валідним ID");
    }

    @Test
    void testDeleteEmployeeContact() {
        int employeeId = 1;
        String phoneNumber = "123-456-7890";
        String email = "johndoe@example.com";

        int contactId = employeeContactDbService.addEmployeeContact(employeeId, phoneNumber, email);

        int rowsDeleted = employeeContactDbService.deleteEmployeeContact(contactId);
        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");
    }

    @Test
    void testUpdatePhoneAndPostalCode_filterConfectioner() {
        // Початкові дані
        String newPhone = "123-456-7890";
        String newPostalCode = "54321";

        // Виклик методу
        int updatedRows = employeeContactDbService.updatePhoneAndPostalCode_filterConfectioner(newPhone, newPostalCode);

        assertTrue(updatedRows > 0, "Має бути оновлено хоча б один запис для кондитерів");

        // Перевірка оновлення PostalCode
        String sqlPostalCode = """
        SELECT PostalCode
        FROM EmployeeAddresses ea
        JOIN Employees e ON e.Id = ea.EmployeeId
        WHERE e.Type = 'Confectioner'
    """;

        try (ResultSet rsPostalCode = queryExecutor.executeQuery(sqlPostalCode)) {
            while (rsPostalCode.next()) {
                assertEquals(newPostalCode, rsPostalCode.getString("PostalCode"), "Поштові коди мають бути оновлені");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Перевірка оновлення PhoneNumber
        String sqlPhoneNumber = """
        SELECT PhoneNumber
        FROM EmployeeContact ec
        JOIN Employees e ON e.Id = ec.EmployeeId
        WHERE e.Type = 'Confectioner'
    """;

        try (ResultSet rsPhoneNumber = queryExecutor.executeQuery(sqlPhoneNumber)) {
            while (rsPhoneNumber.next()) {
                assertEquals(newPhone, rsPhoneNumber.getString("PhoneNumber"), "Телефони мають бути оновлені");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Test
    void testUpdatePhone_filterBarista() {
        // Вхідні дані
        String newPhone = "987-654-3210";

        // Виклик методу
        int updatedRows = employeeContactDbService.updatePhone_filterBarista(newPhone);

        // Перевірка, що оновлено хоча б один рядок
        assertTrue(updatedRows > 0, "Має бути оновлено хоча б один телефон для бариста");

        // SQL-запит для перевірки результату
        String sql = """
        SELECT PhoneNumber 
        FROM EmployeeContact ec 
        JOIN Employees e ON e.Id = ec.EmployeeId 
        WHERE e.Type = 'Barista'
    """;

        // Отримання та перевірка результатів
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String phoneNumber = resultSet.getString("PhoneNumber");
                assertEquals(newPhone, phoneNumber, "Телефон має відповідати новому значенню");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Помилка під час виконання перевірочного SQL-запиту");
        }
    }

}
