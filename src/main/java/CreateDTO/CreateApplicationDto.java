package CreateDTO;

import java.time.LocalDate;

public class CreateApplicationDto {
    private int id;
    private int sid;
    private int scid;
    private LocalDate application_date;
    private String transcript_path;
    private double gpa;

    public CreateApplicationDto(int id, int sid, int scid, LocalDate application_date, String transcript_path, double gpa) {
        this.id = id;
        this.sid = sid;
        this.scid = scid;
        this.application_date = application_date;
        this.transcript_path = transcript_path;
        this.gpa = gpa;
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


    public int getScid() {
        return scid;
    }


    public LocalDate getApplication_date() {
        return application_date;
    }


    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getTranscript_path() {
        return transcript_path;
    }

}
