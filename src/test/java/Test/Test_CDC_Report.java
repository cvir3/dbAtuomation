package Test;

import Utilities.ReportManager;
import Functions.Op_CDCReport;

public class Test_CDC_Report {

    public static void main(String[] args) {
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_CDC_Report");
        new Op_CDCReport().cdcReport(reportManager);
        reportManager.flush();
    }
}
