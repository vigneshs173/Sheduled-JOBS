package main.java.file;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class TxnService {

    private static final Logger logger = Logger.getLogger(TxnService.class.getName());

    void validateTimeStamp(List<TxnResponse> txnList) {

        for(TxnResponse txn : txnList ) {

            LocalDate txnDate = txn.getTimeStamp();
            LocalDate currentDate = LocalDate.now();
            LocalDate expiredDate = txnDate.plusDays(30);

            if (currentDate.isAfter(expiredDate)) {
                    TxnDao dao = new TxnDao();
                    dao.updateTransactionStatus(txn,currentDate,expiredDate);
            }
            else
                logger.info("Not Expired Txn --> ID : " +txn.getId() +" Txn Date : "+ txnDate + " Currrent Date : "+currentDate +" Expired Date : "+expiredDate);
        }
    }
}