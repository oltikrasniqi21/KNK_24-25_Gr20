package Services;

import utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginService {
    private final Connection connection;



    private static final String SUPERADMIN_EMAIL = System.getenv("SUPERADMIN_EMAIL");
    private static final String SUPERADMIN_PASSWORD = System.getenv("SUPERADMIN_PASSWORD");

    public LoginService(Connection connection) {
        this.connection = connection;
    }

    public String authenticate(String email, String inputPassword) throws Exception {
        // First check for superadmin
        if (email.equalsIgnoreCase("superadmin@internal") && inputPassword.equals("knk2025")) {
            return "admin"; // or "superadmin" if you add a new role
        }

        // Normal user authentication
        String query = "SELECT password, role FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String stored = rs.getString("password");
                    String role = rs.getString("role");

                    String[] parts = stored.split("\\$");
                    if (parts.length != 2) return null;

                    String storedSalt = parts[0];
                    String storedHash = parts[1];
                    String inputHash = PasswordUtils.hashPassword(inputPassword, storedSalt);

                    return inputHash.equals(storedHash) ? role : null;
                }
                return null;
            }
        }
    }
}