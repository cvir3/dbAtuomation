package Test;

import Utilities.ReportManager;
import Functions.Op_CRUD;

public class Test_CRUD {

    public static void main (String[] args) throws InterruptedException {
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_CRUD_Operation");
        new Op_CRUD().crudOperation(reportManager);
        Test_DBCount dbCount = new Test_DBCount();
        dbCount.testDbCount(reportManager);
        Test_CDC_Report testCdcReport = new Test_CDC_Report();
        testCdcReport.testCDCReport(reportManager);
        reportManager.flush();

    }
}
