package main.java.file;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TxnDao dao = new TxnDao();
        List<TxnResponse> txnList = dao.getLastThirtyDaysTxn();

        System.out.println("size : " +txnList.size());

        if(txnList != null) {
            TxnService service = new TxnService();
            service.validateTimeStamp(txnList);
        }
        else
            System.out.println("No Records Found!");
    }
}