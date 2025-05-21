package Services;

import Exceptions.EmptyFieldException;
import Exceptions.InvalidFieldException;
import Repository.UsersRepository;
import javafx.scene.control.Alert;
import utils.PasswordUtils;

import java.util.regex.Pattern;

public class CreateUserService {
    private final UsersRepository usersRepository;

    public CreateUserService() {
        this.usersRepository = new UsersRepository();
    }

    public boolean validateFields(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new EmptyFieldException();
        }

        String emailRegex = "^[^@\\s]+@admin\\.uni\\-[a-z]{2,3}\\.edu$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            showAlert("Invalid Email", "Please enter a valid admin email (e.g. user@admin.uni-pr.edu).");
            return false;
        }

        return true;
    }

    public boolean createAdminUser(String firstName, String lastName, String email, String password) {
        if (usersRepository.emailExists(email)) {
            showAlert("Duplicate Email", "This email is already registered.");
            return false;
        }

        String salt = PasswordUtils.getSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        String passwordToStore = salt + "$" + hashedPassword;

        return usersRepository.createAdminUser(
                passwordToStore,
                firstName,
                lastName,
                email,
                "admin",
                null
        );
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}