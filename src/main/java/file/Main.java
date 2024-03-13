package main.java.file;

import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        TxnDao dao = new TxnDao();
        List<TxnResponse> txnList = dao.getLastThirtyDaysTxn();

       logger.info("List size : " + txnList.size());

        if(txnList != null) {
            TxnService service = new TxnService();
            service.validateTimeStamp(txnList);
        }
        else
           logger.info("No Records Found!");
    }
}

