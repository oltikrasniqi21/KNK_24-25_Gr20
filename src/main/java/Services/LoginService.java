package Services;

import utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginService {
    private final Connection connection;

    public LoginService(Connection connection) {
        this.connection = connection;
    }

    public String authenticate(String email, String inputPassword) throws Exception {
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
    public int fetchUserIdByEmail(Connection conn, String email) throws Exception {
        String query = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1; // For superadmin you might use -1 or 0, as they might not be in DB
    }

}