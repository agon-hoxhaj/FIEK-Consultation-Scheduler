package controllers;

import enums.DaysOfWeek;
import enums.DitaJaves;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import models.DTO_data_transfer_object.CreateOrariDto;
import models.DrejtimiNiveliSemestriLenda;
import services.*;

import java.time.LocalTime;

import java.util.*;

public class ProfesoriShtoOrarController extends BaseController{
    private final DNSLService dnslService = new DNSLService();
    private final Map<String, Integer> dnsNameToIdMap = new HashMap<>();
    private final OrariService orariService= new OrariService();

    @FXML
    private ChoiceBox lendaChoiceBox;

    @FXML
    private ChoiceBox ditaChoiceBox;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    public void initialize() {
        int idProf = SessionManager.getInstance().getProfId();
        ArrayList<DrejtimiNiveliSemestriLenda> dnsList= dnslService.getAllDNSL(idProf);

        if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
            for (DrejtimiNiveliSemestriLenda dns : dnsList) {
                int dnsId = dns.getId();
                String name = dnslService.getNameByid(dnsId);
                dnsNameToIdMap.put(name, dnsId);
                lendaChoiceBox.getItems().add(name);
            }
        }
        else{
            for (DrejtimiNiveliSemestriLenda dns : dnsList) {
                int dnsId = dns.getId();
                String name = dnslService.getNameEnglishByid(dnsId);
                dnsNameToIdMap.put(name, dnsId);
                lendaChoiceBox.getItems().add(name);
            }
        }

        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 8));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        DitaJaves[] allDays = DitaJaves.values();
        DaysOfWeek[] allDaysEnglish = DaysOfWeek.values();
        if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
            for (int i = 0; i < 5 ; i++) {
                ditaChoiceBox.getItems().add(allDays[i]);
            }
        }else{
            for (int i = 0; i < 5 ; i++) {
                ditaChoiceBox.getItems().add(allDaysEnglish[i]);
            }
        }

    }
    @FXML
    public void handleCancelClick() throws Exception {
        StageManager.getInstance().closeModal();
    }

    @FXML
    public void handleOkClick(){
        int idDNSL= dnsNameToIdMap.get(lendaChoiceBox.getValue());
        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();
        LocalTime starTime = LocalTime.of(hour, minute);
        DitaJaves ditaJaves=null;
        DaysOfWeek ditaJavesEnglish=null;
        if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
            ditaJaves=(DitaJaves) ditaChoiceBox.getValue();
            ditaJavesEnglish=DaysOfWeek.fromId(ditaJaves.getId());
        }else{
            ditaJavesEnglish=(DaysOfWeek) ditaChoiceBox.getValue();
            ditaJaves=DitaJaves.fromId(ditaJavesEnglish.getId());
        }
        if(idDNSL>0 && ditaJaves!=null && ditaJavesEnglish!= null){
                    // idDNSL
                    // ditaJaves
                    // ditaJavesEnglish
                    // startime
            LocalTime  endTime = starTime.plusMinutes(5);
            boolean  statusi = true;

            CreateOrariDto createOrariDto= new CreateOrariDto(idDNSL,ditaJaves,ditaJavesEnglish,starTime,endTime,statusi);
            orariService.create(createOrariDto);
        }
        else{
            if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
                showAlert(Alert.AlertType.WARNING, "KUJDES", "Të lutem plotëso të dhënat.");
            }else{
                showAlert(Alert.AlertType.WARNING, "ALERT", "Please fill all required fields.");
            }
        }
        StageManager.getInstance().closeModal();
    }


}
