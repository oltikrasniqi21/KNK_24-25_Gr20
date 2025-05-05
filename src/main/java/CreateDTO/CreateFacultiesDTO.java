package CreateDTO;

public class CreateFacultiesDTO {
    private int universityId;
    private String name;

    public CreateFacultiesDTO(int universityId, String name) {
        this.universityId = universityId;
        this.name = name;
    }

    public int getUniversityId() {
        return universityId;
    }

    public String getName() {
        return name;
    }
}
