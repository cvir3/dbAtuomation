package Test;

import Functions.Op_Dbcount_PF1103;
import Utilities.ReportManager;

public class Test_DBCount_PF1103 {

    public static void main(String[] args) throws InterruptedException {
        Test_DBCount_PF1103 testDbCount = new Test_DBCount_PF1103();
        ReportManager reportManager = new ReportManager();
        testDbCount.QADbCount(reportManager);
    }

    public void QADbCount(ReportManager reportManager) {
        reportManager.startTest("Test_Data Count");
        new Op_Dbcount_PF1103().QADbCount(reportManager);
        reportManager.flush();
    }
}
