package main.java.file;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class TxnDao {

    private static final java.util.logging.Logger logger = Logger.getLogger(TxnDao.class.getName());

    private static final String JDBC_URL = "jdbc:mysql://192.168.11.210:3306/mobiversa?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mobi7548";
    private static final Properties queries = loadQueries();

    private static Properties loadQueries() {
        Properties properties = new Properties();
        try (InputStream input = TxnDao.class.getClassLoader().getResourceAsStream("queries.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public List<TxnResponse> getLastThirtyDaysTxn() {
        List<TxnResponse> txnList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(queries.getProperty("getLastThirtyDayTxn"))) {

            LocalDate currentDate = LocalDate.now();
            LocalDate thirtyDaysAgo = currentDate.minusDays(31);

            System.out.println("Current date  : " + currentDate);
            System.out.println("ThirtyDaysAgo : " + thirtyDaysAgo);

            statement.setDate(1, Date.valueOf(thirtyDaysAgo));
            statement.setDate(2, Date.valueOf(currentDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TxnResponse txnResponse = new TxnResponse();
                    txnResponse.setId(resultSet.getLong("ID"));
                    Date sqlDate = resultSet.getDate("TIME_STAMP");
                    txnResponse.setTimeStamp(sqlDate.toLocalDate());
                    txnResponse.setStatus(resultSet.getString("STATUS"));

                    txnList.add(txnResponse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return txnList;
    }

    public void updateTransactionStatus(TxnResponse txn, LocalDate currDate,LocalDate exDate) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(queries.getProperty("updateTxnStatus"))) {

            statement.setString(1, "EE");
            statement.setLong(2, txn.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Transaction status updated --> ID : " +txn.getId() +" Txn Date : "+ txn.getTimeStamp() + " Currrent Date : "+currDate +" Expired Date : "+exDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
