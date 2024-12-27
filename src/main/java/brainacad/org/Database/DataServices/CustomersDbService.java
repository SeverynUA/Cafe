package brainacad.org.Database.DataServices;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersDbService
{
    private QueryExecutor queryExecutor;

    public CustomersDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    //ex2.4
    public int addCustomer(String firstName, String lastName, String middleName, String birthDate, String email, String phoneNumber, double sale)
    {
    try
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);
        String sql = "INSERT INTO Customers (FirstName, LastName, MiddleName, BirthDate, Email, PhoneNumber, Sale) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return queryExecutor.executeInsertAndReturnId(sql, firstName, lastName, middleName, sqlDate, email, phoneNumber, sale);
    } catch (IllegalArgumentException e) {
        System.err.println("Помилка формату дати: " + e.getMessage());
        return -1;
    }
}

    //ex3.4
    public int updateCustomerSale(int customerId, double newSale)
    {
        String sql = "UPDATE Customers SET Sale = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, newSale, customerId);
    }

    //ex4.4
    public int deleteCustomer(int customerId)
    {
        String sql = "DELETE FROM Customers WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, customerId);
    }

    public void showAllCustomers()
    {
        String sql = "SELECT * FROM Customers";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("Customer ID: " + resultSet.getInt("Id") +
                        ", Name: " + resultSet.getString("FirstName") + " " + resultSet.getString("LastName") +
                        ", Email: " + resultSet.getString("Email") +
                        ", Sale: " + resultSet.getDouble("Sale") + "%");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
