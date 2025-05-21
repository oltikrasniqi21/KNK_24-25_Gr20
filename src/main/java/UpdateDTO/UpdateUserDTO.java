package UpdateDTO;

import models.Users;

public class UpdateUserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;


    public UpdateUserDTO(String firstName, String lastName, String email, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public UpdateUserDTO(Users user) {
        this.id = user.getUser_id();
        this.firstName = user.getFirst_name();
        this.lastName = user.getLast_name();
        this.email = user.getEmail();
        this.role = user.getRole();
    }


    public int getId() {
        return this.id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
