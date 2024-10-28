package Test;

import Functions.Op_RandomData_PF1103;
import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.SQLException;

public class Test_RandomData_PF1103 {
    public static void main(String[] args) {
        String connectionUrl = DBConfig.getConnectionUrl();

        ReportManager reportManager = new ReportManager();
        Op_RandomData_PF1103 dbOps = null;
        int recordsToInsert = 500; // Specify the number of records you want to insert

        try {
            dbOps = new Op_RandomData_PF1103(connectionUrl);
            reportManager.startTest("Database inserted in PF1103 DB");

            dbOps.insertPF1103(recordsToInsert);
            reportManager.logPass(recordsToInsert + " records inserted successfully into PF1103.");

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
