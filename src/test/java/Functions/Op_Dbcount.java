package Functions;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_Dbcount {
    public void testDbCount(ReportManager reportManager) {
        String connectionUrl = DBConfig.getConnectionUrl();

        String sqlQuery = "SELECT " +
                "(SELECT COUNT(*) FROM Users_2023) AS Users_23_Count";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sqlQuery)) {

            if (resultSet.next()) {
                int users23Count = resultSet.getInt("Users_23_Count");

                reportManager.logInfo("Users_2023 Count: " + users23Count);

            } else {
                reportManager.logInfo("No data found in the ResultSet.");
            }
            reportManager.logPass("Database query executed successfully.");

        } catch (SQLException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public void testDbCount(ReportManager reportManager) {
//        String connectionUrl = DBConfig.getConnectionUrl();
//        String sqlQuery = "SELECT " +
//                "(SELECT COUNT(*) FROM Users_2023) AS Users_23_Count, " +
//                "(SELECT COUNT(*) FROM Users_2024) AS Users_24_Count;";
//
//        try (Connection conn = DriverManager.getConnection(connectionUrl);
//             Statement stmt = conn.createStatement();
//             ResultSet resultSet = stmt.executeQuery(sqlQuery)) {
//
//            if (resultSet.next()) {
//                int users23Count = resultSet.getInt("Users_23_Count");
//                int users24Count = resultSet.getInt("Users_24_Count");
//                reportManager.logInfo("Users_2023 Count: " + users23Count);
//                reportManager.logInfo("Users_2024 Count: " + users24Count);
//            } else {
//                reportManager.logInfo("No data found in the ResultSet.");
//            }
//            reportManager.logPass("Database query executed successfully.");
//
//        } catch (SQLException e) {
//            reportManager.logFail("Error during database operation: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

}
