package Functions;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_Dbcount_PF1103 {
    public void QADbCount(ReportManager reportManager) {
        String connectionUrl = DBConfig.getConnectionUrl();

        String countQuery = "SELECT " +
                "(SELECT COUNT(*) FROM pf1103) AS pf1103";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(countQuery)) {

            if (resultSet.next()) {
                int pf1103Count = resultSet.getInt("pf1103");

                reportManager.logInfo("pf1103 Count: " + pf1103Count);

            } else {
                reportManager.logInfo("No data found in the ResultSet.");
            }
            reportManager.logPass("Database query executed successfully.");

        } catch (SQLException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
