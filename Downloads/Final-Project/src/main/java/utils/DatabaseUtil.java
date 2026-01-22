package utils;

import config.ConfigManager;

import java.sql.*;

public class DatabaseUtil {

    private static Connection conn;

    private DatabaseUtil() {} // private constructor to prevent instantiation

    // ===================== CONNECT TO DB =====================
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            String dbUrl = ConfigManager.getProperty("dbUrl");
            String dbUser = ConfigManager.getProperty("dbUser");
            String dbPassword = ConfigManager.getProperty("dbPassword");

            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
        return conn;
    }

    // ===================== EXECUTE SELECT QUERY =====================
    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        return stmt.executeQuery(query);
    }

    // ===================== EXECUTE INSERT/UPDATE/DELETE =====================
    public static int executeUpdate(String query) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeUpdate(query);
    }

    // ===================== CLOSE CONNECTION =====================
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null; // reset for next usage
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
