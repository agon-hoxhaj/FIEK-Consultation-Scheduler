package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import services.*;

import java.util.ArrayList;
import java.util.Date;

public class ProfesoriTerminetContoller extends BaseController {

    OrariDataService orariDataService = new OrariDataService();
    OrariService orariService = new OrariService();
    StudentService studentService = new StudentService();
    TerminetService terminetService = new TerminetService();
    DNSLService dnslService = new DNSLService();
    ArrayList<Terminet> listaTerminet = terminetService.getAllTerminetByProfId(SessionManager.getInstance().getProfId());

    @FXML
    private TableView<TerminiDisplay> terminet;
    @FXML
    private TableColumn<TerminiDisplay, String> koha;
    @FXML
    private TableColumn<TerminiDisplay, String> lendatermini;
    @FXML
    private TableColumn<TerminiDisplay, String> st;
    @FXML
    private TableColumn<TerminiDisplay, String> arsyea;

    @FXML
    public void initialize() {
        terminetService.removeInvalidTermin();
        ObservableList<TerminiDisplay> displayList = FXCollections.observableArrayList();
        if (listaTerminet!=null) {
            for (Terminet ter : listaTerminet) {
                OrariData orariData = orariDataService.getById(ter.getIdOrari());
                Orari orari = orariService.getById(orariData.getIdOrari());
                Date data = orariData.getData();
                Student student = studentService.getById(ter.getIdStudenti());

                String kohaTerminit = orari.getOraFillimit().toString() + " - " +
                        orari.getOraMbarimit().toString() + " | " +
                        data.toString() + " | " +
                        orari.getDita().toString();
                System.out.println("koha :"+kohaTerminit);
                String lenda = dnslService.getNameByid(orari.getIdDrejtimiNiveliSemestriLenda());
                String studentName = student.getEmri() + " " + student.getMbiemri();
                String arsye=ter.getArsyeja();
                System.out.println(arsye);
                displayList.add(new TerminiDisplay(kohaTerminit, lenda, studentName,arsye));
            }

            koha.setCellValueFactory(new PropertyValueFactory<>("koha"));
            lendatermini.setCellValueFactory(new PropertyValueFactory<>("lenda"));
            st.setCellValueFactory(new PropertyValueFactory<>("studenti"));
            arsyea.setCellValueFactory(new PropertyValueFactory<>("arsyea"));
            terminet.setItems(displayList);
        }
    }
}
