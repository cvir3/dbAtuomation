package Functions.LocalDb;

import Utilities.RandomData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Op_RandomDB {
    private final Connection connection;
    private final Statement statement;

    public Op_RandomDB(String connectionUrl) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection(connectionUrl);
        this.statement = connection.createStatement();
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE RandomData1 ("
                + "id INT PRIMARY KEY IDENTITY(1,1),"
                + "first_name VARCHAR(50),"
                + "last_name VARCHAR(50),"
                + "email VARCHAR(100),"
                + "gender VARCHAR(50),"
                + "mobile_number NVARCHAR(10));";
        statement.execute(createTableSQL);
    }

    public void insertRandomData(int numberOfRecords) throws SQLException {
        for (int i = 0; i < numberOfRecords; i++) {
            String firstName = RandomData.generateRandomString(10);
            String lastName = RandomData.generateRandomString(10);
            String email = firstName + "@example.com";
            String gender = (Math.random() < 0.5) ? "Male" : "Female";
            String mobileNumber = RandomData.generateRandomMobileNumber();

            String insertSQL = String.format("INSERT INTO RandomData1 (first_name, last_name, email, gender, mobile_number) "
                    + "VALUES ('%s', '%s', '%s', '%s', '%s');", firstName, lastName, email, gender, mobileNumber);
            statement.execute(insertSQL);
        }
    }

    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
