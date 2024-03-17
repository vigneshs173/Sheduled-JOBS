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

    private static final Logger logger = Logger.getLogger(TxnDao.class.getName());
    private static final Properties properties = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TxnDao.class.getClassLoader().getResourceAsStream("main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public List<TxnResponse> getLastThirtyDaysTxn() {
        List<TxnResponse> txnList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("sql.getLastThirtyDayTxn"))) {

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

    public void updateTransactionStatus(TxnResponse txn, LocalDate currDate, LocalDate exDate) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("sql.updateTxnStatus"))) {

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

