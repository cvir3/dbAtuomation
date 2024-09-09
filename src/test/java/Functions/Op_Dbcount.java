package Functions;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_Dbcount {
    public void testDbCount(ReportManager reportManager) {
        String connectionUrl = DBConfig.getConnectionUrl();

        String countQuery = "SELECT " +
                "(SELECT COUNT(*) FROM Users_2023) AS Users_23_Count, " +
                "(SELECT COUNT(*) FROM Users_2024) AS Users_24_Count;";

        String sqlQuery2023 = "SELECT * FROM Users_2023";
        String sqlQuery2024 = "SELECT * FROM Users_2024";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement()) {

            ResultSet countResultSet = stmt.executeQuery(countQuery);
            if (countResultSet.next()) {
                int users23Count = countResultSet.getInt("Users_23_Count");
                int users24Count = countResultSet.getInt("Users_24_Count");
                reportManager.logInfo("Users_2023 Count: " + users23Count);
                reportManager.logInfo("Users_2024 Count: " + users24Count);
            } else {
                reportManager.logInfo("No data found in the count ResultSet.");
            }

            // Users_2023
            ResultSet rs2023 = stmt.executeQuery(sqlQuery2023);
            StringBuilder htmlTable2023 = new StringBuilder();
            htmlTable2023.append("<h2>Users_2023 Table</h2>");
            htmlTable2023.append("<table border='1'><tr>")
                    .append("<th>UserID</th><th>First_Name</th><th>Last_Name</th>")
                    .append("<th>Email</th><th>Gender</th><th>Mobile_Number</th>")
                    .append("</tr>");

            while (rs2023.next()) {
                htmlTable2023.append("<tr>")
                        .append("<td>").append(rs2023.getString("UserID")).append("</td>")
                        .append("<td>").append(rs2023.getString("First_Name")).append("</td>")
                        .append("<td>").append(rs2023.getString("Last_Name")).append("</td>")
                        .append("<td>").append(rs2023.getString("Email")).append("</td>")
                        .append("<td>").append(rs2023.getString("Gender")).append("</td>")
                        .append("<td>").append(rs2023.getString("Mobile_Number")).append("</td>")
                        .append("</tr>");
            }
            htmlTable2023.append("</table>");

            //Users_2024
            ResultSet rs2024 = stmt.executeQuery(sqlQuery2024);
            StringBuilder htmlTable2024 = new StringBuilder();
            htmlTable2024.append("<h2>Users_2024 Table</h2>");
            htmlTable2024.append("<table border='1'><tr>")
                    .append("<th>UserID</th><th>First_Name</th><th>Last_Name</th>")
                    .append("<th>Email</th><th>Gender</th><th>Mobile_Number</th>")
                    .append("</tr>");

            while (rs2024.next()) {
                htmlTable2024.append("<tr>")
                        .append("<td>").append(rs2024.getString("UserID")).append("</td>")
                        .append("<td>").append(rs2024.getString("First_Name")).append("</td>")
                        .append("<td>").append(rs2024.getString("Last_Name")).append("</td>")
                        .append("<td>").append(rs2024.getString("Email")).append("</td>")
                        .append("<td>").append(rs2024.getString("Gender")).append("</td>")
                        .append("<td>").append(rs2024.getString("Mobile_Number")).append("</td>")
                        .append("</tr>");
            }
            htmlTable2024.append("</table>");

            reportManager.logInfo(htmlTable2023.toString());
            reportManager.logInfo(htmlTable2024.toString());
            reportManager.logPass("Database query executed successfully.");

        } catch (SQLException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            reportManager.flush();
        }
    }
}
