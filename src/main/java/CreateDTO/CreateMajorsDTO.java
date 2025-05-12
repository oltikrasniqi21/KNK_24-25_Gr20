package CreateDTO;

public class CreateMajorsDTO {
    private int facultyId;
    private String name;

    public CreateMajorsDTO(int facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }
}
