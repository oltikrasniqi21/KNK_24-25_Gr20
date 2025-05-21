package controllers;

import Services.DashboardService;
import Services.LanguageManager;
import Services.SceneManager;
import javafx.fxml.FXML;
import Services.LanguageManager;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.SceneLocator;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminHomePageController {
    private final LanguageManager languageManager = LanguageManager.getInstance();


    @FXML
    private BorderPane mainLayout;

    private void loadCenterContent(String fxmlFile) {
        try {

            ResourceBundle bundle = LanguageManager.getInstance().getResourceBundle();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile), bundle);
            Node content = loader.load();
            mainLayout.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private PieChart usersStatusPieChart;

    @FXML
    private PieChart applicationStatusPieChart;

    @FXML
    private PieChart userRolePieChart;

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Label totalApplicationsLabel;

    @FXML
    private Label totalScholarshipsLabel;

    @FXML
    private BarChart<String, Number> studentsPerUniversityBarChart;



    private DashboardService dashboardService = new DashboardService();

    public void initialize(){
        Map<String, Integer> statusContent = dashboardService.getUserStatusCounts();

        statusContent.forEach((status, count) -> {
            PieChart.Data slice = new PieChart.Data(status, count);
            usersStatusPieChart.getData().add(slice);
        });

        Map<String, Integer> applicationContent = dashboardService.getApplicationStatusCounts();

        applicationContent.forEach((status, count) -> {
            PieChart.Data slice = new PieChart.Data(status, count);
            applicationStatusPieChart.getData().add(slice);
        });

        Map<String, Integer> roleContent = dashboardService.getUserRoleCounts();

        roleContent.forEach((status,count)->{
            PieChart.Data slice = new PieChart.Data(status, count);
            userRolePieChart.getData().add(slice);
        });

        int userCount = dashboardService.getUserCount();
        totalUsersLabel.setText(String.valueOf(userCount));

        int scholarshipCount = dashboardService.getScholarshipCount();
        totalScholarshipsLabel.setText(String.valueOf(scholarshipCount));

        int applicationCount = dashboardService.getApplicationCount();
        totalApplicationsLabel.setText(String.valueOf(applicationCount));

        HBox.setMargin(usersStatusPieChart, new Insets(40, 0, 20, 0));
        HBox.setMargin(applicationStatusPieChart, new Insets(40, 0, 20, 0));
        HBox.setMargin(userRolePieChart, new Insets(40, 0, 20, 0));

        Map<String, Integer> universityCounts = dashboardService.getStudentCountsByUniversity();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Students Per University");

        universityCounts.forEach((university, count) -> {
            series.getData().add(new XYChart.Data<>(university, count));
        });

        studentsPerUniversityBarChart.getData().add(series);

// Optional: Rotate X axis labels for readability if many universities
        CategoryAxis xAxis = (CategoryAxis) studentsPerUniversityBarChart.getXAxis();
        xAxis.setTickLabelRotation(45);

    }

    @FXML
    private void handleManageUsers(){
        loadCenterContent(SceneLocator.MANAGE_USERS_PAGE);
    }

    @FXML
    private void handleManageStudents(){
        loadCenterContent(SceneLocator.LIST_STUDENTS_PAGE);
    }

    @FXML
    private void handleDashboardClick(){SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);}
    @FXML
    private void handleManageScholarships(){
        loadCenterContent(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    @FXML
    private void handleViewApplications(){
        loadCenterContent(SceneLocator.MANAGE_APPLICATIONS_PAGE);
    }

    @FXML
    private void handleFeedback(){
        loadCenterContent(SceneLocator.MANAGE_ADMIN_FEEDBACK_PAGE);
    }

    @FXML
    private void handleNotification(){
        loadCenterContent(SceneLocator.MANAGE_ADMIN_NOTIFICATION_PAGE);
    }

    @FXML
    private void handleLogout(){
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
    }

    @FXML
    private void handleFAQ() {
        loadCenterContent(SceneLocator.MANAGE_FAQ_PAGE);
    }

    @FXML
    private void handleNews(){loadCenterContent(SceneLocator.NEWS_ADMIN);}

    @FXML
    private void handleAddUniversities(){loadCenterContent(SceneLocator.MANAGE_UNIVERSITIES);}
    @FXML
    private void handleStatistics(){loadCenterContent(SceneLocator.STATISTICS);}

    private void loadLanguage(Locale locale) throws Exception {
        languageManager.setLocale(locale);
        SceneManager.reload();
    }

    @FXML
    private void handleSQLanguageClick() throws Exception {
        loadLanguage(new Locale("sq"));
    }

    @FXML
    private void handleENLanguageClick() throws Exception {
        loadLanguage(Locale.ENGLISH);
    }

}

