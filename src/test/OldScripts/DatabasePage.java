import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePage {

    private String connectionUrl;

    public DatabasePage(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void createTable(String tableName) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement()) {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String createTableSQL = "CREATE TABLE " + tableName + " (" +
                    "id INT PRIMARY KEY IDENTITY(1,1), " +
                    "randomNumber INT)";
            stmt.executeUpdate(createTableSQL);
        }
    }

    public void insertRandomData(String tableName) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement()) {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String insertDataSQL = "INSERT INTO " + tableName + " (randomNumber) VALUES (RAND()*1000)";
            stmt.executeUpdate(insertDataSQL);
        }
    }
}
