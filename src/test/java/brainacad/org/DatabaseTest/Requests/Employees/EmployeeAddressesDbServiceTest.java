package brainacad.org.DatabaseTest.Requests.Employees;

import brainacad.org.Database.DataServices.Employee.EmployeeAddressesDbService;
import brainacad.org.Database.DatabaseUtil;
import brainacad.org.DatabaseTest.Requests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAddressesDbServiceTest extends BaseTest {

    private final EmployeeAddressesDbService employeeAddressesDbService = new EmployeeAddressesDbService();


    @Test
    void testAddEmployeeAddress() {
        int employeeId = 1;
        String addressLine1 = "123 Main St";
        String addressLine2 = "Apt 4B";
        String city = "New York";
        String postalCode = "10001";
        String country = "USA";

        int addressId = employeeAddressesDbService.addEmployeeAddress(employeeId, addressLine1, addressLine2, city, postalCode, country);
        assertTrue(addressId > 0, "Адреса має бути додана із валідним ID");
    }

    @Test
    void testDeleteEmployeeAddress() {
        int employeeId = 1;
        String addressLine1 = "123 Main St";
        String addressLine2 = "Apt 4B";
        String city = "New York";
        String postalCode = "10001";
        String country = "USA";

        int addressId = employeeAddressesDbService.addEmployeeAddress(employeeId, addressLine1, addressLine2, city, postalCode, country);

        int rowsDeleted = employeeAddressesDbService.deleteEmployeeAddress(addressId);
        assertEquals(1, rowsDeleted, "Має бути видалено 1 рядок");
    }
}
