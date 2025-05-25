package UpdateDTO;

public class UpdateStudentsDTO {
    
    private Double gpa;
    private Integer yearOfStudy;
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

}
