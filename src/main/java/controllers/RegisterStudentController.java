package controllers;

import enums.Gender;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.DTO_data_transfer_object.CreateStudentDto;
import models.Profesor;
import models.Student;
import services.*;
import utils.SceneLocator;

import java.util.Map;

public class RegisterStudentController extends BaseController  {

    private UserService userService = new UserService();
    private StudentService studentService = new StudentService();
    private DNSService dnsService = new DNSService();

    @FXML
    private TextField personalNumber;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ChoiceBox<String> departament;

    @FXML
    private ChoiceBox<String> level;

    @FXML
    private ChoiceBox<String> semester;

    @FXML
    private CheckBox female;

    @FXML
    private CheckBox male;

    @FXML
    private Label errorGenderLabel;

    private String selectedDepartament;
    private String selectedLevel;
    private String selectedSemester;
    private int idDepartament;
    private int idLevel;
    private int idSemester;

    private Map<String,Integer> drejtimiMap = Map.of(
            "IKS",1,
            "EAR", 2,
            "TIK",3,
            "ELEKTROENERGJETIKË",4
    );

    private Map<String, Integer> niveliMap = Map.of(
      "Bachelor",1,
      "Master",2,
      "PHD",3
    );

    private Map<String, Integer> semesterMap = Map.of(
            "1",1,
            "2",2,
            "3",3,
            "4",4,
            "5",5,
            "6",6
    );

    @FXML
    public void initialize(){
        populateChoiceBoxes();


        departament.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDepartament = newValue;
            idDepartament = drejtimiMap.getOrDefault(newValue,0);
        });

        level.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLevel = newValue;
            idLevel = niveliMap.getOrDefault(newValue,0);
        });

        semester.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedSemester = newValue;
            idSemester = semesterMap.getOrDefault(newValue,0);
        });
    }


    @FXML
    private void handleRegisterStudenti(){
     try{
          String personalNum = personalNumber.getText().trim();
          String emri = firstName.getText().trim();
           String mbiemri = lastName.getText().trim();
           String emaili = email.getText().trim();
           String phoneNum = phoneNumber.getText().trim();

            if(female.isSelected() && male.isSelected()){
                errorGenderLabel.setText("Only one gender can be selected!");
                return;
            }else if(!female.isSelected() && !male.isSelected()){
                errorGenderLabel.setText("You must select a gender!");
                return;
            }

         Gender gender = female.isSelected() ? Gender.F : Gender.M;
         if(personalNum.isEmpty() || emri.isEmpty() || mbiemri.isEmpty() || emaili.isEmpty() || phoneNum.isEmpty()){
             showAlert(Alert.AlertType.ERROR, "Error","Please fill all the fields!");
         }

         int idStudimi = dnsService.getIdStudimi(idDepartament, idLevel,idSemester);
         System.out.println(idDepartament + " " + idLevel + " " + idSemester);
         System.out.println(idStudimi);
         System.out.println(userService.getLastAdded().getId());

         CreateStudentDto dto = new CreateStudentDto(
                 personalNum,
                 gender,
                 emri,
                 mbiemri,
                 emaili,
                 phoneNum,
                 72,
                 idStudimi,
                 userService.getLastAdded().getId(),
                 true
         );

         Student student = studentService.create(dto);
         if (student == null || student.getId() <=0) {
             showAlert(Alert.AlertType.ERROR, "Error", "Failed to create student!");
             return;
         }
         SessionManager.getInstance().setStudentId(student.getId());
         showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Successful!");
         navigateToStudentInterface();
        }catch(Exception e){
         e.printStackTrace();
         showAlert(Alert.AlertType.ERROR, "Error", "Registration Failed!");
     }
    }

    private void populateChoiceBoxes(){
        departament.getItems().clear();
        departament.getItems().addAll(drejtimiMap.keySet());
        level.getItems().clear();
        level.getItems().addAll(niveliMap.keySet());
        semester.getItems().clear();
        semester.getItems().addAll(semesterMap.keySet());
        departament.setValue("IKS");
        selectedDepartament = "IKS";
        idDepartament = drejtimiMap.getOrDefault("IKS", 0);
        level.setValue("Bachelor");
        selectedLevel = "Bachelor";
        idLevel = niveliMap.getOrDefault("Bachelor", 0);
        semester.setValue("1");
        selectedSemester = "1";
        idSemester = semesterMap.getOrDefault("1", 0);
    }
}
