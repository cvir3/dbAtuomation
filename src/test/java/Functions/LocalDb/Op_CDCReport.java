package Functions.LocalDb;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_CDCReport {
    public void cdcReport(ReportManager reportManager) throws InterruptedException {
        String connectionUrl = DBConfig.getConnectionUrl();
        String sqlQuery = "select * from cdc.dbo_Users_2023_CT";
        Thread.sleep(5000);

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sqlQuery)) {
            StringBuilder htmlTable = new StringBuilder();
            htmlTable.append("<table border='1'><tr>")
                    .append("<th>__$start_lsn</th><th>__$end_lsn</th><th>__$seqval</th><th>__$operation</th>")
                    .append("<th>__$update_mask</th><th>UserID</th><th>first_name</th><th>last_name</th>")
                    .append("<th>email</th><th>gender</th><th>mobile_number</th><th>__$command_id</th>")
                    .append("</tr>");

            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
                htmlTable.append("<tr>")
                        .append("<td>").append(resultSet.getString("__$start_lsn")).append("</td>")
                        .append("<td>").append(resultSet.getString("__$end_lsn")).append("</td>")
                        .append("<td>").append(resultSet.getString("__$seqval")).append("</td>")
                        .append("<td>").append(resultSet.getString("__$operation")).append("</td>")
                        .append("<td>").append(resultSet.getString("__$update_mask")).append("</td>")
                        .append("<td>").append(resultSet.getString("UserID")).append("</td>")
                        .append("<td>").append(resultSet.getString("first_name")).append("</td>")
                        .append("<td>").append(resultSet.getString("last_name")).append("</td>")
                        .append("<td>").append(resultSet.getString("email")).append("</td>")
                        .append("<td>").append(resultSet.getString("gender")).append("</td>")
                        .append("<td>").append(resultSet.getString("mobile_number")).append("</td>")
                        .append("<td>").append(resultSet.getString("__$command_id")).append("</td>")
                        .append("</tr>");
            }

            htmlTable.append("</table>");

            if (rowCount == 0) {
                reportManager.logFail("No entries found in the CDC table.");
            } else {
                reportManager.logPass("Entries found in the CDC table: " + rowCount);
                reportManager.logInfo(htmlTable.toString());
            }

        } catch (SQLException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            reportManager.flush();
        }
    }
}
