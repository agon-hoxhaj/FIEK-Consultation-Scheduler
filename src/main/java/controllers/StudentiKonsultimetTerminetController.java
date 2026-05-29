package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.*;
import models.DTO_data_transfer_object.CreateTerminetDto;
import services.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class StudentiKonsultimetTerminetController extends BaseController{

    private DNSLService dnslService = new DNSLService();
    private StudentService studentService = new StudentService();
    private OrariService orariService = new OrariService();
    private OrariDataService orariDataService = new OrariDataService();
    private TerminetService terminService = new  TerminetService();
    @FXML
    private AnchorPane anchorTerminetRezervuara;

    @FXML
    private FlowPane flowTerminetRezervuara;

    @FXML
    private FlowPane flowStudentiKonsultimetTerminet;

    @FXML
    private ChoiceBox<String> lendaChoiceBox;

    private Map<String, Integer> dnsNameToIdMap = new HashMap<>();

    boolean isAlbanian = LanguageManager.getInstance().getLocale().getLanguage().equals("sq");
    @FXML
    public void initialize() {
        try {
            orariDataService.generateValidOrareData();
            int studentId = SessionManager.getInstance().getStudentId();
            Student student= studentService.getById(studentId);
            String condition="WHERE id_drejtimi_niveli_semestri = "+student.getStudimi();
            ArrayList<DrejtimiNiveliSemestriLenda> listDNSL= dnslService.getAll(condition);
            dnsNameToIdMap.clear();
            List<String> displayList = new ArrayList<>();
            List<Integer> dnslIds = new ArrayList<>();
            String lendaName;
            for (DrejtimiNiveliSemestriLenda DNSL : listDNSL) {
                dnslIds.add(DNSL.getId());
                if(isAlbanian) {
                    lendaName=dnslService.getNameEnglishByid(DNSL.getId());
                    displayList.add(lendaName);
                    dnsNameToIdMap.put(lendaName, DNSL.getId());
                }else {
                    lendaName = dnslService.getNameByid(DNSL.getId());
                    displayList.add(lendaName);
                    dnsNameToIdMap.put(lendaName, DNSL.getId());
                }
            }
            if (isAlbanian) {
                lendaChoiceBox.setValue("Zgjidh lenden");
            } else {
                lendaChoiceBox.setValue("Select subject");
            }
            lendaChoiceBox.getItems().clear();
            for (String lenda : displayList) {
                lendaChoiceBox.getItems().add(lenda);
            }
            lendaChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    int dnslId = dnsNameToIdMap.get(newVal);
                    updateFlowPane(dnslId);
                }
            });
            flowTerminetRezervuara.setHgap(10);
            flowTerminetRezervuara.setVgap(10);
            flowTerminetRezervuara.setPadding(new Insets(10));
            updateReservedAppointmentsFlowPane();
        }catch(Exception e){
            e.printStackTrace();
            if(isAlbanian){
                showAlert(Alert.AlertType.ERROR,"Error","U shfaq nje problem! Ju lutem provoni perseri!");
            }else {
                showAlert(Alert.AlertType.ERROR, "Error", "There was a problem! Please try again!");
            }
        }
    }

    private void updateFlowPane(int dnslId) {
        flowStudentiKonsultimetTerminet.getChildren().clear();
        orariDataService.generateValidOrareData();
        List<Orari> activeOraret = orariService.getActiveOraret(dnslId);
        configureFlowPaneStyle();
        List<Button> buttonsToAdd = new ArrayList<>();

        for (Orari orari : activeOraret) {
            List<OrariData> datat = orariDataService.getValidOrariDataByOrarId(orari.getId());
            for (OrariData orariData : datat) {
                int usedSlots = orariService.countReservedTerminet(orariData.getId());
                if (usedSlots >= 1) continue;
                String subjectName = lendaChoiceBox.getValue();
                Button button = createSlotButton(dnslId, orari, orariData, subjectName);
                buttonsToAdd.add(button);
            }
        }
        flowStudentiKonsultimetTerminet.getChildren().addAll(buttonsToAdd);
    }

    private void configureFlowPaneStyle() {
        flowStudentiKonsultimetTerminet.setVgap(20);
        flowStudentiKonsultimetTerminet.setHgap(20);
        flowStudentiKonsultimetTerminet.setStyle("-fx-background-color: #f0f0f0;");
        flowStudentiKonsultimetTerminet.setPadding(new Insets(10, 20, 10, 20));
    }

    private Button createSlotButton(int dnslId, Orari orari, OrariData orariData, String subjectName) {
        LocalTime slotTime = orari.getOraFillimit();
        LocalTime slotEndTime = orari.getOraMbarimit();
        String buttonName = orariData.getData() + " - " + orari.getDita().name() + "\n" +
                slotTime + " - " + slotEndTime + "\n" + subjectName;
        Button button = new Button(buttonName);
        button.setPrefWidth(500);
        button.setPrefHeight(70);
        button.setOnAction(event -> {
            int studentId = SessionManager.getInstance().getStudentId();
            Date date = orariData.getData();
            LocalDate localDate;
            if (date instanceof java.sql.Date) {
                localDate = ((java.sql.Date) date).toLocalDate();
            } else {
                localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            LocalDateTime appointmentDateTime = LocalDateTime.of(localDate, slotTime);
            boolean alreadyReserved = terminService.hasStudentReservedAppointment(studentId, appointmentDateTime);

            if (alreadyReserved) {
                showAlert(Alert.AlertType.WARNING, "Warning",
                        isAlbanian ? "Ju keni rezervuar tashmë këtë termin." : "You have already reserved this appointment.");
                return;
            }
            TextInputDialog inputDialog = new TextInputDialog();
            inputDialog.setTitle(isAlbanian ? "Arsyeja e konsultimit" : "Reason for Consultation");
            inputDialog.setHeaderText(isAlbanian ?
                    "Ju lutem shkruani arsyen për konsultim:" :
                    "Please enter the reason for this consultation:");
            inputDialog.setContentText(isAlbanian ? "Arsyeja:" : "Reason:");
            TextField inputField = inputDialog.getEditor();
            Button okButton = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setDisable(true);

            inputField.textProperty().addListener((obs, oldText, newText) -> {
                okButton.setDisable(newText.trim().isEmpty());
            });
            Optional<String> reasonResult = inputDialog.showAndWait();
            reasonResult.ifPresent(reason -> {
                reason = reason.trim();
                if (reason.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING,
                            isAlbanian ? "Input i Munguar" : "Missing Input",
                            isAlbanian ? "Duhet të shkruani arsye për të vazhduar." : "You must enter a reason to continue.");
                    return;
                }
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle(isAlbanian ? "Konfirmo rezervimin" : "Confirm Reservation");
                confirmAlert.setHeaderText(isAlbanian ?
                        "A deshironi te rezervoni kete termin?" :
                        "Do you want to reserve this appointment?");
                Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
                if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                    Timestamp intervali = Timestamp.valueOf(appointmentDateTime);
                    CreateTerminetDto termin = new CreateTerminetDto(
                            orariData.getId(),
                            studentId,
                            intervali,
                            reason,
                            true
                    );
                    Terminet terminet = terminService.create(termin);
                    if (terminet != null) {
                        showAlert(Alert.AlertType.INFORMATION,
                                isAlbanian ? "Sukses" : "Success",
                                isAlbanian ? "Termini u rezervua me sukses!" : "Appointment reserved!");
                        updateFlowPane(dnslId);
                        updateReservedAppointmentsFlowPane();
                    } else {
                        showAlert(Alert.AlertType.ERROR,
                                isAlbanian ? "Gabim" : "Error",
                                isAlbanian ? "Rezervimi deshtoi." : "Failed to reserve appointment.");
                    }
                }
            });
        });
        return button;
    }

    private void updateReservedAppointmentsFlowPane() {
        flowTerminetRezervuara.getChildren().clear();
        int studentId = SessionManager.getInstance().getStudentId();
        List<Terminet> reservedAppointments = terminService.getReservedValidAppointmentsByStudent(studentId);

        if (reservedAppointments.isEmpty()) {
            anchorTerminetRezervuara.setPrefHeight(80);
            anchorTerminetRezervuara.setMinHeight(80);
            anchorTerminetRezervuara.setMaxHeight(80);
            LanguageManager languageManager = LanguageManager.getInstance();
            String string = languageManager.getResourceBundle().getString("txt.nukKaRezervime");
            Text text = new Text(string);
            text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            text.setFill(Color.RED);
            flowTerminetRezervuara.getChildren().add(text);
            return;
        } else {
            anchorTerminetRezervuara.setPrefHeight(Region.USE_COMPUTED_SIZE);
            anchorTerminetRezervuara.setMinHeight(Region.USE_COMPUTED_SIZE);
            anchorTerminetRezervuara.setMaxHeight(Region.USE_COMPUTED_SIZE);
        }

        for (Terminet terminet : reservedAppointments) {
            LocalDateTime appointmentDateTime = terminet.getIntervaliKohor().toLocalDateTime();
            String dateStr = appointmentDateTime.toLocalDate().toString();
            String timeStr = appointmentDateTime.toLocalTime().toString();
            Orari orari = orariService.getById(orariDataService.getById(terminet.getIdOrari()).getIdOrari());
            String subjectName = isAlbanian ?
                    dnslService.getNameByid(orari.getIdDrejtimiNiveliSemestriLenda()) :
                    dnslService.getNameEnglishByid(orari.getIdDrejtimiNiveliSemestriLenda());
            String buttonText = dateStr + " - " + orari.getDita().name() + "\n" +
                    timeStr + " - " + orari.getOraMbarimit() + "\n" + subjectName;
            Button reservedButton = new Button(buttonText);
            reservedButton.setPrefWidth(500);
            reservedButton.setPrefHeight(70);
            reservedButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            reservedButton.setOnAction(event -> {
                Alert confirmCancel = new Alert(Alert.AlertType.CONFIRMATION);
                confirmCancel.setTitle(isAlbanian ? "Anulo Terminin" : "Cancel Appointment");
                confirmCancel.setHeaderText(isAlbanian ?
                        "A jeni i sigurt që dëshironi të anuloni këtë takim?" :
                        "Are you sure you want to cancel this appointment?");
                Optional<ButtonType> result = confirmCancel.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean deleted = terminService.delete(terminet.getId());
                    if (deleted) {
                        showAlert(Alert.AlertType.INFORMATION,
                                isAlbanian ? "Anulim i suksesshëm" : "Cancelled Successfully",
                                isAlbanian ? "Termini u anulua me sukses." : "Appointment successfully cancelled.");
                        updateReservedAppointmentsFlowPane();
                        String selectedLenda = lendaChoiceBox.getValue();
                        if (selectedLenda != null && dnsNameToIdMap.containsKey(selectedLenda)) {
                            updateFlowPane(dnsNameToIdMap.get(selectedLenda));
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR,
                                isAlbanian ? "Gabim gjatë anulimit" : "Cancellation Error",
                                isAlbanian ? "Nuk mund të anulohet termini." : "Could not cancel the appointment.");
                    }
                }
            });
            flowTerminetRezervuara.getChildren().add(reservedButton);
        }
    }

}