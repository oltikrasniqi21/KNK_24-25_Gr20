package Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static utils.AlertMessages.showAlert;

public class StatisticsService {
    private final Connection connection;

    public StatisticsService(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<PieChart.Data> getStudentCountByUniversity() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String sql = "SELECT university, COUNT(*) as count FROM students GROUP BY university";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String university = rs.getString("university");
                int count = rs.getInt("count");
                data.add(new PieChart.Data(university, count));
            }
        } catch (Exception e) {
            showAlert("Database Error!", "Failed to fetch data from the database.");
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ObservableList<PieChart.Data> getStudentCountByFaculty() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String sql = "SELECT faculty, COUNT(*) as count FROM students GROUP BY faculty";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String faculty = rs.getString("faculty");
                int count = rs.getInt("count");
                data.add(new PieChart.Data(faculty, count));
            }
        } catch (Exception e) {
            showAlert("Database Error!", "Failed to fetch data from the database.");
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ObservableList<PieChart.Data> getStudentCountByMajor() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String sql = "SELECT major, COUNT(*) as count FROM students GROUP BY major";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String major = rs.getString("major");
                int count = rs.getInt("count");
                data.add(new PieChart.Data(major, count));
            }
        } catch (Exception e) {
            showAlert("Database Error!", "Failed to fetch data from the database.");
            System.out.println(e.getMessage());
        }
        return data;
    }
}