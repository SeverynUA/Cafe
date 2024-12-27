package brainacad.org.DatabaseTest.Requests.Employees;
import brainacad.org.Database.DataServices.Employee.EmployeesDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import brainacad.org.Models.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeesDbServiceTest extends BaseTest {

    private final EmployeesDbService employeesDbService = new EmployeesDbService();
    private final QueryExecutor queryExecutor = new QueryExecutor();

    @Test
    void testAddEmployee() {
        String firstName = "John";
        String lastName = "Doe";
        String middleName = "Edward";
        String position = Position.Barista.getStatus(); // Перетворюємо на рядок
        LocalDate hireDate = LocalDate.parse("2022-01-15");

        int employeeId = employeesDbService.addEmployee(firstName, lastName, middleName,Position.Barista, hireDate);
        assertTrue(employeeId > 0, "Співробітник має бути доданий із валідним ID");
    }

    @Test
    void testDeleteEmployee() {
        String firstName = "Jane";
        String lastName = "Smith";
        String middleName = "Ann";
        String position = "Waiter";
        LocalDate hireDate = LocalDate.parse("2023-06-20");

        int employeeId = employeesDbService.addEmployee(firstName, lastName, middleName, Position.Waiter, hireDate);

        int rowsDeleted = employeesDbService.deleteEmployee(employeeId);
        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");
    }

    @Test
    void testShowAllEmployees()
    {
        LocalDate hireDate1 = LocalDate.parse("2020-11-10");
        LocalDate hireDate2 = LocalDate.parse("2021-03-05");
        employeesDbService.addEmployee("Alice", "Brown", "Rose", Position.Confectioner, hireDate1);
        employeesDbService.addEmployee("Bob", "Green", "Michael", Position.Barista, hireDate2);

        System.out.println("Список співробітників:");
        employeesDbService.showAllEmployees();
    }

    @Test
    void testDeleteEmployee_Waiter() {
        // Додавання тестового запису
        int employeeId = queryExecutor.executeInsertAndReturnId(
                "INSERT INTO Employees (FirstName, LastName, Type) VALUES ('Test', 'Waiter', 'Waiter')"
        );

        LocalDate hireDate2 = LocalDate.parse("2021-03-05");
        employeesDbService.addEmployee("Bob", "Green", "Michael", Position.Barista, hireDate2);
        // Перевірка, що запис додано
        assertTrue(employeeId > 0, "Співробітника має бути додано");

        // Видалення запису
        int deletedRows = employeesDbService.deleteEmployee_Waiter(employeeId);

        // Перевірка, що запис видалено
        assertEquals(1, deletedRows, "Має бути видалено 1 запис");
    }


    @Test
    void testDeleteEmployee_Barista() {
        // Додавання тестового запису
        int employeeId = queryExecutor.executeInsertAndReturnId(
                "INSERT INTO Employees (FirstName, LastName, Type) VALUES ('Test', 'Barista', 'Barista')"
        );

        LocalDate hireDate2 = LocalDate.parse("2021-03-05");
        employeesDbService.addEmployee("Bob", "Green", "Michael", Position.Barista, hireDate2);
        // Перевірка, що запис додано
        assertTrue(employeeId > 0, "Співробітника має бути додано");

        // Видалення запису
        int deletedRows = employeesDbService.deleteEmployee_Barista(employeeId);

        // Перевірка, що запис видалено
        assertEquals(1, deletedRows, "Має бути видалено 1 запис");
    }

    @Test
    void testShowAllEmployees_Barista() {
        LocalDate hireDate1 = LocalDate.parse("2020-11-10");
        LocalDate hireDate2 = LocalDate.parse("2021-03-05");
        employeesDbService.addEmployee("Alice", "Brown", "Rose", Position.Confectioner, hireDate1);
        employeesDbService.addEmployee("Bob", "Green", "Michael", Position.Barista, hireDate2);

        System.out.println("Список співробітників:");
        employeesDbService.showAllEmployees_Barista();
    }

    @Test
    void testShowAllEmployees_Waiter() {
        LocalDate hireDate1 = LocalDate.parse("2020-11-10");
        LocalDate hireDate2 = LocalDate.parse("2021-03-05");
        employeesDbService.addEmployee("Alice", "Brown", "Rose", Position.Waiter, hireDate1);
        employeesDbService.addEmployee("Bob", "Green", "Michael", Position.Barista, hireDate2);

        System.out.println("Список співробітників:");
        employeesDbService.showAllEmployees_Waiter();
    }


}
