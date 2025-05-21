package utils;

import Database.DBCustomConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static utils.PasswordUtils.getSalt;
import static utils.PasswordUtils.hashPassword;

public class GenerateTheSuperAdmin {
        public static void main(String[] args) {
            String password = "knk25";
            String email = "knk25@admin.com";
            String firstName = "KNK";
            String lastName = "2025";
            String role = "admin";
            String status = "active";
            String salt = getSalt();
            String hashedPassword = hashPassword(password, salt);
            Connection connection = DBCustomConnector.getConnection();
            String query = "INSERT INTO users (password, first_name, last_name, email, role, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, salt + "$" + hashedPassword);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, email);
                stmt.setString(5, role);
                stmt.setString(6, status);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Admin user created successfully!");
                } else {
                    System.out.println("Failed to create admin user.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
}
