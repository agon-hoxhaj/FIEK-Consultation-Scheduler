package controllers;

import javafx.fxml.FXML;
import services.StageManager;

public class HelpModalController {

    @FXML
    public void handleClose() {
        try {
            StageManager.getInstance().closeModal();
        } catch (Exception e) {
            System.out.println("Failed to close modal cleanly: " + e.getMessage());
        }
    }
}