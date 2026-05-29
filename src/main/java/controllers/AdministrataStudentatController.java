package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.FlowPane;
import services.LanguageManager;
import services.StudentService;

import java.awt.*;
import java.util.Locale;

public class AdministrataStudentatController {
    StudentService studentService = new StudentService();

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("IKS", studentService.merrNumrinEStudentPerDrejtim(1)),
                new PieChart.Data("EAR",studentService.merrNumrinEStudentPerDrejtim(2)),
                new PieChart.Data("TIK",studentService.merrNumrinEStudentPerDrejtim(3)),
                new PieChart.Data("Elektoenergjetikë",studentService.merrNumrinEStudentPerDrejtim(4))
        );
        int total = studentService.merrNumrinTotalTeStudenteve();

        pieChart.setData(pieData);
        if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))) {
            pieChart.setTitle("Përqindja e Studentëve sipas Drejtimit");
        }else{
            pieChart.setTitle("Percentage of Students based by Department");
        }
        for (PieChart.Data data : pieData) {
            double perc = (data.getPieValue() / total) * 100;
            String etiketa = String.format("%s (%.1f%%)", data.getName(), perc);
            data.setName(etiketa);
        }
    }
}
