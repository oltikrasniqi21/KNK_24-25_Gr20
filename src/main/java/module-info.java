module com.example.knk_2425_gr20 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.knk_2425_gr20 to javafx.fxml;
    exports com.example.knk_2425_gr20;
}