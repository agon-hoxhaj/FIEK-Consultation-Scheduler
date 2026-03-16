module org.example.fiekconsultationscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.fiekconsultationscheduler to javafx.fxml;
    exports org.example.fiekconsultationscheduler;
}