package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.SceneManager;
import services.SessionManager;
import services.UserService;
import utils.SceneLocator;

public class ChangePasswordController extends BaseController{

    private UserService userService = new UserService();
    @FXML
    private TextField user;

    @FXML
    private TextField personalNumber;

    @FXML
    private Label errorUsernameLabel;

    @FXML
    private Label errorPersonalNumLabel;

    @FXML
    private void handleChangePass(){
        try{
            String username = user.getText().trim();
            String personalNum = personalNumber.getText().trim();

            if(username.isEmpty()){
                errorUsernameLabel.setText("Username is required!");
                return;
            }

            if(personalNum.isEmpty()){
                errorPersonalNumLabel.setText("Personal number is required!");
                return;
            }

            boolean isSame = userService.validatePersonalNumberAndUser(username,personalNum);
            if(!isSame){
                showAlert(Alert.AlertType.ERROR,"ERROR","Invalid username or personal number!");
                return;
            }

            SessionManager.getInstance().setUsername(username);
            SceneManager.load(SceneLocator.REDIRECT_CHANGE_PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Error","There was a problem! Please try again!");
        }

    }

}
