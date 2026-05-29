package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Feedback;
import services.FeedbackService;
import services.ProfesoretLendetService;
import services.SessionManager;

import java.util.List;

public class ProfesoriFeedbackController extends BaseController {

    @FXML
    private TableView<FeedbackTableRow> feedbackTable;

    @FXML
    private TableColumn<FeedbackTableRow, String> commentColumn;

    @FXML
    private TableColumn<FeedbackTableRow, String> evaluationColumn;

    private final FeedbackService feedbackService = new FeedbackService();

    @FXML
    public void initialize() {
        int profesorId = SessionManager.getInstance().getProfId();

        List<Feedback> allFeedbacks = feedbackService.getAll();

        List<FeedbackTableRow> filteredFeedbacks = allFeedbacks.stream()
                .filter(fb -> fb.getIdProfesori() == profesorId)
                .map(fb -> new FeedbackTableRow(
                        fb.getKomenti(),
                        feedbackService.getEvaluationDescription(fb.getVleresimi())
                ))
                .toList();

        commentColumn.setCellValueFactory(new PropertyValueFactory<>("komenti"));
        evaluationColumn.setCellValueFactory(new PropertyValueFactory<>("vleresimi"));

        feedbackTable.setItems(FXCollections.observableArrayList(filteredFeedbacks));
    }

    public class FeedbackTableRow {
        private final String komenti;
        private final String vleresimi;

        public FeedbackTableRow(String komenti, String vleresimi) {
            this.komenti = komenti;
            this.vleresimi = vleresimi;
        }

        public String getKomenti() {
            return komenti;
        }

        public String getVleresimi() {
            return vleresimi;
        }
    }
}