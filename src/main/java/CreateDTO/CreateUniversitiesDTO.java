package CreateDTO;

public class CreateUniversitiesDTO {
    private final String name;
    private final String city;
    private final String country;

    public CreateUniversitiesDTO(String name, String city, String country) {
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}