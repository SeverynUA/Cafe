package brainacad.org.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class DatabaseUtil
{
    private static final String URL = ConfigLoader.getProperty("db.url");
    private static final String USER = ConfigLoader.getProperty("db.username");
    private static final String PASSWORD = ConfigLoader.getProperty("db.password");
    private static final String SQL_FILE_PATH = ConfigLoader.getProperty("db.sql_file_path");

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void initDatabase(String databaseName)
    {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement())
        {

            String checkDbQuery = String.format("SELECT 1 FROM pg_database WHERE datname = '%s'", databaseName);
            ResultSet resultSet = statement.executeQuery(checkDbQuery);

            if (!resultSet.next()) {
                String createDbQuery = String.format("CREATE DATABASE %s", databaseName);
                statement.executeUpdate(createDbQuery);
                System.out.printf("База даних '%s' успішно створена.%n", databaseName);
            } else {
                System.out.printf("База даних '%s' вже існує.%n", databaseName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        executeSQLFile();
    }

    public static void executeSQLFile()
    {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(SQL_FILE_PATH)))
        {

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                sql.append(line).append("\n");
            }

            statement.execute(sql.toString());
            System.out.println("SQL-файл успішно виконаний!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetDatabase()
    {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement())
        {
            String dropTablesQuery = """
            DO $$ DECLARE
            r RECORD;
            BEGIN
                FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
                    EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
                END LOOP;
            END $$;
        """;

            statement.execute(dropTablesQuery);
            System.out.println("Всі таблиці успішно видалені.");

            String dropEnumsQuery = """
            DO $$ DECLARE
            r RECORD;
            BEGIN
                FOR r IN (SELECT typname FROM pg_type WHERE typtype = 'e' AND typnamespace = 'public'::regnamespace) LOOP
                    EXECUTE 'DROP TYPE IF EXISTS ' || quote_ident(r.typname) || ' CASCADE';
                END LOOP;
            END $$;
        """;

            statement.execute(dropEnumsQuery);
            System.out.println("Всі ENUM типи успішно видалені.");

            executeSQLFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}