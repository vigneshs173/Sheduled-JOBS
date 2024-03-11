package main.java.mobi;

import java.time.*;
import java.util.List;

public class TxnService {

    void validateTimeStamp(List<TransactionResponse> txnList) {

        for(TransactionResponse txn : txnList ) {

            LocalDate txnDate = txn.getTimeStamp();
            LocalDate currentDate = LocalDate.now();
            LocalDate expiredDate = txnDate.plusDays(30);

            if (currentDate.isAfter(expiredDate)) {
                TxnDao dao = new TxnDao();
                dao.updateTransactionStatus(txn);
            }
        }
    }
}
