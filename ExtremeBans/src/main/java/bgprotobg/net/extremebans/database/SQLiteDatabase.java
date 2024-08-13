package bgprotobg.net.extremebans.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase {

    private static final String URL = "jdbc:sqlite:plugins/ExtremeBans/punishments.db";
    private static SQLiteDatabase instance;
    private Connection connection;

    private SQLiteDatabase() {
        try {
            connection = DriverManager.getConnection(URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SQLiteDatabase getInstance() {
        if (instance == null) {
            instance = new SQLiteDatabase();
        }
        return instance;
    }

    private void createTables() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS punishments (" +
                "uuid TEXT PRIMARY KEY," +
                "type TEXT NOT NULL," +
                "reason TEXT NOT NULL," +
                "expire_time INTEGER" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
