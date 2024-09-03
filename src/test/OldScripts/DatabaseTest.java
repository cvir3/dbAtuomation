import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] args) {
        // Initialize ExtentReports
        ExtentReports extent = ExtentReportManager.getInstance();
        ExtentTest test = ExtentReportManager.createTest("Database Table Creation and Data Insertion Test");

        // Database connection details
        String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";
        DatabasePage dbPage = new DatabasePage(connectionUrl);

        try {
            // Perform actions using DatabasePage
            dbPage.createTable("RandomData9");
            test.log(Status.PASS, "Table created successfully.");

            dbPage.insertRandomData("RandomData9");
            test.log(Status.PASS, "Data inserted successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            test.log(Status.FAIL, "Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Write everything to the log file
            ExtentReportManager.flush();
        }
    }
}
