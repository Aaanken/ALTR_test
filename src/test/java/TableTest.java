import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TableTest {


        private final static String URL = "jdbc:mysql://127.0.0.1:3306";
        private final static String USER = "root";
        private final static String PASSWORD = "mysecretpassword";
        private Connection connection;
        private final String dbName = "anna_test";
        private final String tableName = "mytable";

        @Before
        public void setUp() throws SQLException {
            connection = CreateTable.getConnection(URL, USER, PASSWORD);
        }

        @After
        public void tearDown() throws SQLException {
            if (connection != null) {
                connection.close();
            }
        }

        @Test
        public void testCreateDatabase() throws SQLException {
            CreateTable.createDB(dbName);
            PreparedStatement statement = connection.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?");
            statement.setString(1, dbName);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(dbName, resultSet.getString(1));
        }

        @Test
        public void testCreateTable() throws SQLException {
            CreateTable.createTable(dbName, tableName);
            PreparedStatement statement = connection.prepareStatement("SHOW TABLES LIKE ?");
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(tableName, resultSet.getString(1));
        }

        @Test
        public void testInsertData() throws SQLException {
            CreateTable.createTable(dbName, tableName);
            String selectData = String.format("SELECT COUNT(*) FROM %s.%s", dbName, tableName);
            PreparedStatement statement = connection.prepareStatement(selectData);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            int countBeforeInsert = resultSet.getInt(1);
            resultSet = CreateTable.setInsertData(dbName, tableName);
            assertTrue(resultSet.next());
            int countAfterInsert = resultSet.getInt(1);
            assertEquals(countBeforeInsert + 1, countAfterInsert);
        }

        @Test
        public void testDeleteData() throws SQLException {
            String selectData = "SELECT COUNT(*) FROM anna_test.mytable WHERE column2 = 'value2'";
            PreparedStatement statement = connection.prepareStatement(selectData);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            int countBeforeDelete = resultSet.getInt(1);

            String deleteData = "DELETE FROM anna_test.mytable WHERE column2 = 'value2'";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteData);
            int rowsAffected = deleteStatement.executeUpdate();
            assertEquals(countBeforeDelete, rowsAffected);

            resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            int countAfterDelete = resultSet.getInt(1);

            assertEquals(countBeforeDelete - rowsAffected, countAfterDelete);
        }
    @Test
    public void testUpdateData() throws SQLException {
        CreateTable.createTable(dbName, tableName);
        String insertData = String.format("INSERT INTO %s.%s (column1, column2) VALUES (?, ?)", dbName, tableName);
        PreparedStatement statement = connection.prepareStatement(insertData);
        statement.setString(1, "value1");
        statement.setString(2, "value2");
        statement.executeUpdate();

        String updateData = String.format("UPDATE %s.%s SET column2 = ? WHERE column1 = ?", dbName, tableName);
        PreparedStatement updateStatement = connection.prepareStatement(updateData);
        updateStatement.setString(1, "updated_value2");
        updateStatement.setString(2, "value1");
        int rowsAffected = updateStatement.executeUpdate();
        assertEquals(1, rowsAffected);

        String selectData = String.format("SELECT column2 FROM %s.%s WHERE column1 = ?", dbName, tableName);
        PreparedStatement selectStatement = connection.prepareStatement(selectData);
        selectStatement.setString(1, "value1");
        ResultSet resultSet = selectStatement.executeQuery();
        assertTrue(resultSet.next());
        assertEquals("updated_value2", resultSet.getString(1));
    }
    @Test
    public void testSelectData() throws SQLException {
        CreateTable.createTable(dbName, tableName);
        String insertData = String.format("INSERT INTO %s.%s (column1, column2) VALUES (?, ?)", dbName, tableName);
        PreparedStatement statement = connection.prepareStatement(insertData);
        statement.setString(1, "value1");
        statement.setString(2, "value2");
        statement.executeUpdate();

        String selectData = String.format("SELECT column1, column2 FROM %s.%s WHERE column1 = ?", dbName, tableName);
        PreparedStatement selectStatement = connection.prepareStatement(selectData);
        selectStatement.setString(1, "value1");
        ResultSet resultSet = selectStatement.executeQuery();
        assertTrue(resultSet.next());
        assertEquals("value1", resultSet.getString(1));
        assertEquals("value2", resultSet.getString(2));
    }


//    @Test
//    public void testDeleteAllData() throws SQLException {
//        CreateTable.createTable(dbName, tableName);
//        String selectData = String.format("SELECT COUNT(*) FROM %s.%s", dbName, tableName);
//        PreparedStatement statement = connection.prepareStatement(selectData);
//        ResultSet resultSet = statement.executeQuery();
//        assertTrue(resultSet.next());
//        int countBeforeDelete = resultSet.getInt(1);
//
//        String deleteData = "DELETE FROM " + dbName + "." + tableName;
//        PreparedStatement deleteStatement = connection.prepareStatement(deleteData);
//        int rowsAffected = deleteStatement.executeUpdate();
//        assertEquals(countBeforeDelete, rowsAffected);
//
//        resultSet = statement.executeQuery();
//        assertTrue(resultSet.next());
//        int countAfterDelete = resultSet.getInt(1);
//
//        assertEquals(0, countAfterDelete);
//    }




}
