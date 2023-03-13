import java.sql.*;

public class CreateTable {
    private static Connection connection;
    private static PreparedStatement statement;
    private static String createDatabase = "CREATE DATABASE IF NOT EXISTS %s";
    private static String createTable = "CREATE TABLE IF NOT EXISTS %s.%s (id INT NOT NULL AUTO_INCREMENT, " +
            "column1 VARCHAR(50), column2 VARCHAR(50), column3 VARCHAR(50), " +
            "column4 VARCHAR(50), column5 VARCHAR(50), PRIMARY KEY (id), " +
            "INDEX idx_column1 (column1), INDEX idx_column3 (column3))";
    private static String insertData = "INSERT INTO %s.%s (column1, column2, column3, column4, column5) " +
            "VALUES (?, ?, ?, ?, ?)";

    public static Connection getConnection(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    public static void createDB(String schemaName) {

        try {
            statement = connection.prepareStatement(String.format(createDatabase,schemaName));
            statement.execute();
            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createTable(String schemaName,String tableName) {
        try {
            createDB(schemaName);
            statement = connection.prepareStatement(String.format(createTable,schemaName,tableName));
            statement.execute();
            System.out.println("Table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static ResultSet setInsertData(String schemaName,String tableName) {
        ResultSet resultSet=null;
        for (int i = 1; i <= 10; i++) {
            try {
                statement = connection.prepareStatement(String.format(insertData,schemaName,tableName));
                statement.setString(1, "value" + i);
                statement.setString(2, "value" + (i + 1));
                statement.setString(3, "value" + (i + 2));
                statement.setString(4, "value" + (i + 3));
                statement.setString(5, "value" + (i + 4));
                statement.executeUpdate();
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Data inserted successfully!");
        return resultSet;
    }

}