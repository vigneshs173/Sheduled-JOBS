package main.java.mobi;

import java.time.LocalDate;

public class TransactionResponse {

    private Long id;
    private LocalDate timeStamp;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }
}
