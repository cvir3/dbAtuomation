package Test;

import Functions.Op_CRUD_Operations;
import Utilities.ReportManager;

public class Test_CRUD_Operations {

    public static void main (String[] args) throws InterruptedException {
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_CRUD_Operation");
        new Op_CRUD_Operations().crudOperation(reportManager);
        Test_DBCount dbCount = new Test_DBCount();
        dbCount.testDbCount(reportManager);
        Test_CDC_Report testCdcReport = new Test_CDC_Report();
        testCdcReport.testCDCReport(reportManager);
        reportManager.flush();
    }
}
