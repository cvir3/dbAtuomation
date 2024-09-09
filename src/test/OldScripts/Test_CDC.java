package Test;

import Utilities.DBConfig;
import Utilities.ReportManager;
import Functions.Op_Dbcount;

import java.sql.*;

public class Test_CDC {

    public static void main(String[] args) {
        String connectionUrl = DBConfig.getConnectionUrl();
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_CDC");
        new Op_Dbcount().testDbCount(reportManager);


        // SQL Query
        String sqlQuery = "SELECT COALESCE(u23.UserID, u24.UserID) AS ID, " +
                "u23.First_Name AS First_Name_2023, u24.First_Name AS First_Name_2024, " +
                "u23.Last_Name AS Last_Name_2023, u24.Last_Name AS Last_Name_2024, " +
                "u23.Email AS Email_2023, u24.Email AS Email_2024, " +
                "u23.Gender AS Gender_2023, u24.Gender AS Gender_2024, " +
                "u23.Mobile_Number AS Mobile_Number_2023, u24.Mobile_Number AS Mobile_Number_2024, " +
                "CASE WHEN u23.UserID IS NULL THEN 'New in 2024' " +
                "WHEN u24.UserID IS NULL THEN 'Deleted in 2024' " +
                "ELSE 'Updated' END AS ChangeType " +
                "FROM Users_2023 u23 FULL OUTER JOIN Users_2024 u24 ON u23.UserID = u24.UserID " +
                "WHERE u23.UserID IS NULL OR u24.UserID IS NULL " +
                "OR (u23.First_Name IS DISTINCT FROM u24.First_Name) " +
                "OR (u23.Last_Name IS DISTINCT FROM u24.Last_Name) " +
                "OR (u23.Email IS DISTINCT FROM u24.Email) " +
                "OR (u23.Gender IS DISTINCT FROM u24.Gender) " +
                "OR (u23.Mobile_Number IS DISTINCT FROM u24.Mobile_Number);";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sqlQuery)) {
            StringBuilder htmlTable = new StringBuilder();
            htmlTable.append("<table border='1'><tr>")
                    .append("<th>UserID</th><th>ChangeType</th>")
                    .append("<th>First_Name_2023</th><th>First_Name_2024</th>")
                    .append("<th>Last_Name_2023</th><th>Last_Name_2024</th>")
                    .append("<th>Email_2023</th><th>Email_2024</th>")
                    .append("<th>Gender_2023</th><th>Gender_2024</th>")
                    .append("<th>Mobile_Number_2023</th><th>Mobile_Number_2024</th>")
                    .append("</tr>");

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;

                String id = resultSet.getString("ID");
                String changeType = resultSet.getString("ChangeType");
                String First_Name2023 = resultSet.getString("First_Name_2023");
                String First_Name2024 = resultSet.getString("First_Name_2024");
                String Last_Name2023 = resultSet.getString("Last_Name_2023");
                String Last_Name2024 = resultSet.getString("Last_Name_2024");
                String email2023 = resultSet.getString("Email_2023");
                String email2024 = resultSet.getString("Email_2024");
                String gender2023 = resultSet.getString("Gender_2023");
                String gender2024 = resultSet.getString("Gender_2024");
                String Mobile_Number2023 = resultSet.getString("Mobile_Number_2023");
                String Mobile_Number2024 = resultSet.getString("Mobile_Number_2024");

                // Append each discrepancy to the table
                htmlTable.append("<tr>")
                        .append("<td>").append(id).append("</td>")
                        .append("<td>").append(changeType).append("</td>")
                        .append("<td>").append(First_Name2023).append("</td>")
                        .append("<td>").append(First_Name2024).append("</td>")
                        .append("<td>").append(Last_Name2023).append("</td>")
                        .append("<td>").append(Last_Name2024).append("</td>")
                        .append("<td>").append(email2023).append("</td>")
                        .append("<td>").append(email2024).append("</td>")
                        .append("<td>").append(gender2023).append("</td>")
                        .append("<td>").append(gender2024).append("</td>")
                        .append("<td>").append(Mobile_Number2023).append("</td>")
                        .append("<td>").append(Mobile_Number2024).append("</td>")
                        .append("</tr>");
            }

            htmlTable.append("</table>");

            if (!hasResults) {
                reportManager.logFail("No discrepancies found between Users_2023 and Users_2024 tables.");
            } else {
                reportManager.logPass("Discrepancies found between Users_2023 and Users_2024 tables.");
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
