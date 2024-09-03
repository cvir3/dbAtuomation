package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Utilities.DBConfig;
import Utilities.ReportManager;


public class Test_DBCount {

//    public static void main(String[] args) {
//        String connectionUrl = DBConfig.getConnectionUrl();
//        ReportManager reportManager = new ReportManager();
//        reportManager.startTest("Test_Data Count");
//
//        // SQL Query
//        String sqlQuery = "SELECT " +
//                "(SELECT COUNT(*) FROM Users_2023) AS Users_23_Count, " +
//                "(SELECT COUNT(*) FROM Users_2024) AS Users_24_Count;";
//
//        try (Connection conn = DriverManager.getConnection(connectionUrl);
//             Statement stmt = conn.createStatement();
//             ResultSet resultSet = stmt.executeQuery(sqlQuery)) {
//
//
//            // Check if ResultSet has data
//            if (resultSet.next()) {
//                int users23Count = resultSet.getInt("Users_23_Count");
//                int users24Count = resultSet.getInt("Users_24_Count");
//                reportManager.logInfo("Users_2023 Count: " + users23Count);
//                reportManager.logInfo("Users_2024 Count: " + users24Count); // Corrected to Users_2024
//            } else {
//                reportManager.logInfo("No data found in the ResultSet.");
//            }
//            reportManager.logPass("Database query executed successfully.");
//
//        } catch (SQLException e) {
//            reportManager.logFail("Error during database operation: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            reportManager.flush();
//        }
//    }
public void testDbCount(ReportManager reportManager) {
    String connectionUrl = DBConfig.getConnectionUrl();
    String sqlQuery = "SELECT " +
            "(SELECT COUNT(*) FROM Users_2023) AS Users_23_Count, " +
            "(SELECT COUNT(*) FROM Users_2024) AS Users_24_Count;";

    try (Connection conn = DriverManager.getConnection(connectionUrl);
         Statement stmt = conn.createStatement();
         ResultSet resultSet = stmt.executeQuery(sqlQuery)) {

        if (resultSet.next()) {
            int users23Count = resultSet.getInt("Users_23_Count");
            int users24Count = resultSet.getInt("Users_24_Count");
            reportManager.logInfo("Users_2023 Count: " + users23Count);
            reportManager.logInfo("Users_2024 Count: " + users24Count);
        } else {
            reportManager.logInfo("No data found in the ResultSet.");
        }
        reportManager.logPass("Database query executed successfully.");

    } catch (SQLException e) {
        reportManager.logFail("Error during database operation: " + e.getMessage());
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_Data Count");
        new Test_DBCount().testDbCount(reportManager);
        reportManager.flush();
    }

}
