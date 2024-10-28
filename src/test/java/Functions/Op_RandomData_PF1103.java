package Functions;

import Utilities.RandomData_PF1103;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Op_RandomData_PF1103 {
    private final Connection connection;
    private final Statement statement;

    public Op_RandomData_PF1103(String connectionUrl) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection(connectionUrl);
        this.statement = connection.createStatement();
    }


    public void insertPF1103(int numberOfRecords) throws SQLException {
        for (int i = 0; i < numberOfRecords; i++) {
            int iOfficeNum = RandomData_PF1103.generateRandomOfficeNum();
            String sOfficeDept = RandomData_PF1103.generateRandomOfficeDept();
            int iEffectiveLvl = RandomData_PF1103.generateRandomEffectiveLvl();
            int iIndCtlNum = RandomData_PF1103.generateRandomIndCtlNum();
            int iCompanionCtlNum = RandomData_PF1103.generateRandomCompanionCtlNum();
            String sEmployeeID = RandomData_PF1103.generateRandomEmployeeID();
            String sFirstName = RandomData_PF1103.generateRandomFirstName();
            String sLastName = RandomData_PF1103.generateRandomLastName();
            String iDateEffective = RandomData_PF1103.generateCurrentDateTime();


            String insertSQL = String.format("INSERT INTO PF1103 (iOfficeNum, sOfficeDept, iEffectiveLvl, iIndCtlNum, iCompanionCtlNum, sEmployeeID, sFirstName, sLastName, iDateEffective) "
                            + "VALUES (%d, '%s', %d, %d, %d, '%s', '%s', '%s', '%s');",
                    iOfficeNum, sOfficeDept, iEffectiveLvl, iIndCtlNum, iCompanionCtlNum, sEmployeeID, sFirstName, sLastName, iDateEffective);


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
