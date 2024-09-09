package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Utilities.ReportManager;
import Utilities.DBConfig;


public class Test_Comparison {

    public static void main(String[] args) {

        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_Compair Execution");
        String connectionUrl = DBConfig.getConnectionUrl();
        //String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement()) {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // SQL query to compare Users_2023 and Users_2024
            String compareUsersSQL = "SELECT " +
                    "u23.UserID AS UserID_2023, " +
                    "u23.FirstName AS FirstName_2023, " +
                    "u23.LastName AS LastName_2023, " +
                    "u23.Gender AS Gender_2023, " +
                    "u24.UserID AS UserID_2024, " +
                    "u24.FirstName AS FirstName_2024, " +
                    "u24.LastName AS LastName_2024, " +
                    "u24.Gender AS Gender_2024 " +
                    "FROM Users_2023 u23 " +
                    "FULL OUTER JOIN Users_2024 u24 ON u23.UserID = u24.UserID " +
                    "WHERE u23.FirstName <> u24.FirstName " +
                    "OR u23.LastName <> u24.LastName " +
                    "OR u23.Gender <> u24.Gender " +
                    "OR u23.UserID IS NULL " +
                    "OR u24.UserID IS NULL;";

            // Execute the SQL query
            ResultSet resultSet = stmt.executeQuery(compareUsersSQL);

            // Process the result set
            while (resultSet.next()) {
                String userId2023 = resultSet.getString("UserID_2023");
                String firstName2023 = resultSet.getString("FirstName_2023");
                String lastName2023 = resultSet.getString("LastName_2023");
                String gender2023 = resultSet.getString("Gender_2023");
                String userId2024 = resultSet.getString("UserID_2024");
                String firstName2024 = resultSet.getString("FirstName_2024");
                String lastName2024 = resultSet.getString("LastName_2024");
                String gender2024 = resultSet.getString("Gender_2024");

                // Log the differences
                reportManager.logInfo("2023: [" + userId2023 + ", " + firstName2023 + ", " + lastName2023 + ", " + gender2023 + "]");
                reportManager.logInfo("2024: [" + userId2024 + ", " + firstName2024 + ", " + lastName2024 + ", " + gender2024 + "]");

            }

            reportManager.logPass("Comparison completed successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            reportManager.flush();
        }
    }
}
