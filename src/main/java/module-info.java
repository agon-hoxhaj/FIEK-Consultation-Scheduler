module com.example.knk25_gr15 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens models to javafx.fxml;
    exports models;
    opens controllers to javafx.fxml;
    exports controllers;
    opens Application to javafx.fxml;
    exports Application;

}