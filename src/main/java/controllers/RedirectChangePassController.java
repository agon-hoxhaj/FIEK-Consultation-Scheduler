package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.DTO_data_transfer_object.CreatePasswordsDto;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdatePasswordsDto;
import models.DTO_data_transfer_object.UpdateUserDto;
import models.Passwords;
import models.User;
import services.PasswordsService;
import services.SceneManager;
import services.SessionManager;
import services.UserService;
import utils.SceneLocator;

public class RedirectChangePassController extends BaseController {

    private PasswordsService passwordsService = new PasswordsService();
    private UserService userService = new UserService();
    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField confirmedPass;

    @FXML
    private Label errorPasswordLabel;

    @FXML
    private void handleChangePassword(){
        try {
            String password = newPass.getText().trim();
            String retypedpassword = confirmedPass.getText().trim();

            if (!password.equals(retypedpassword)) {
                errorPasswordLabel.setText("Passwords don't match!");
                return;
            }

            String username = SessionManager.getInstance().getUsername();
            String salt = passwordsService.generateSalt();
            int id = passwordsService.getPasswordIdByUsername(username);
            String hashedPassword = passwordsService.hashPassword(password, salt, 100);
            CreatePasswordsDto createdPass = new CreatePasswordsDto(hashedPassword, salt, 100, "PBKDF2-HMAC-SHA256");
            Passwords pass = passwordsService.create(createdPass);


            if (pass == null || pass.getId() <=0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password!");
                return;
            }
            int passwordId = pass.getId();

            UpdateUserDto updatedUser = new UpdateUserDto(passwordId);
            User user = userService.updatePassword(userService.getUserIdByUsername(username), updatedUser);

            if (user == null || user.getId() <=0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user!");
                return;
            }

            boolean updated = passwordsService.updateUserId(passwordId, user.getId());
            if (!updated) {
                throw new Exception("Failed to update password!");
            }
            SceneManager.load(SceneLocator.SIGN_IN);
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password!");
        }
    }

}
