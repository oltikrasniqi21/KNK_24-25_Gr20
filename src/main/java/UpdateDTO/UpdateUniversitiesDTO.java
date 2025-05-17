package UpdateDTO;

public class UpdateUniversitiesDTO {
    private int universityId;
    private String name;
    private String city;
    private String country;

    public UpdateUniversitiesDTO(int universityId, String name, String city, String country) {
        this.universityId = universityId;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}