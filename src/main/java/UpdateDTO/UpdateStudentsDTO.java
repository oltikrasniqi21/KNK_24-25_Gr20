package UpdateDTO;

public class UpdateStudentsDTO {

    private double gpa;
    private Integer yearOfStudy;    //perdorim Integer e jo int, sepse nese nuk caktojme nje vlere te nje kolone, default mos me qene zero '0', por NULL. E tipi 'int' nuk e vendos NULL.
    //private Integer coursesLeft;
    private String priority;

    public UpdateStudentsDTO(double gpa, Integer yearOfStudy, String priority) {
        this.gpa = gpa;
        this.yearOfStudy = yearOfStudy;
        //this.coursesLeft = coursesLeft;
        this.priority = priority;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    //public Integer getCoursesLeft() {
      //  return coursesLeft;
    //}

    //public void setCoursesLeft(Integer coursesLeft) {
      //  this.coursesLeft = coursesLeft;
    //}

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
