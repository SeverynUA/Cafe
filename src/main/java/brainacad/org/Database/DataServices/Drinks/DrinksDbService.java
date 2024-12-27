package brainacad.org.Database.DataServices.Drinks;

import brainacad.org.Database.QueryExecutor;
import brainacad.org.Models.Type_Drinks;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrinksDbService
{
    private QueryExecutor queryExecutor;

    public DrinksDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    public int addDrink(Type_Drinks type, double price)
    {
        String sql = "INSERT INTO Drinks (Type, Price) VALUES (?::drinktype, ?)";
        return queryExecutor.executeInsertAndReturnId(sql, type.getType(), price);
    }

    public int updateDrinkPrice(int drinkId, double newPrice)
    {
        String sql = "UPDATE Drinks SET Price = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, newPrice, drinkId);
    }

    //ex3.1
    public int updatePriceDrink_FilterType(Type_Drinks type , double newPrice)
    {
        String sql = "UPDATE Drinks SET Price = ? WHERE Type = ?::DrinkType";
        return queryExecutor.executeUpdate(sql, newPrice, type.toString());
    }

    public int deleteDrink(int drinkId)
    {
        String sql = "DELETE FROM Drinks WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, drinkId);
    }

    public void showAllDrinks()
    {
        String sql = "SELECT * FROM Drinks";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("Drink ID: " + resultSet.getInt("Id") +
                        ", Type: " + resultSet.getString("Type") +
                        ", Price: $" + resultSet.getDouble("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ex5.1
    public void showAllDrinksWithNames(String languageCode) {
        String sql = """
        SELECT d.Id, d.Type, d.Price, 
               COALESCE(MAX(dt.Name), 'Без назви') AS DrinkName
        FROM Drinks d
        LEFT JOIN DrinkTranslations dt 
        ON d.Id = dt.DrinkId AND dt.LanguageCode = ?
        GROUP BY d.Id, d.Type, d.Price
    """;

        try (ResultSet resultSet = queryExecutor.executeQuery(sql, languageCode)) {
            System.out.println("Напої:");
            while (resultSet.next())
            {
                int id = resultSet.getInt("Id");
                String type = resultSet.getString("Type");
                double price = resultSet.getDouble("Price");
                String name = resultSet.getString("DrinkName");

                if (name == null) {
                    continue;
                }

                System.out.println("ID: " + id + ", Назва: " + name +
                        ", Тип: " + type + ", Ціна: $" + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
