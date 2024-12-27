package brainacad.org.Database.DataServices;

import brainacad.org.Database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguagesDbService
{
    private QueryExecutor queryExecutor;

    public LanguagesDbService()
    {
        queryExecutor = new QueryExecutor();
    }

    public String  addLanguage(String code, String name)
    {
        String sql = "INSERT INTO Languages (Code, Name) VALUES (?, ?)";
        return queryExecutor.executeInsertAndReturnChar(sql, code, name);
    }

    public int updateLanguageName(String code, String newName)
    {
        String sql = "UPDATE Languages SET Name = ? WHERE Code = ?";
        return queryExecutor.executeUpdate(sql, newName, code);
    }

    public int deleteLanguage(String code)
    {
        String sql = "DELETE FROM Languages WHERE Code = ?";
        return queryExecutor.executeUpdate(sql, code);
    }

    public void showAllLanguages()
    {
        String sql = "SELECT * FROM Languages";
        try (ResultSet resultSet = queryExecutor.executeQuery(sql))
        {
            while (resultSet.next()) {
                System.out.println("Language Code: " + resultSet.getString("Code") +
                        ", Name: " + resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
