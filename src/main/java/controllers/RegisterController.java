package controllers;

import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.DTO_data_transfer_object.CreatePasswordsDto;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdatePasswordsDto;
import models.Passwords;
import models.User;
import services.PasswordsService;
import services.UserService;

public class RegisterController extends BaseController{

    private UserService userService = new UserService();
    private PasswordsService passwordsService = new PasswordsService();

    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

    @FXML
    private PasswordField retypedPassword;

    @FXML
    private CheckBox Student;

    @FXML
    private CheckBox Professor;

    @FXML
    private CheckBox Admin;

    @FXML
    private Label errorUsernameLabel;

    @FXML
    private Label errorPasswordLabel;

    @FXML
    private Label errorRetypedPasswordLabel;

    @FXML
    private void handleregister(){
        String username = Username.getText().trim();
        String password = Password.getText().trim();
        String retypedpassword = retypedPassword.getText().trim();

        if(username.isEmpty() || password.isEmpty() || retypedpassword.isEmpty()){
            errorPasswordLabel.setText("Please fill all the fields");
            errorUsernameLabel.setText("Please fill all the fields");
            errorRetypedPasswordLabel.setText("Please fill all the fields");
            return;
        }

        if(!password.equals(retypedpassword)){
            errorRetypedPasswordLabel.setText("Passwords don't match!");
            return;
        }

        int selectedCount =0;
        Role selectedRole = null;

        if(Student.isSelected()){
            selectedCount++;
            selectedRole = Role.student;
        }
        if(Professor.isSelected()){
            selectedCount++;
            selectedRole = Role.profesor;
        }
        if(Admin.isSelected()){
            selectedCount++;
            selectedRole = Role.staf_administrativ;
        }

        if(selectedCount !=1){
            showAlert(Alert.AlertType.ERROR,"Error","Please select exactly one role!");
            return;
        }

        try{
            if(userService.doesUsernameExist(username)){
                errorUsernameLabel.setText("Username already exists!");
                return;
            }

            String salt = passwordsService.generateSalt();
            String hashedPassword = passwordsService.hashPassword(password, salt, 100);
            CreatePasswordsDto createdPass = new CreatePasswordsDto(hashedPassword, salt, 100, "PBKDF2-HMAC-SHA256");
            Passwords pass = passwordsService.create(createdPass);

            if (pass == null || pass.getId() <=0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create password!");
                return;
            }
            int passwordId = pass.getId();


            CreateUserDto createdUser = new CreateUserDto(username, passwordId, selectedRole);
            User user = userService.create(createdUser);

            if (user == null || user.getId() <=0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create user!");
                return;
            }

            switch (selectedRole) {
                case student:
                    navigateToRegisterStudent();
                    break;
                case profesor:
                    navigateToRegisterProfesor();
                    break;
                case staf_administrativ:
                    navigateToRegisterAdmin();
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid role!");
            }

        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Error","An error occurred during registration!");
        }

    }

}
