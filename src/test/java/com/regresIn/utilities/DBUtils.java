package com.regresIn.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pthomas3
 * from;
 * https://github.com/intuit/karate/blob/master/karate-demo/src/main/java/com/intuit/karate/demo/util/DbUtils.java
 */

public class DBUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    /**
     * @param db Create a jdbc connection using the url, username, password
     */
    public static void createConnection(String db) {
        String url = "jdbc:mariadb://maria.test.finspire.tech:3306/" + db;
        String username = "finspire_user";
        String password = "beniYakmaKendini357YakHerseyiYak!!!!";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close existing jdbc connection, destroy the Statement, ResultSet objects
     */
    public static void destroyConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param query
     * @param db
     * @return returns a single cell value. If the results in multiple rows and/or
     * columns of data, only first column of the first row will be returned.
     * The rest of the data will be ignored
     */
    public static Object getCellValue(String query, String db) {
        return getQueryResultList(query, db).get(0).get(0);
    }

    /**
     * @param query
     * @param db
     * @return returns a list of Strings which represent a row of data. If the query
     * results in multiple rows and/or columns of data, only first row will
     * be returned. The rest of the data will be ignored
     */
    public static List<Object> getRowList(String query, String db) {
        return getQueryResultList(query, db).get(0);
    }

    /**
     * @param query
     * @param db
     * @return returns a map which represent a row of data where key is the column
     * name. If the query results in multiple rows and/or columns of data,
     * only first row will be returned. The rest of the data will be ignored
     */
    public static Map<String, Object> getRowMap(String query, String db) {
        Map<String, Object> map = getQueryResultMap(query, db).get(0);
        return map;
    }

    /**
     * @param query
     * @param db
     * @return returns query result in a list of lists where outer list represents
     * collection of rows and inner lists represent a single row
     */
    public static List<List<Object>> getQueryResultList(String query, String db) {
        List<List<Object>> rowList = new ArrayList<>();
        try {
            runQuery(query, db);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }
                rowList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroyConnection();
        }
        return rowList;
    }

    /**
     * @param query
     * @param column
     * @param db
     * @return list of values of a single column from the result set
     */
    public static List<Object> getColumnData(String query, String column, String db) {
        List<Object> rowList = new ArrayList<>();
        try {
            runQuery(query, db);
            while (resultSet.next()) {
                rowList.add(resultSet.getObject(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroyConnection();
        }
        return rowList;
    }

    /**
     * @param query
     * @param db
     * @return returns query result in a list of maps where the list represents
     * collection of rows and a map represents represent a single row with
     * key being the column name
     */
    public static List<Map<String, Object>> getQueryResultMap(String query, String db) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        try {
            runQuery(query, db);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroyConnection();
        }
        return rowList;
    }

    /**
     * @param query
     * @param db
     * @return List of columns returned in result set
     */
    public static List<String> getColumnNames(String query, String db) {
        List<String> columns = new ArrayList<>();
        try {
            runQuery(query, db);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroyConnection();
        }
        return columns;
    }

    public static void executeQuery(String query, String db) {
        try {
            runQuery(query, db);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroyConnection();
        }
    }

    /**
     * Private method which is responsible from creating connection and running the query
     * @param query
     * @param db
     * @throws SQLException
     */
    private static void runQuery(String query, String db) throws SQLException {
        createConnection(db);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(query);
    }

    public static int getRowCount() throws Exception {
        resultSet.last();
        int rowCount = resultSet.getRow();
        return rowCount;
    }

}
