package controllers;

import enums.Gender;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DTO_data_transfer_object.CreateProfesorDto;
import models.Profesor;
import models.StafiAdministrativ;
import services.ProfesorService;
import services.SessionManager;
import services.UserService;

public class RegisterProfesorController extends BaseController{

    private ProfesorService profesorService = new ProfesorService();
    private UserService userService = new UserService();
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
    private CheckBox female;

    @FXML
    private CheckBox male;

    @FXML
    private Label errorGenderLabel;

    @FXML
    private void handleRegisterProfessor(){
        try{
            if(female.isSelected() && male.isSelected()){
                errorGenderLabel.setText("Only one gender can be selected!");
                return;
            }else if(!female.isSelected() && !male.isSelected()){
                errorGenderLabel.setText("You must select a gender!");
                return;
            }

            Gender gender = female.isSelected() ? Gender.F : Gender.M;

            String personalNum = personalNumber.getText().trim();
            String emri = firstName.getText().trim();
            String mbiemri = lastName.getText().trim();
            String emaili = email.getText().trim();
            String phoneNum = phoneNumber.getText().trim();

            if(personalNum.isEmpty() || emri.isEmpty() || mbiemri.isEmpty() || emaili.isEmpty() || phoneNum.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Error","Please fill all the fields!");
            }

            CreateProfesorDto dto = new CreateProfesorDto(
                    personalNum,
                    gender,
                    emri,
                    mbiemri,
                    emaili,
                    phoneNum,
                    72,
                    1,
                    userService.getLastAdded().getId(),
                    true
            );
            Profesor prof = profesorService.create(dto);
            if (prof == null || prof.getId() <=0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create professor!");
                return;
            }
            SessionManager.getInstance().setProfId(prof.getId());
            showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Successful!");
            navigateToProfessorInterface();
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Registration Failed!");
        }
    }
}
