package Test.LocalDb;

import Functions.LocalDb.Op_IncrementalData;
import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.SQLException;

public class Test_IncrementalData {
    public static void main(String[] args) {
        String connectionUrl = DBConfig.getConnectionUrl();
        ReportManager reportManager = new ReportManager();
        Op_IncrementalData dbOps = null;
        int recordsToInsert = 100000;

        try {
            dbOps = new Op_IncrementalData(connectionUrl);
            reportManager.startTest("Insert data in to Local DB");
            dbOps.incrementalData(recordsToInsert);
            reportManager.logPass(recordsToInsert + " records inserted successfully into Local_PF1103");

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
