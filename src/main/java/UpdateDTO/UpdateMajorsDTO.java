package UpdateDTO;

public class UpdateMajorsDTO {
    private int majorId;
    private String name;

    public UpdateMajorsDTO(int majorId, String name) {
        this.majorId = majorId;
        this.name = name;
    }

    public int getMajorId() {
        return majorId;
    }

    public String getName() {
        return name;
    }
}
