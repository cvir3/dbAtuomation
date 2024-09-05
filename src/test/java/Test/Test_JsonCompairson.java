package Test;

import Utilities.ReportManager;
import Functions.Op_JsonCom;

public class Test_JsonCompairson {

    public static void main(String[] args) {
        ReportManager reportManager = new ReportManager();
        reportManager.startTest("Test_JsonComparison_Operation");
        Op_JsonCom jsonComparison = new Op_JsonCom();
        jsonComparison.compareJsonFiles(reportManager);
        reportManager.flush();
    }

}

