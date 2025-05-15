module com.example.knk_2425_gr20 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.knk_2425_gr20 to javafx.fxml;
    opens Database to javafx.fxml;

    exports com.example.knk_2425_gr20;
    exports Database;

    exports test to javafx.fxml;
    opens test;

    exports controllers to javafx.fxml;
    opens controllers;


    opens models to javafx.base;
    exports models;


}