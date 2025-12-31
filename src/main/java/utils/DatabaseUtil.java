package utils;

import config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtil {
    private static Connection conn;

    private DatabaseUtil() {}

    // Connect to DB
    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(
                    ConfigManager.getProperty("dbUrl"),
                    ConfigManager.getProperty("dbUser"),
                    ConfigManager.getProperty("dbPassword")
            );
        }
        return conn;
    }

    // Execute SELECT query
    public static ResultSet executeQuery(String query) throws Exception {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(query);
    }

    // Execute INSERT/UPDATE/DELETE
    public static int executeUpdate(String query) throws Exception {
        Statement stmt = getConnection().createStatement();
        return stmt.executeUpdate(query);
    }

    // Close connection
    public static void closeConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
