package main.java.file;

import java.time.LocalDate;
import java.util.List;

public class TxnService {

    void validateTimeStamp(List<TxnResponse> txnList) {

        for(TxnResponse txn : txnList ) {

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