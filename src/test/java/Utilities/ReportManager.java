package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportManager {
    private ExtentReports extent;
    private ExtentTest test;

    public ReportManager() {
        new File("reports/html").mkdirs();

        // current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MMM_yyyy-hh_mm_ss_a");
        String timestamp = now.format(formatter);

        String reportPath = "reports/html/DBTestReport_" + timestamp + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setReportName("Database Testing Report");
        sparkReporter.config().setDocumentTitle("Database Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System Information
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    public void startTest(String testName) {
        test = extent.createTest(testName);
    }

    public void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    public void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }

    public void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }
    public void logTable(String tableName, String tableData) {
        test.info("Table: " + tableName);
        test.info("<table border='1'>" + tableData + "</table>");
    }

    public void flush() {
        if (extent != null) {
            extent.flush();
        }
    }



}
