package Functions;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_CRUD {

    public void crudOperation(ReportManager reportManager){
        String connectionUrl = DBConfig.getConnectionUrl();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(connectionUrl);
            stmt = conn.createStatement();

            // Insert operation
            String insertSql = "INSERT INTO Users_2023 (UserID, first_name, last_name, email, gender, mobile_number) VALUES (5, 'Viren5', 'Automationtester', 'vir@yopmail.com', 'male', '9898098983')";
            int insertRows = stmt.executeUpdate(insertSql);
            System.out.println("Insert operation executed, Rows affected: " + insertRows);
            reportManager.logInfo("Successfully inserted new entry.");

            // Check if the record exists
            String checkSql = "SELECT * FROM Users_2023 WHERE UserID = 5";
            rs = stmt.executeQuery(checkSql);

            if (rs.next()) {
                // If the record exists, proceed with update and delete operations
                System.out.println("Record found: UserID = " + rs.getInt("UserID") + ", first_Name = " + rs.getString("first_Name"));

                // Update operation
                String updateSql = "UPDATE Users_2023 SET first_name = 'TestUser4' WHERE UserID = 5";
                int updateRows = stmt.executeUpdate(updateSql);
                System.out.println("Update operation executed, Rows affected: " + updateRows);
                reportManager.logInfo("Successfully updated.");

                // Delete operation
//                String deleteSql = "DELETE FROM Users_2023 WHERE UserID = 14";
//                int deleteRows = stmt.executeUpdate(deleteSql);
//                System.out.println("Delete operation executed, Rows affected: " + deleteRows);
//                reportManager.logInfo("Successfully deleted entry.");
            } else {
                System.out.println("No record found with UserID = 5. Insert operation failed.");
                reportManager.logInfo("No record found with UserID = 5.");
            }

            reportManager.logPass("CRUD operations completed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            reportManager.logFail("CRUD operations failed due to SQL exception.");
        } finally {
            // Close the ResultSet, Statement, and Connection objects to release resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reportManager.flush();
        }
    }
}

