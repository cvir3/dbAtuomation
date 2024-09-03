import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = now.format(formatter);

            // Create directories if they don't exist
            new File("reports/html").mkdirs();
            new File("reports/screenshots").mkdirs();

            // Setup ExtentSparkReporter with a timestamped path to the HTML file
            String reportPath = "reports/html/extentReport_" + timestamp + ".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Set report title and document name
            sparkReporter.config().setReportName("Database Testing Report");
            sparkReporter.config().setDocumentTitle("Database Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD); // Choose between STANDARD and DARK

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Adding System Information
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        if (extent != null) {
            test = extent.createTest(testName);
        }
        return test;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
