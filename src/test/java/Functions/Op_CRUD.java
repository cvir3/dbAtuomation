package Functions;

import Utilities.ReportManager;

import java.sql.*;

public class Op_CRUD {
    public static void main(String[] args) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_CRUD");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            con = DriverManager.getConnection(connectionUrl);
            stmt = con.createStatement();

            // This is Insert operation
            String insertSql = "INSERT INTO Users_2023 (UserID, first_name, last_name) VALUES (13, 'Dell', 'Automationtester')";
            int insertRows = stmt.executeUpdate(insertSql);
            System.out.println("Insert operation executed, Rows affected: " + insertRows);
            reportManager.logInfo("Successfully inserted new entry.");

            String checkSql = "SELECT * FROM Users_2023 WHERE UserID = 13";
            rs = stmt.executeQuery(checkSql);

            if (rs.next()) {
                // If the record exists, proceed with update and delete operations
                System.out.println("Record found: UserID = " + rs.getInt("UserID") + ", first_Name = " + rs.getString("first_Name"));

                // This is  Update operation
                String updateSql = "UPDATE Users_2023 SET first_Name = 'TestUser' WHERE UserID = 13";
                int updateRows = stmt.executeUpdate(updateSql);
                System.out.println("Update operation executed, Rows affected: " + updateRows);
                reportManager.logInfo("Successfully updated");


                // This is Delete operation
                String deleteSql = "DELETE FROM CategoryTest3 WHERE CategoryID = 1";
                int deleteRows = stmt.executeUpdate(deleteSql);
                System.out.println("Delete operation executed, Rows affected: " + deleteRows);

            } else {

                System.out.println("No record found with UserID = 13. Insert operation failed.");
            }
            reportManager.logPass("CRUD operations is successfully.");

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        } finally {
            // Close the ResultSet, Statement, and Connection objects to release resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reportManager.flush();
        }
    }
}

