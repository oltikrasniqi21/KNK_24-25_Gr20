package CreateDTO;

public class CreateStudentsDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double gpa;
    private Integer yearOfStudy;
    private String university;
    private String faculty;
    private String major;
    private Integer coursesLeft;
    private String priority;

    public CreateStudentsDTO(String firstName, String lastName, String email, String password, Double gpa, Integer yearOfStudy, String university, String faculty, String major, Integer coursesLeft, String priority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gpa = gpa;
        this.yearOfStudy = yearOfStudy;
        this.university = university;
        this.faculty = faculty;
        this.major = major;
        this.coursesLeft = coursesLeft;
        this.priority = priority;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getCoursesLeft() {
        return coursesLeft;
    }

    public void setCoursesLeft(Integer coursesLeft) {
        this.coursesLeft = coursesLeft;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

