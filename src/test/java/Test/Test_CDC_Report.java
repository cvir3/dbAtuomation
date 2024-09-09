package Test;

import Utilities.ReportManager;
import Functions.Op_CDCReport;

public class Test_CDC_Report {

    public static void main(String[] args) throws InterruptedException {

        Test_CDC_Report tcr = new Test_CDC_Report();
        ReportManager reportManager = new ReportManager();
        tcr.testCDCReport(reportManager);
    }

    public void testCDCReport(ReportManager reportManager) throws InterruptedException {

        reportManager.startTest("Test_CDC_Report");
        new Op_CDCReport().cdcReport(reportManager);
        reportManager.flush();
    }
}
