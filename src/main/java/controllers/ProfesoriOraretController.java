package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import models.DTO_data_transfer_object.UpdateOrariDto;
import models.DrejtimiNiveliSemestriLenda;
import models.Orari;
import services.*;
import utils.SceneLocator;

import java.util.*;

public class ProfesoriOraretController extends BaseController{
    private final OrariService orariService= new OrariService();
    private final DNSLService dnsService = new DNSLService();
    private final Map<String, Integer> dnsNameToIdMap = new HashMap<>();

    ArrayList<Orari> activeOraret;

    @FXML
    private FlowPane flowPaneProfesoriOraret;

    @FXML
    private ChoiceBox<String> choiceBoxProfesoriOraret;

    @FXML
    public void handleShtoOrarClick(){

        String title = SceneManager.getInstance()
                .getLanguageManager()
                .getResourceBundle()
                .getString("btn.shtoOrar");

        StageManager.getInstance().showModal(SceneLocator.SHTO_ORAR, title);

    }
    @FXML
    public void initialize() {
        int idProf =SessionManager.getInstance().getProfId();
        System.out.println("id e profes :"+idProf);
        ArrayList<DrejtimiNiveliSemestriLenda> dnsList=dnsService.getAllDNSL(idProf);

        if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
            for (DrejtimiNiveliSemestriLenda dns : dnsList) {
                int dnsId = dns.getId();
                String name = dnsService.getNameByid(dnsId);
                dnsNameToIdMap.put(name, dnsId);
                choiceBoxProfesoriOraret.getItems().add(name);
            }
        }
        else{
            for (DrejtimiNiveliSemestriLenda dns : dnsList) {
                int dnsId = dns.getId();
                String name = dnsService.getNameEnglishByid(dnsId);
                dnsNameToIdMap.put(name, dnsId);
                choiceBoxProfesoriOraret.getItems().add(name);
            }
        }


        System.out.println(dnsService.getNameByid(1));

        choiceBoxProfesoriOraret.setOnAction(event -> updateFlowPane());

    }
    private void updateFlowPane() {
        flowPaneProfesoriOraret.getChildren().clear();

        String selectedOption = choiceBoxProfesoriOraret.getValue();
        if (selectedOption == null) { return; }

        Integer dnsId = dnsNameToIdMap.get(selectedOption);
        if (dnsId == null) return;

        activeOraret = orariService.getActiveOraret(dnsId);

        flowPaneProfesoriOraret.setVgap(15);
        flowPaneProfesoriOraret.setHgap(15);
        flowPaneProfesoriOraret.setStyle("-fx-background-color: #1A1A1A;");
        flowPaneProfesoriOraret.setPadding(new Insets(15));
        for (Orari orari : activeOraret) {
            String buttonName = orari.getDita().name() + "\n" +
                    orari.getOraFillimit() + " - " + orari.getOraMbarimit();
            Button button = new Button(buttonName);
            button.setPrefWidth(260);
            button.setPrefHeight(60);
            button.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            button.setStyle(
                    "-fx-background-color: #242424; " +
                            "-fx-text-fill: #FFFFFF; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 8px; " +
                            "-fx-border-color: #3A3A3A; " +
                            "-fx-border-radius: 8px; " +
                            "-fx-cursor: hand;"
            );
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2D2426; -fx-text-fill: #FFB3B3; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-border-color: #FFB3B3; -fx-border-radius: 8px; -fx-cursor: hand;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #242424; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-border-color: #3A3A3A; -fx-border-radius: 8px; -fx-cursor: hand;"));

            button.setOnAction(event -> {
                Optional<ButtonType> result;
                if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
                    result = showConfirmAlert(Alert.AlertType.CONFIRMATION, "Konfirmo fshirjen", "Jeni i sigurtë që dëshironi të fshini këtë orar?");
                } else {
                    result = showConfirmAlert(Alert.AlertType.CONFIRMATION, "Delete Confirmation", "Are you sure you want to delete this schedule?");
                }
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    UpdateOrariDto updateOrariDto = new UpdateOrariDto(orari.getId(), false);
                    orariService.update(updateOrariDto);
                    updateFlowPane(); // Refresh view
                }
            });
            flowPaneProfesoriOraret.getChildren().add(button);
        }
    }
}
