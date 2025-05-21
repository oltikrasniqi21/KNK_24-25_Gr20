package Services;

import Repository.UsersRepository;
import utils.PasswordUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

public class SignupService {
    private final UsersRepository usersRepository;


    public SignupService() {
        this.usersRepository = new UsersRepository();
    }

    public boolean isValidStudentEmail(String email) {
        String regex = "^[\\w.-]+@student\\.uni-[a-z]{2,10}\\.edu$";
        return Pattern.matches(regex, email);
    }

    public boolean emailContainsNameAndSurname(String email, String firstName, String lastName) {
        if (email == null || firstName == null || lastName == null) return false;
        String emailLower = email.toLowerCase();
        return emailLower.contains(firstName.toLowerCase()) && emailLower.contains(lastName.toLowerCase());
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    public String saveStudentDocument(File pdfFile) throws IOException {
        if (pdfFile == null) return null;
        File uploadDir = new File("uploads");
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Failed to create upload directory.");
        }
        File dest = new File(uploadDir, pdfFile.getName());
        Files.copy(pdfFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return dest.getAbsolutePath();
    }

    public void signupStudent(String password, String firstName, String lastName, String email,
                              int yearOfStudy, String university, String faculty, String major, String documentPath) throws Exception {

        String salt = PasswordUtils.getSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        String passwordToStore = salt + "$" + hashedPassword;

        usersRepository.signupStudent(passwordToStore, firstName, lastName, email, yearOfStudy, university, faculty, major, documentPath);
    }

}