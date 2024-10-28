package Functions;

import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.*;

public class Op_CRUD_PF1103 {

    public void crudPF1103(ReportManager reportManager){
        String connectionUrl = DBConfig.getConnectionUrl();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(connectionUrl);
            stmt = conn.createStatement();

            insertRecord(conn, 9998, "Hr", 0, 0, 0,"989829898", "Jay", "Patel" );
            reportManager.logPass("Record inserted successfully");

//            updateRecord(conn, 4, "auto@updated.com");
//            reportManager.logPass("Record Update successfully");

//            deleteRecord(conn, 6);
//            reportManager.logPass("Record Deleted successfully");

//            displayTableData(conn, "pf1103");
//            reportManager.logPass("Displayed data from pf1103");

//            countRows(conn);
//            reportManager.logPass("Counted rows in both tables");

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

    public void insertRecord(Connection connection, int iOfficeNum, String sOfficeDept, int iEffectiveLvl, int iIndCtlNum, int iCompanionCtlNum, String sEmployeeID, String sFirstName, String sLastName) throws SQLException {
        String insertSql = "INSERT INTO dbo.pf1103 (iOfficeNum, sOfficeDept, iEffectiveLvl, iIndCtlNum, iCompanionCtlNum, sEmployeeID, sFirstName,sLastName ) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
            pstmt.setInt(1, iOfficeNum);
            pstmt.setString(2, sOfficeDept);
            pstmt.setInt(3, iEffectiveLvl);
            pstmt.setInt(4, iIndCtlNum);
            pstmt.setInt(5, iCompanionCtlNum);
            pstmt.setString(6, sEmployeeID);
            pstmt.setString(7, sFirstName);
            pstmt.setString(8, sLastName);
            pstmt.executeUpdate();
        }
    }

    public void updateRecord(Connection connection, int iOfficeNum, String sFirstName) throws SQLException {
        String updateSql = "UPDATE dbo.pf1103 SET FirsName = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
            pstmt.setString(1, sFirstName);
            pstmt.setInt(2, iOfficeNum);
            pstmt.executeUpdate();
        }
    }

    public void deleteRecord(Connection connection, int iOfficeNum) throws SQLException {
        String deleteSql = "DELETE FROM dbo.pf1103 WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {
            pstmt.setInt(1, iOfficeNum);
            pstmt.executeUpdate();
        }
    }

//    private void displayTableData(Connection connection, String tableName) throws SQLException {
//        String query = "SELECT * FROM dbo." + tableName;
//        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                System.out.println("UserID: " + rs.getInt("UserID") +
//                        ", First_Name: " + rs.getString("First_Name") +
//                        ", Last_Name: " + rs.getString("Last_Name") +
//                        ", Email: " + rs.getString("Email") +
//                        ", Gender: " + rs.getString("Gender") +
//                        ", Mobile_Number: " + rs.getString("Mobile_Number"));
//            }
//        }
//    }

//    private void countRows(Connection connection) throws SQLException {
//        String countSql = "SELECT " +
//                "(SELECT COUNT(*) FROM dbo.Users_2023) AS Source_Count, " +
//                "(SELECT COUNT(*) FROM dbo.Users_2024) AS Destination_Count";
//        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(countSql)) {
//            if (rs.next()) {
//                System.out.println("Source_Count: " + rs.getInt("Source_Count"));
//                System.out.println("Destination_Count: " + rs.getInt("Destination_Count"));
//            }
//        }
//    }


}

