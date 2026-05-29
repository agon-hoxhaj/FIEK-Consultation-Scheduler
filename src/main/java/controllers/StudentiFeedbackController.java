package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.DTO_data_transfer_object.CreateFeedbackDto;
import models.DrejtimiNiveliSemestriLenda;
import models.Profesor;
import services.*;

import java.util.*;

public class StudentiFeedbackController extends BaseController {

    private ProfesorService profesorService = new ProfesorService();
    private FeedbackService feedbackService = new FeedbackService();
    private DNSLService dnslService = new DNSLService();

    private final Map<String, Integer> dnsNameToIdMap = new HashMap<>();

    @FXML
    private TextArea komentiField;

    @FXML
    private ComboBox<Integer> vleresimiCombo;

    @FXML
    private ComboBox<String> profesoriCombo;

    @FXML
    private ComboBox<String> lendaCombo;

    @FXML
    public void initialize() {

        vleresimiCombo.getItems().addAll(1, 2, 3, 4, 5);

        List<Profesor> profesoret = profesorService.getAll();
        List<String> profesorNames = profesoret.stream()
                .map(Profesor::toString)
                .toList();
        profesoriCombo.setItems(FXCollections.observableArrayList(profesorNames));

        profesoriCombo.setOnAction(event -> {
            String selectedProf = profesoriCombo.getValue();
            if (selectedProf != null) {
                String[] fullName = selectedProf.split(" ", 2);
                if (fullName.length == 2) {
                    String emri = fullName[0];
                    String mbiemri = fullName[1];
                    int profId = profesorService.getProfIdByEmriMbiemri(emri, mbiemri);

                    ArrayList<DrejtimiNiveliSemestriLenda> dnsList = dnslService.getAllDNSL(profId);

                    lendaCombo.getItems().clear();
                    dnsNameToIdMap.clear();

                    for (DrejtimiNiveliSemestriLenda dns : dnsList) {
                        int dnsId = dns.getId();
                        String name = LanguageManager.getInstance().getLocale().getLanguage().equals("sq") ?
                                dnslService.getNameByid(dnsId) :
                                dnslService.getNameEnglishByid(dnsId);
                        dnsNameToIdMap.put(name, dnsId);
                        lendaCombo.getItems().add(name);
                    }
                }
            }
        });
    }

    @FXML
    private void handleSubmitFeedback() {
        try {
            Integer vleresimi = vleresimiCombo.getValue();
            String komenti = komentiField.getText().trim();
            String profesori = profesoriCombo.getValue();
            String lendaSelected = lendaCombo.getValue();

            if (vleresimi == null || komenti == null || komenti.trim().isEmpty()
                    || profesori == null || lendaSelected == null) {
                if(LanguageManager.getInstance().getLocale().getLanguage().equals("sq")) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Plotesoni te gjitha fushat!");
                }else{
                    showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields!");
                }
                return;
            }

            String[] fullName = profesori.split(" ", 2);
            if (fullName.length != 2) {
                if(LanguageManager.getInstance().getLocale().getLanguage().equals("sq")) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Emri i profesorit eshte invalid.");
                }else{
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid professor name.");
                }
                return;
            }

            String emri = fullName[0];
            String mbiemri = fullName[1];
            int profId = profesorService.getProfIdByEmriMbiemri(emri, mbiemri);

            Integer idDNSL = dnsNameToIdMap.get(lendaSelected);
            if (idDNSL == null) {
                if(LanguageManager.getInstance().getLocale().getLanguage().equals("sq")) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Zgjidh nje lende valide!");
                }else{
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid subject selection.");
                }
                return;
            }

            int studentId = SessionManager.getInstance().getStudentId();

            CreateFeedbackDto dto = new CreateFeedbackDto(profId, studentId, vleresimi, komenti);
            feedbackService.create(dto);

            if(LanguageManager.getInstance().getLocale().getLanguage().equals("sq")) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Vleresimi u krijua!");
            }else{
                showAlert(Alert.AlertType.INFORMATION, "Success", "Feedback created!");
            }
            komentiField.clear();

        } catch (Exception e) {
            e.printStackTrace();
            if(LanguageManager.getInstance().getLocale().getLanguage().equals("sq")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Gabim gjate krijimit te vleresimit!");
            }else {
                showAlert(Alert.AlertType.ERROR, "Error", "There was an error trying to create feedback!");
            }
        }
    }
}
