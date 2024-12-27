package brainacad.org.Database.DataServices.Employee;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeAddressesDbService
{
    private QueryExecutor queryExecutor;

    public EmployeeAddressesDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    public int addEmployeeAddress(int employeeId, String addressLine1, String addressLine2, String city, String postalCode, String country)
    {
        String sql = "INSERT INTO EmplyoeeAddresses (EmployeeId, AddressLine1, AddressLine2, City, PostalCode, Country) VALUES (?, ?, ?, ?, ?, ?)";
        return queryExecutor.executeInsertAndReturnId(sql, employeeId, addressLine1, addressLine2, city, postalCode, country);
    }

    public int updateEmployeeAddress(int addressId, String addressLine1, String addressLine2, String city, String postalCode, String country)
    {
        String sql = "UPDATE EmplyoeeAddresses SET AddressLine1 = ?, AddressLine2 = ?, City = ?, PostalCode = ?, Country = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, addressLine1, addressLine2, city, postalCode, country, addressId);
    }

    public int deleteEmployeeAddress(int addressId)
    {
        String sql = "DELETE FROM EmplyoeeAddresses WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, addressId);
    }

    public void showAllEmployeeAddresses()
    {
        String sql = "SELECT * FROM EmplyoeeAddresses";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("Address ID: " + resultSet.getInt("Id") +
                        ", Employee ID: " + resultSet.getInt("EmployeeId") +
                        ", Address: " + resultSet.getString("AddressLine1") +
                        ", " + resultSet.getString("AddressLine2") +
                        ", City: " + resultSet.getString("City") +
                        ", Postal Code: " + resultSet.getString("PostalCode") +
                        ", Country: " + resultSet.getString("Country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
