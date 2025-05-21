package UpdateDTO;

public class UpdateStudentsDTO {
    
    private Double gpa;
    private Integer yearOfStudy;    //perdorim Integer e jo int, sepse nese nuk caktojme nje vlere te nje kolone, default mos me qene zero '0', por NULL. E tipi 'int' nuk e vendos NULL.
    private String priority;

    public UpdateStudentsDTO(Double gpa, Integer yearOfStudy, String priority) {
        this.gpa = gpa;
        this.yearOfStudy = yearOfStudy;
        this.priority = priority;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
