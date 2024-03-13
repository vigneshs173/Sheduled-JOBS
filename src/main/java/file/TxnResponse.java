package main.java.file;

import java.time.LocalDate;

public class TxnResponse {

    private Long id;
    private LocalDate timeStamp;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
