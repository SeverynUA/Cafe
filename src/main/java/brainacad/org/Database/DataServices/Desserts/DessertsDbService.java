package brainacad.org.Database.DataServices.Desserts;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DessertsDbService
{
    private QueryExecutor queryExecutor;

    public DessertsDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    //ex2.1
    public int addDessert(double price)
    {
        String sql = "INSERT INTO Desserts (Price) VALUES (?) RETURNING Id";
        return queryExecutor.executeInsertAndReturnId(sql, price);
    }

    public int updateDessertPrice(int dessertId, double newPrice)
    {
        String sql = "UPDATE Desserts SET Price = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, newPrice, dessertId);
    }

    public int deleteDessert(int dessertId)
    {
        String sql = "DELETE FROM Desserts WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, dessertId);
    }

    public void showAllDesserts()
    {
        String sql = "SELECT * FROM Desserts";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("Dessert ID: " + resultSet.getInt("Id") +
                        ", Price: $" + resultSet.getDouble("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ex5.2
    public void showAllDessertsWithNames(String languageCode)
    {
        String sql = """
        SELECT d.Id, d.Price, 
               COALESCE(MAX(dt.Name), NULL) AS DessertName
        FROM Desserts d
        LEFT JOIN Desserts_Translations dt 
        ON d.Id = dt.DessertId AND dt.LanguageCode = ?
        GROUP BY d.Id, d.Price
    """;

        try (ResultSet resultSet = queryExecutor.executeQuery(sql, languageCode))
        {
            System.out.println("Десерти:");
            while (resultSet.next())
            {
                int id = resultSet.getInt("Id");
                double price = resultSet.getDouble("Price");
                String name = resultSet.getString("DessertName");

                if (name == null) {
                    continue;
                }

                System.out.println("ID: " + id + ", Назва: " + name + ", Ціна: $" + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
