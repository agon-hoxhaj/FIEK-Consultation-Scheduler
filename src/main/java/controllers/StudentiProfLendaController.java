package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentiProfLendaController extends BaseController{
    private StudentService studentService = new StudentService();
    private DNSLService dnslService = new DNSLService();
    private ProfesoretLendetService profesoretLendetService = new ProfesoretLendetService();
    private ProfesorService profesorService= new ProfesorService();

    @FXML
    private TableView<ProfesorLendaDisplay> tableView;

    @FXML
    private TableColumn<ProfesorLendaDisplay, String> emriProfesorit;

    @FXML
    private TableColumn<ProfesorLendaDisplay, String> emriLendes;

    public void initialize() {

        emriProfesorit.setCellValueFactory(new PropertyValueFactory<>("profEmri"));
        emriLendes.setCellValueFactory(new PropertyValueFactory<>("lendaEmri"));

        try {
            int studentId = SessionManager.getInstance().getStudentId();
            Student student= studentService.getById(studentId);
            String condition="WHERE id_drejtimi_niveli_semestri = "+student.getStudimi();
            ArrayList<DrejtimiNiveliSemestriLenda> listDNSL= dnslService.getAll(condition);

            List<ProfesorLendaDisplay> displayList = new ArrayList<>();
            String lendaName;
            String profName;
            if(LanguageManager.getInstance().getLocale().equals(new Locale("sq"))){
                for (DrejtimiNiveliSemestriLenda DNSL : listDNSL) {
                    lendaName=dnslService.getNameByid(DNSL.getId());
                    profName=profesorService.getNameById(profesoretLendetService.getById(DNSL.getIdProfesori_Lenda()).getIdProfesor()) ;
                    displayList.add(new ProfesorLendaDisplay( lendaName, profName));
                }
            }
            else{
                for (DrejtimiNiveliSemestriLenda DNSL : listDNSL) {
                    lendaName=dnslService.getNameEnglishByid(DNSL.getId());
                    profName=profesorService.getNameById(profesoretLendetService.getById(DNSL.getIdProfesori_Lenda()).getIdProfesor()) ;
                    displayList.add(new ProfesorLendaDisplay( lendaName, profName));
                }
            }

            if(displayList.isEmpty()){
                if(LanguageManager.getInstance().getLocale().getLanguage().equals("sq")) {
                    showAlert(Alert.AlertType.INFORMATION, "Informacion", "Nuk keni lende te listuara!");
                }else{
                    showAlert(Alert.AlertType.INFORMATION, "Information", "You have no enlisted subjects!");
                }
            }
            tableView.setItems(FXCollections.observableArrayList(displayList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
