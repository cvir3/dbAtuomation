package Functions;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_CRUD_Operations {

    public void crudOperation(ReportManager reportManager){
        String connectionUrl = DBConfig.getConnectionUrl();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(connectionUrl);
            stmt = conn.createStatement();

//            insertRecord(conn, 8, "Brown", "Bony", "brown@example.com", "M", "9893289687");
//            reportManager.logPass("Record inserted successfully");

//            updateRecord(conn, 4, "auto@updated.com");
//            reportManager.logPass("Record Update successfully");

            deleteRecord(conn, 6);
            reportManager.logPass("Record Deleted successfully");

            displayTableData(conn, "Users_2024");
            reportManager.logPass("Displayed data from Users_2024");

            countRows(conn);
            reportManager.logPass("Counted rows in both tables");

        } catch (SQLException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            reportManager.flush();
        }
    }

    public void insertRecord(Connection connection, int userId, String firstName, String lastName, String email, String gender, String mobileNumber) throws SQLException {
        String insertSql = "INSERT INTO dbo.Users_2023 (UserID, First_Name, Last_Name, Email, Gender, Mobile_Number) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, gender);
            pstmt.setString(6, mobileNumber);
            pstmt.executeUpdate();
        }
    }

    public void updateRecord(Connection connection, int userId, String newEmail) throws SQLException {
        String updateSql = "UPDATE dbo.Users_2023 SET Email = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    public void deleteRecord(Connection connection, int userId) throws SQLException {
        String deleteSql = "DELETE FROM dbo.Users_2023 WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    private void displayTableData(Connection connection, String tableName) throws SQLException {
        String query = "SELECT * FROM dbo." + tableName;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("UserID: " + rs.getInt("UserID") +
                        ", First_Name: " + rs.getString("First_Name") +
                        ", Last_Name: " + rs.getString("Last_Name") +
                        ", Email: " + rs.getString("Email") +
                        ", Gender: " + rs.getString("Gender") +
                        ", Mobile_Number: " + rs.getString("Mobile_Number"));
            }
        }
    }

    private void countRows(Connection connection) throws SQLException {
        String countSql = "SELECT " +
                "(SELECT COUNT(*) FROM dbo.Users_2023) AS Source_Count, " +
                "(SELECT COUNT(*) FROM dbo.Users_2024) AS Destination_Count";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(countSql)) {
            if (rs.next()) {
                System.out.println("Source_Count: " + rs.getInt("Source_Count"));
                System.out.println("Destination_Count: " + rs.getInt("Destination_Count"));
            }
        }
    }


}

