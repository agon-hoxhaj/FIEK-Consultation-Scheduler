module org.example.fiekconsultationscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.fiekconsultationscheduler to javafx.fxml;
    exports org.example.fiekconsultationscheduler;
}