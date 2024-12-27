package brainacad.org.Database;

import java.sql.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutor
{
    // Виконує запити типу INSERT, UPDATE, DELETE (зміна даних)
    public int executeUpdate(String sql, Object... params)
    {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {

            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: ");
            for (int i = 0; i < params.length; i++) {
                System.out.println("Param[" + (i + 1) + "]: " + params[i]);
                statement.setObject(i + 1, params[i]);
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Execution Error: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }



    // Виконує INSERT-запити і повертає згенерований ID
    public int executeInsertAndReturnId(String sql, Object... params)
    {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++)
            {
                statement.setObject(i + 1, params[i]);
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys())
                {
                    if (generatedKeys.next())
                    {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String executeInsertAndReturnChar(String sql, Object... params)
    {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys())
                {
                    if (generatedKeys.next()) {
                        return generatedKeys.getString(1);
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Виконує SELECT-запити і повертає ResultSet
    public ResultSet executeQuery(String sql, Object... params)
    {
        try {
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++)
            {
                statement.setObject(i + 1, params[i]);
            }

            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
