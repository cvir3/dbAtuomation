package Test;

import Functions.Op_RandomDB;
import Utilities.DBConfig;
import Utilities.ReportManager;

import java.sql.SQLException;

public class Test_RandomDb {
    public static void main(String[] args) {
        String connectionUrl = DBConfig.getConnectionUrl();
        //String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";

        ReportManager reportManager = new ReportManager();
        Op_RandomDB dbOps = null;

        try {
            dbOps = new Op_RandomDB(connectionUrl);
            reportManager.startTest("Database Table Creation and Data Insertion Test");

            dbOps.createTable();
            reportManager.logPass("Table created successfully.");

            dbOps.insertRandomData(10);
            reportManager.logPass("Random data inserted successfully.");

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
