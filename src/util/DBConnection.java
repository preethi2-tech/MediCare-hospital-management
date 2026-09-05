package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String URL = System.getProperty("medicare.db.url", "jdbc:mysql://localhost:3306/medicare_db?useSSL=false&serverTimezone=UTC");
    private static final String USER = System.getProperty("medicare.db.user", "root");
        private static final String PASSWORD = System.getProperty(
            "medicare.db.password",
            System.getenv().getOrDefault("MEDICARE_DB_PASSWORD", "yourpassword"));
    private DBConnection() {}
    public static Connection getConnection() throws SQLException { return DriverManager.getConnection(URL, USER, PASSWORD); }
    public static boolean testConnection() { try (Connection ignored = getConnection()) { return true; } catch (SQLException ex) { return false; } }
}
