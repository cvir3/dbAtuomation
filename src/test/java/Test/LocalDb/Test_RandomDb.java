package Test.LocalDb;

import Functions.Op_RandomData_PF1103;
import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.SQLException;

public class Test_RandomDb {
    public static void main(String[] args) {
        String connectionUrl = DBConfig.getConnectionUrl();
        //String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";

        ReportManager reportManager = new ReportManager();
        Op_RandomData_PF1103 dbOps = null;

        try {
            dbOps = new Op_RandomData_PF1103(connectionUrl);
            reportManager.startTest("Database Table Creation and Insertion In Local DB");

            dbOps.insertPF1103(200);
            reportManager.logPass("Random data inserted successfully.");
            reportManager.logInfo("Database created and inserted into local database.");

        } catch (SQLException | ClassNotFoundException e) {
            reportManager.logFail("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (dbOps != null) {
                    dbOps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reportManager.flush();
        }
    }
}
