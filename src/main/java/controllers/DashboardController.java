package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import services.*;
import database.DBConnection;

import java.sql.*;

public class DashboardController extends BaseController {

    @FXML private Label lblTotalStudentat;
    @FXML private Label lblTotalTerminet;
    @FXML private Label lblTotalProfesoret;
    @FXML private Label lblAvgRating;
    @FXML private Label lblAiInsight;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private PieChart pieChart;
    @FXML private FlowPane aiTagsPane;

    private final Connection conn = DBConnection.getConnection();

    public void initialize() {
        loadKpiCards();
        loadBarChart();
        loadPieChart();
        loadAiInsight();
    }

    private void loadKpiCards() {
        lblTotalStudentat.setText(String.valueOf(queryInt("SELECT COUNT(*) FROM studentat WHERE student_aktiv=TRUE")));
        lblTotalProfesoret.setText(String.valueOf(queryInt("SELECT COUNT(*) FROM profesoret WHERE profesor_aktiv=TRUE")));
        lblTotalTerminet.setText(String.valueOf(queryInt("SELECT COUNT(*) FROM terminet WHERE rezervuar=TRUE AND intervali_kohor >= NOW()")));
        double avg = queryDouble("SELECT AVG(vleresimi) FROM feedback");
        lblAvgRating.setText(avg > 0 ? String.format("%.1f ★", avg) : "—");
    }

    private void loadBarChart() {
        boolean isAlb = languageManager.getLocale().getLanguage().equals("sq");
        xAxis.setLabel(isAlb ? "Drejtimet" : "Departments");
        yAxis.setLabel(isAlb ? "Numri i profesorëve" : "Number of professors");
        yAxis.setTickUnit(1);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String[] colors = {"#A3D5FF", "#FFD6A5", "#CAFFBF", "#D0BFFF"};
        int i = 0;

        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT d.drejtimi, COUNT(DISTINCT p.id) as n " +
                        "FROM profesoret p " +
                        "JOIN profesoret_lendet pl ON pl.id_profesori=p.id " +
                        "JOIN drejtimet_nivelet_semestrat_lendet dnsl ON dnsl.id_profesori_lenda=pl.id " +
                        "JOIN drejtimet_nivelet_semestrat dns ON dns.id=dnsl.id_drejtimi_niveli_semestri " +
                        "JOIN drejtimet d ON d.id=dns.id_drejtimi " +
                        "GROUP BY d.id, d.drejtimi")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String dept = rs.getString("drejtimi");
                int n = rs.getInt("n");
                XYChart.Data<String, Number> d = new XYChart.Data<>(dept, n);
                series.getData().add(d);
                final String color = colors[i % colors.length];
                i++;
                d.nodeProperty().addListener((obs, o, node) -> {
                    if (node != null) node.setStyle("-fx-bar-fill: " + color + ";");
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }

        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
    }

    private void loadPieChart() {
        pieChart.getData().clear();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT d.drejtimi, COUNT(DISTINCT p.id) as n " +
                        "FROM profesoret p " +
                        "JOIN profesoret_lendet pl ON pl.id_profesori=p.id " +
                        "JOIN drejtimet_nivelet_semestrat_lendet dnsl ON dnsl.id_profesori_lenda=pl.id " +
                        "JOIN drejtimet_nivelet_semestrat dns ON dns.id=dnsl.id_drejtimi_niveli_semestri " +
                        "JOIN drejtimet d ON d.id=dns.id_drejtimi " +
                        "GROUP BY d.id, d.drejtimi")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pieChart.getData().add(new PieChart.Data(rs.getString("drejtimi"), rs.getInt("n")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadAiInsight() {
        StringBuilder sb = new StringBuilder();
        boolean isAlb = languageManager.getLocale().getLanguage().equals("sq");
        aiTagsPane.getChildren().clear();

        try {
            // 1. Profesori me më shumë rezervime
            String sqlTopProf =
                    "SELECT pr.emri, pr.mbiemri, COUNT(t.id) as n " +
                            "FROM terminet t " +
                            "JOIN oraret_data od ON od.id=t.id_orari " +
                            "JOIN oraret o ON o.id=od.id_orari " +
                            "JOIN drejtimet_nivelet_semestrat_lendet dnsl ON dnsl.id=o.id_drejtimi_niveli_semestri_lenda " +
                            "JOIN profesoret_lendet pl ON pl.id=dnsl.id_profesori_lenda " +
                            "JOIN profesoret pr ON pr.id=pl.id_profesori " +
                            "WHERE t.rezervuar=TRUE GROUP BY pr.id, pr.emri, pr.mbiemri ORDER BY n DESC LIMIT 1";

            try (PreparedStatement ps = conn.prepareStatement(sqlTopProf)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("emri") + " " + rs.getString("mbiemri");
                    int cnt = rs.getInt("n");
                    sb.append(isAlb
                            ? "📈  Profesori më i kërkuar: " + name + " (" + cnt + " rezervime aktive)\n\n"
                            : "📈  Most requested professor: " + name + " (" + cnt + " active bookings)\n\n");
                    addTag(name + " 🔥", "#FFD6A5");
                }
            }

            // 2. Departamenti me më pak studentë
            String sqlMinDept =
                    "SELECT d.drejtimi, COUNT(s.id) as n " +
                            "FROM studentat s " +
                            "JOIN drejtimet_nivelet_semestrat dns ON s.studimi=dns.id " +
                            "JOIN drejtimet d ON d.id=dns.id_drejtimi " +
                            "WHERE s.student_aktiv=TRUE GROUP BY d.id, d.drejtimi ORDER BY n ASC LIMIT 1";

            try (PreparedStatement ps = conn.prepareStatement(sqlMinDept)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String dept = rs.getString("drejtimi");
                    int n = rs.getInt("n");
                    sb.append(isAlb
                            ? "⚠️  Departamenti me studentët më të paktë: " + dept + " (" + n + " studentë)\n   → Rekomandohet rishqyrtim i kapacitetit të konsultimeve.\n\n"
                            : "⚠️  Department with fewest students: " + dept + " (" + n + " students)\n   → Recommend reviewing consultation capacity.\n\n");
                    addTag(isAlb ? dept + " ⚠" : dept + " ⚠", "#FFADAD");
                }
            }

            // 3. Feedback i ulët
            String sqlLowFeed =
                    "SELECT pr.emri, pr.mbiemri, AVG(f.vleresimi) as avg_v " +
                            "FROM feedback f " +
                            "JOIN profesoret pr ON pr.id=f.id_profesori " +
                            "GROUP BY pr.id, pr.emri, pr.mbiemri " +
                            "HAVING AVG(f.vleresimi) < 3.0 " +
                            "ORDER BY avg_v ASC LIMIT 1";

            try (PreparedStatement ps = conn.prepareStatement(sqlLowFeed)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("emri") + " " + rs.getString("mbiemri");
                    double avg = rs.getDouble("avg_v");
                    sb.append(isAlb
                            ? "💬  Vlerësim i ulët: " + name + " (mesatare: " + String.format("%.1f", avg) + "★)\n   → Sugjerojmë vëmendje ndaj feedbackut të studentëve.\n"
                            : "💬  Low rating alert: " + name + " (avg: " + String.format("%.1f", avg) + "★)\n   → Suggest reviewing student feedback.\n");
                    addTag(name + " ★" + String.format("%.1f", avg), "#D0BFFF");
                } else {
                    sb.append(isAlb ? "✅  Të gjithë profesorët kanë vlerësim mbi 3.0 ★\n" : "✅  All professors have rating above 3.0 ★\n");
                    addTag(isAlb ? "Gjithçka OK ✅" : "All good ✅", "#CAFFBF");
                }
            }

        } catch (Exception e) {
            sb.append(isAlb ? "Nuk mund të ngarkohet analiza." : "Could not load analysis.");
            e.printStackTrace();
        }

        lblAiInsight.setText(sb.toString().trim());
    }

    private void addTag(String text, String color) {
        Label tag = new Label(text);
        tag.setStyle("-fx-background-color: " + color + "; -fx-text-fill: #121212; " +
                "-fx-padding: 3 10 3 10; -fx-background-radius: 99; -fx-font-size: 11px; -fx-font-weight: bold;");
        aiTagsPane.getChildren().add(tag);
    }

    private int queryInt(String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }

    private double queryDouble(String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }
}