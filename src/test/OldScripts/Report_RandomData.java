import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Report_RandomData {

    public static void main(String[] args) {

        new File("reports/html").mkdirs();
        new File("reports/screenshots").mkdirs();

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MMM_yyyy-hh_mm_ss_a");
        String timestamp = now.format(formatter);

        // Setup ExtentSparkReporter with a timestamped path to the HTML file
        String reportPath = "reports/html/DBTestReport_" + timestamp + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Set report title and document name
        sparkReporter.config().setReportName("Database Testing Report");
        sparkReporter.config().setDocumentTitle("Database Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Adding System Information
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        ExtentTest test = extent.createTest("Database Table Creation and Data Insertion Test");

        String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement()) {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Step 1: Create Table
            String createTableSQL = "CREATE TABLE RandomData7 ("
                    + "id INT PRIMARY KEY IDENTITY(1,1),"
                    + "first_name VARCHAR(50),"
                    + "last_name VARCHAR(50),"
                    + "email VARCHAR(100),"
                    + "gender VARCHAR(50),"
                    + "ip_address VARCHAR(50));";

            stmt.execute(createTableSQL);
            test.log(Status.PASS, "Table created successfully.");


            for (int i = 0; i < 1000; i++) {
                String firstName = Test_RandomData.generateRandomString(10);
                String lastName = Test_RandomData.generateRandomString(10);
                String email = firstName + "@example.com";
                String gender = (Math.random() < 0.5) ? "Male" : "Female";
                String ipAddress = Test_RandomData.generateRandomIP();

                String insertSQL = String.format("INSERT INTO RandomData7 (first_name, last_name, email, gender, ip_address) "
                        + "VALUES ('%s', '%s', '%s', '%s', '%s');", firstName, lastName, email, gender, ipAddress);
                stmt.execute(insertSQL);
            }
            test.log(Status.PASS, "Random data inserted successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            test.log(Status.FAIL, "Error during database operation: " + e.getMessage());
            e.printStackTrace();
        }

        // Write everything to the log file
        extent.flush();
    }
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public static String generateRandomIP() {
        return (int) (Math.random() * 256) + "." + (int) (Math.random() * 256) + "."
                + (int) (Math.random() * 256) + "." + (int) (Math.random() * 256);
    }


}
