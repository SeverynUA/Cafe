package brainacad.org.Database.DataServices.Employee;

import brainacad.org.Database.QueryExecutor;
import brainacad.org.Models.Position;
import brainacad.org.Services.EmployeeService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeesDbService
{
    private QueryExecutor queryExecutor;

    public EmployeesDbService() {
        queryExecutor = new QueryExecutor();
    }

    //ex2.2 , 2.3
    public int addEmployee(String firstName, String lastName, String middleName, Position position, LocalDate hireDate)
    {
        String sql = "INSERT INTO Employees (FirstName, LastName, MiddleName, Type, HireDate) VALUES (?, ?, ?, ?::jobposition,?)";
        return queryExecutor.executeInsertAndReturnId(sql, firstName, lastName, middleName, position.getStatus(), hireDate);
    }


    public int deleteEmployee(int employeeId)
    {
        String sql = "DELETE FROM Employees WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, employeeId);
    }

    //ex4.2
    public int deleteEmployee_Waiter(int employeeId)
    {
        String sql = "DELETE FROM Employees WHERE Id = ? AND Type = 'Waiter'";
        return queryExecutor.executeUpdate(sql, employeeId);
    }

    //ex4.3
    public int deleteEmployee_Barista(int employeeId)
    {
        String sql = "DELETE FROM Employees WHERE Id = ? AND Type = 'Barista' ";
        return queryExecutor.executeUpdate(sql, employeeId);
    }

    public void showAllEmployees()
    {
        String sql = "SELECT * FROM Employees";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("Id") +
                        ", Name: " + resultSet.getString("FirstName") +
                        " " + resultSet.getString("LastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ex5.3
    public void showAllEmployees_Barista()
    {
        String sql = "SELECT * FROM Employees WHERE Type = 'Barista'";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("ID: " + resultSet.getInt("Id") +
                        ", Name: " + resultSet.getString("FirstName") +
                        " " + resultSet.getString("LastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ex5.4
    public void showAllEmployees_Waiter()
    {
        String sql = "SELECT * FROM Employees WHERE Type = 'Waiter'";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("ID: " + resultSet.getInt("Id") +
                        ", Name: " + resultSet.getString("FirstName") +
                        " " + resultSet.getString("LastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

