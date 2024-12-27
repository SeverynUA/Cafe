package brainacad.org.Database.DataServices.Drinks;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrinkTranslationsDbService
{
    private QueryExecutor queryExecutor;

    public DrinkTranslationsDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    public int addDrinkTranslation(int drinkId, String languageCode, String name)
    {
        String sql = "INSERT INTO DrinkTranslations (DrinkId, LanguageCode, Name) VALUES (?, ?, ?)";
        return queryExecutor.executeInsertAndReturnId(sql, drinkId, languageCode, name);
    }

    public int updateDrinkTranslation(int translationId, String newName)
    {
        String sql = "UPDATE DrinkTranslations SET Name = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, newName, translationId);
    }

    public int deleteDrinkTranslation(int translationId)
    {
        String sql = "DELETE FROM DrinkTranslations WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, translationId);
    }

    public void showAllDrinkTranslations()
    {
        String sql = "SELECT * FROMDrinkTranslations";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("Translation ID: " + resultSet.getInt("Id") +
                        ", Drink ID: " + resultSet.getInt("DrinkId") +
                        ", Language: " + resultSet.getString("LanguageCode") +
                        ", Name: " + resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
