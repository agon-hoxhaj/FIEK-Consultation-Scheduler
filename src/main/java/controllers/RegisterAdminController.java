package controllers;

import enums.Gender;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DTO_data_transfer_object.CreateStafiAdministrativDto;
import models.StafiAdministrativ;
import services.SessionManager;
import services.StafiAdministrativService;
import services.UserService;

public class RegisterAdminController extends BaseController{

    private StafiAdministrativService stafiAdministrativService = new StafiAdministrativService();
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
    private TextField title;

    @FXML
    private CheckBox female;

    @FXML
    private CheckBox male;

    @FXML
    private Label errorGenderLabel;

    @FXML
    private void handleRegisterAdmin(){
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
            String titulli = title.getText().trim();

            if(personalNum.isEmpty() || emri.isEmpty() || mbiemri.isEmpty() || emaili.isEmpty() || phoneNum.isEmpty() || titulli.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Error","Please fill all the fields!");
            }

            CreateStafiAdministrativDto dto = new CreateStafiAdministrativDto(
                    personalNum,
                    gender,
                    emri,
                    mbiemri,
                    emaili,
                    phoneNum,
                    72,
                    userService.getLastAdded().getId(),
                    titulli
            );
            StafiAdministrativ stafi = stafiAdministrativService.create(dto);
            if (stafi == null || stafi.getId() <=0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create administrator!");
                return;
            }
            SessionManager.getInstance().setAdminId(stafi.getId());
            showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Successful!");
            navigateToAdminInterface();
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Registration Failed!");
        }
    }


}
