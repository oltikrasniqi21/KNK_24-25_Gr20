package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Universities {
    private final int universityId;
    private final String name;
    private final String city;
    private final String country;

    protected Universities(int universityId, String name, String city, String country) {
        this.universityId = universityId;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public static Universities getInstance(ResultSet rs) throws SQLException {
        int universityId = rs.getInt("id");
        String name = rs.getString("name");
        String city = rs.getString("city");
        String country = rs.getString("country");
        return new Universities(universityId, name, city, country);
    }

    public int getUniversityId() {
        return universityId;
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