package controllers;

import Services.StatisticsService;
import Database.DBCustomConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private PieChart universityPieChart;
    @FXML
    private PieChart facultyPieChart;
    @FXML
    private PieChart majorPieChart;

    private final StatisticsService statisticsService =
            new StatisticsService(DBCustomConnector.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        universityPieChart.setData(statisticsService.getStudentCountByUniversity());
        facultyPieChart.setData(statisticsService.getStudentCountByFaculty());
        majorPieChart.setData(statisticsService.getStudentCountByMajor());

        universityPieChart.setTitle(resources.getString("stats.university"));
        facultyPieChart.setTitle(resources.getString("stats.faculty"));
        majorPieChart.setTitle(resources.getString("stats.major"));
    }
}