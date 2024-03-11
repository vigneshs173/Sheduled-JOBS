package main.java.mobi;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TxnDao {
//    private static final String JDBC_URL = "jdbc:mysql://192.168.11.210:3306/mobiversa?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "Mobi7548";

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mobiversa?";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mobiversa@12345";

//    private static final Logger logger = Logger.getLogger(TxnDao.class.getName());

    public List<TransactionResponse> getAllTxn() {
        List<TransactionResponse> txnList = new ArrayList();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM um_ecom_txnresponse WHERE TIME_STAMP >= ? AND TIME_STAMP <= ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                LocalDate currentDate = LocalDate.now();
                LocalDate thirtyDaysAgo = currentDate.minusDays(31);

                System.out.println("currernt date : " + currentDate);
                System.out.println("thirtyDaysAgo : " + thirtyDaysAgo);

                statement.setDate(1, Date.valueOf(thirtyDaysAgo));
                statement.setDate(2, Date.valueOf(currentDate));

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {

                        TransactionResponse txnResponse = new TransactionResponse();
                        txnResponse.setId(resultSet.getLong("ID"));
                        Date sqlDate = resultSet.getDate("TIME_STAMP");
                        txnResponse.setTimeStamp(sqlDate.toLocalDate());

                        txnList.add(txnResponse);
                    }
                    return txnList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    void updateTransactionStatus(TransactionResponse txn) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE um_ecom_txnresponse SET txn_status = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "EE");
                statement.setLong(2, txn.getId());

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Transaction status updated for ID : " + txn.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}