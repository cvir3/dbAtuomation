package Test.LocalDb;

import Utilities.ReportManager;
import Functions.LocalDb.Op_Dbcount;

public class Test_DBCount {

    public static void main(String[] args) throws InterruptedException {
        Test_DBCount testDbCount = new Test_DBCount();
        ReportManager reportManager = new ReportManager();
        testDbCount.testDbCount(reportManager);
    }

    public void testDbCount(ReportManager reportManager) {
        reportManager.startTest("Test_Data Count");
        new Op_Dbcount().testDbCount(reportManager);
        reportManager.flush();
    }
}
