package brainacad.org.Database.DataServices.Employee;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeContactDbService
{
    private QueryExecutor queryExecutor;

    public EmployeeContactDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    public int addEmployeeContact(int employeeId, String phoneNumber, String email)
    {
        String sql = "INSERT INTO EmployeeContact (EmployeeId, PhoneNumber, Email) VALUES (?, ?, ?)";
        return queryExecutor.executeInsertAndReturnId(sql, employeeId, phoneNumber, email);
    }

    public int updateEmployeeContact(int contactId, String newPhoneNumber, String newEmail)
    {
        String sql = "UPDATE EmployeeContact SET PhoneNumber = ?, Email = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, newPhoneNumber, newEmail, contactId);
    }

    public int deleteEmployeeContact(int contactId)
    {
        String sql = "DELETE FROM EmployeeContact WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, contactId);
    }


    //ex3.2
    public int updatePhoneAndPostalCode_filterConfectioner(String phone, String postalCode)
    {
        String sql = """
        UPDATE EmployeeAddresses ea
        SET PostalCode = ?
        WHERE ea.EmployeeId IN (
            SELECT e.Id
            FROM Employees e
            WHERE e.Type = 'Confectioner'
        );

        UPDATE EmployeeContact ec
        SET PhoneNumber = ?
        WHERE ec.EmployeeId IN (
            SELECT e.Id
            FROM Employees e
            WHERE e.Type = 'Confectioner'
        );
    """;

        return queryExecutor.executeUpdate(sql, postalCode, phone);
    }


    //ex3.3
    public int updatePhone_filterBarista(String phone)
    {
        String sql = """
        UPDATE EmployeeContact ec
        SET PhoneNumber = ?
        WHERE ec.EmployeeId IN (
            SELECT e.Id 
            FROM Employees e 
            WHERE e.Type = 'Barista')
        """;

        return queryExecutor.executeUpdate(sql, phone);
    }

    public void showAllEmployeeContacts()
    {
        String sql = "SELECT * FROM EmployeeContact";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next()) {
                System.out.println("Contact ID: " + resultSet.getInt("Id") +
                        ", Employee ID: " + resultSet.getInt("EmployeeId") +
                        ", Phone: " + resultSet.getString("PhoneNumber") +
                        ", Email: " + resultSet.getString("Email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
