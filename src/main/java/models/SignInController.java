package models;

import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.SceneLocator;

public class SignInController extends BaseController{

    private UserService userService = new UserService();
    private PasswordsService passwordsService = new PasswordsService();
    private StudentService studentService = new StudentService();
    private ProfesorService profesorService = new ProfesorService();
    private StafiAdministrativService  stafiAdministrativService = new StafiAdministrativService();

    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

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
    private void handleForgotPass() throws Exception{
        SceneManager.load(SceneLocator.CHANGE_PASSWORD);
    }

    @FXML
    private void handleLogin() {

        String username = this.Username.getText().trim();
        String password = this.Password.getText().trim();

        int selectedCount = 0;
        Role selectedRole =null;
        if(Student.isSelected()) {
            selectedCount++;
            selectedRole = Role.student;
        }
        if(Professor.isSelected()) {
            selectedCount++;
            selectedRole = Role.profesor;
        }

        if(Admin.isSelected()) {
            selectedCount++;
            selectedRole = Role.staf_administrativ;
        }

        if(selectedCount !=1){
            showAlert(Alert.AlertType.ERROR,"Error", "Please select exactly on role!");
            return;
        }

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username or password cannot be empty!");
            return;
        }

        try {
            int userId = userService.getUserIdByUsername(username);
            System.out.println("User Id: " + userId);

            SessionManager.getInstance().setUserId(userId);
            SessionManager.getInstance().setUsername(username);

            if (selectedRole == Role.profesor) {
                Profesor profesor = profesorService.getByUserId(userId);
                if (profesor != null) {
                    SessionManager.getInstance().setProfId(profesor.getId());
                }
            }


            if (userId == -1) {
                errorUsernameLabel.setText("Invalid username!");
                return;
            }

            User user = userService.getById(userId);
            SessionManager.getInstance().setCurrentUser(user);

            boolean isValid = passwordsService.verifyPassword(user.getPassword(), password);
            if (!isValid) {
                errorPasswordLabel.setText("Invalid password!");
                return;
            }

            if(Student.isSelected()) {
                SessionManager.getInstance().setStudentId(studentService.getIdByUserId(userId));
            }else if(Professor.isSelected()) {
                SessionManager.getInstance().setProfId(profesorService.getIdByUserId(userId));
            }else{
                SessionManager.getInstance().setAdminId(stafiAdministrativService.getIdByUserId(userId));
            }
            Role userRole = userService.getUserRoleByUserId(userId);
            if(!selectedRole.equals(userRole)){
                showAlert(Alert.AlertType.ERROR,"Error","Role mismatch for the user!");
            }else {

                if (Student.isSelected()) {
                    navigateToStudentInterface();
                } else if (Professor.isSelected()) {
                    navigateToProfessorInterface();
                } else if (Admin.isSelected()) {
                    navigateToAdminInterface();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during login!");
        }
    }
    }





