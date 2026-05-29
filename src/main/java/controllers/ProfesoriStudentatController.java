package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Student;
import services.StudentService;
import java.util.List;
import services.SessionManager;


public class ProfesoriStudentatController extends BaseController{
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colEmri;
    @FXML
    private TableColumn<Student, String> colMbiemri;
    @FXML
    private TableColumn<Student, String> colEmail;


    private StudentService studentService = new StudentService();


    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmri.setCellValueFactory(new PropertyValueFactory<>("emri"));
        colMbiemri.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadStudentetEProfesorit();
    }

    private void loadStudentetEProfesorit() {
        int profId = SessionManager.getInstance().getProfId();
        System.out.println("Profesor ID nga sessioni: " + profId);

        if (profId != 0) {
            List<Student> studentet = studentService.getStudentetByProfesorId(profId);
            System.out.println("Numri i studentëve të gjetur: " + studentet.size());

            for (Student s : studentet) {
                System.out.println("Student: " + s.getEmri() + " " + s.getMbiemri());
            }

            studentTable.getItems().setAll(studentet);
        } else {
            System.out.println("Profesor ID është 0!");
        }
    }


}
