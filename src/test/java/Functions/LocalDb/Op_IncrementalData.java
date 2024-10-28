package Functions.LocalDb;
import java.sql.*;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Op_IncrementalData {
    private Connection connection;
    private final Statement statement;

       public Op_IncrementalData(String connectionUrl) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection(connectionUrl);
        this.statement = connection.createStatement();
    }

    public void incrementalData(int recordCount) throws SQLException {
        String sql = "INSERT INTO Local_PF1103 (iOfficeNum, sOfficeDept, iEffectiveLvl, iIndCtlNum, iCompanionCtlNum, sEmployeeID, sFirstName, sLastName, iDateEffective) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Random rand = new Random();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int counter = 1; counter <= recordCount; counter++) {
                preparedStatement.setInt(1, rand.nextInt(1000) + 1);  // iOfficeNum
                preparedStatement.setString(2, generateRandomDept());  // sOfficeDept
                preparedStatement.setInt(3, rand.nextInt(10) + 1);     // iEffectiveLvl
                preparedStatement.setInt(4, rand.nextInt(1000) + 1);   // iIndCtlNum
                preparedStatement.setInt(5, rand.nextInt(1000) + 1);   // iCompanionCtlNum
                preparedStatement.setString(6, generateEmployeeID(rand)); // sEmployeeID
                preparedStatement.setString(7, generateRandomFirstName(rand)); // sFirstName
                preparedStatement.setString(8, generateRandomLastName(rand));  // sLastName
                preparedStatement.setString(9, getCurrentDate(-5)); // iDateEffective with timezone offset

                preparedStatement.executeUpdate();
            }
        }
    }

    private String generateRandomDept() {
        Random rand = new Random();
        return "" + (char) (65 + rand.nextInt(26)) + (char) (65 + rand.nextInt(26)) + (char) (65 + rand.nextInt(26));
    }

    private String generateEmployeeID(Random rand) {
        return "EMP" + String.format("%04d", rand.nextInt(10000));
    }

    private String generateRandomFirstName(Random rand) {
        return (char) (65 + rand.nextInt(26)) + "First";
    }

    private String generateRandomLastName(Random rand) {
        return (char) (65 + rand.nextInt(26)) + "Last";
    }

    private String getCurrentDate(int timezoneOffset) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        return dateFormat.format(new Date(date.getTime() + timezoneOffset * 3600 * 1000));
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
