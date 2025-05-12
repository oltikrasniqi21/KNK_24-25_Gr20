package CreateDTO;

import java.time.LocalDate;

public class CreateApplicationDto {
    private int id;
    private int sid;
    private int scid;
    private LocalDate application_date;
    private String status;

    public CreateApplicationDto(int id, int sid, int scid, LocalDate application_date, String status) {
        this.id = id;
        this.sid = sid;
        this.scid = scid;
        this.application_date = application_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    public LocalDate getApplication_date() {
        return application_date;
    }

    public void setApplication_date(LocalDate application_date) {
        this.application_date = application_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
