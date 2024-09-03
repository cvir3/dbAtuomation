import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test_RandomData {

    public static void main(String[] args) {

        ExtentReports extent = new ExtentReports();

        ExtentTest test = extent.createTest("Database Table Creation and Data Insertion Test");

        String connectionUrl = "jdbc:sqlserver://CPC-Viren-9E0N7;DatabaseName=tpcxbb_1gb;user=sa;password=Test@1234;encrypt=true;trustServerCertificate=true;";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement()) {


            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Step 1: Create Table
            String createTableSQL = "CREATE TABLE RandomData4 ("
                    + "id INT PRIMARY KEY IDENTITY(1,1),"
                    + "first_name VARCHAR(50),"
                    + "last_name VARCHAR(50),"
                    + "email VARCHAR(100),"
                    + "gender VARCHAR(50),"
                    + "ip_address VARCHAR(50));";

            stmt.execute(createTableSQL);
            test.log(Status.PASS, "Table created successfully.");


            for (int i = 0; i < 1000; i++) {
                String firstName = generateRandomString(10);
                String lastName = generateRandomString(10);
                String email = firstName + "@example.com";
                String gender = (Math.random() < 0.5) ? "Male" : "Female";
                String ipAddress = generateRandomIP();

                String insertSQL = String.format("INSERT INTO RandomData4 (first_name, last_name, email, gender, ip_address) "
                        + "VALUES ('%s', '%s', '%s', '%s', '%s');", firstName, lastName, email, gender, ipAddress);
                stmt.execute(insertSQL);
            }
            test.log(Status.PASS, "Random data inserted successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            test.log(Status.FAIL, "Error during database operation: " + e.getMessage());
            e.printStackTrace();
        }


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
