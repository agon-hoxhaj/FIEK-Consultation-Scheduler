package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import database.DBConnection;
import models.Profesor;
import services.LanguageManager;
import services.ProfesorService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class AdministrataProfesoretController extends BaseController {
    ProfesorService profesorService=new ProfesorService();

    @FXML
    private FlowPane flowPane;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private Map<String, String> drejtimiNgjyra = new LinkedHashMap<>() {{
        put("Inxhineria kompjuterike dhe softuerike", "#A3D5FF");
        put("Elektronikë, Automatikë dhe Robotikë", "#FFD6A5");
        put("Teknologjite e Informacionit dhe Komunikimit", "#CAFFBF");
        put("Elektroenergjetike", "#D0BFFF");
    }};

    @FXML
    public void initialize() {
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        shfaqStatistikat();
        flowPane.setStyle("-fx-padding: 15;");

        if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))) {
            xAxis.setLabel("Drejtimet");
            yAxis.setLabel("Numri i profesoreve");
        }else{
            xAxis.setLabel("Department");
            yAxis.setLabel("Number of Profesors");
        }

        yAxis.setTickUnit(1);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });

        Map<String, Integer> profesorePerDrejtim = new HashMap<>();
        profesorePerDrejtim.put("IKS", 22);
        profesorePerDrejtim.put("EAR", 31);
        profesorePerDrejtim.put("TIK", 30);
        profesorePerDrejtim.put("Elektroenergjetike", 23);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Profesorët");

        Map<String, String> drejtimiNgjyra = new LinkedHashMap<>();
        drejtimiNgjyra.put("IKS", "#A3D5FF");
        drejtimiNgjyra.put("EAR", "#FFD6A5");
        drejtimiNgjyra.put("TIK", "#Caffbf");
        drejtimiNgjyra.put("Elektroenergjetike", "#D0BFFF");

        for (Map.Entry<String, String> entry : drejtimiNgjyra.entrySet()) {
            String drejtimi = entry.getKey();
            String ngjyra = entry.getValue();
            Integer vlera = profesorePerDrejtim.getOrDefault(drejtimi, 0);

            XYChart.Data<String, Number> data = new XYChart.Data<>(drejtimi, vlera);
            series.getData().add(data);

            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + ngjyra + ";");
                    Tooltip tooltip = new Tooltip("Numri i profesoreve: " + vlera);
                    Tooltip.install(newNode, tooltip);
                }
            });

        }
        barChart.setCategoryGap(40);
        barChart.setBarGap(5);
        barChart.setPrefWidth(800);
        barChart.setPrefHeight(550);

        barChart.getData().clear();
        barChart.getData().add(series);
    }

    private void shfaqStatistikat() {

        Map<String, Integer> statistika = profesorService.merrNumrinEProfPerDrejtim();

        for (Map.Entry<String, Integer> entry : statistika.entrySet()) {
            String drejtimi = entry.getKey();
            Integer numri = entry.getValue();
            String ngjyra = drejtimiNgjyra.getOrDefault(drejtimi, "#e0f0ff");

            VBox karta = new VBox(5);
            karta.setStyle(
                    "-fx-padding: 10;" +
                            "-fx-background-color: " + ngjyra + ";" +
                            "-fx-border-color: #005a9c;" +
                            "-fx-border-radius: 5;" +
                            "-fx-background-radius: 5;"
            );
            karta.setPrefWidth(350);

            if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))) {
                Label emriDrejtimit = new Label("Drejtimi: " + drejtimi);
                emriDrejtimit.setStyle("-fx-font-weight: bold; -fx-text-fill: #121212;");

                Label numriProf = new Label("Profesorë: " + numri);
                karta.getChildren().addAll(emriDrejtimit, numriProf);
            }else{
                Label emriDrejtimit = new Label("Department: " + drejtimi);
                emriDrejtimit.setStyle("-fx-font-weight: bold; -fx-text-fill: #121212;");

                Label numriProf = new Label("Professors: " + numri);
                karta.getChildren().addAll(emriDrejtimit, numriProf);
            }
            flowPane.getChildren().add(karta);
        }
    }
}