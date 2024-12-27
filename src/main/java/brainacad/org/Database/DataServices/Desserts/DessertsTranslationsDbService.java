package brainacad.org.Database.DataServices.Desserts;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DessertsTranslationsDbService
{ ;
    private QueryExecutor queryExecutor;

    public DessertsTranslationsDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    public int addDessertTranslation(int dessertId, String languageCode, String name)
    {
        String sql = "INSERT INTO Desserts_Translations (DessertId, LanguageCode, Name) VALUES (?, ?, ?)";
        return queryExecutor.executeInsertAndReturnId(sql, dessertId, languageCode, name);
    }

    public int updateDessertTranslation(int translationId, String newName)
    {
        String sql = "UPDATE Desserts_Translations SET Name = ? WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, newName, translationId);
    }

    public int deleteDessertTranslation(int translationId)
    {
        String sql = "DELETE FROM Desserts_Translations WHERE Id = ?";
        return queryExecutor.executeUpdate(sql, translationId);
    }

    public void showAllDessertTranslations()
    {
        String sql = "SELECT * FROM Desserts_Translations";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next())
            {
                System.out.println("Translation ID: " + resultSet.getInt("Id") +
                        ", Dessert ID: " + resultSet.getInt("DessertId") +
                        ", Language: " + resultSet.getString("LanguageCode") +
                        ", Name: " + resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


