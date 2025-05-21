package UpdateDTO;

public class UpdateFacultiesDTO {
    private int facultyId;
    private String name;

    public UpdateFacultiesDTO(int facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}