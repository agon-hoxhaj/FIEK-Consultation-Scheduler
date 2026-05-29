package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Feedback {
    private int id;
    private int idProfesori;
    private int idStudenti;
    private Timestamp data;
    private int vleresimi;
    private String komenti;

    private Feedback(int id, int idProfesori, int idStudenti, Timestamp data, int vleresimi, String komenti) {
        this.id = id;
        this.idProfesori = idProfesori;
        this.idStudenti = idStudenti;
        this.data = data;
        this.vleresimi = vleresimi;
        this.komenti = komenti;
    }

    public static Feedback getInstance(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int idProfesori = resultSet.getInt("id_profesori");
            int idStudenti = resultSet.getInt("id_studenti");
            Timestamp data = resultSet.getTimestamp("data");
            int vleresimi = resultSet.getInt("vleresimi");
            String komenti = resultSet.getString("komenti");

            return new Feedback(id, idProfesori, idStudenti, data, vleresimi, komenti);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public int getIdProfesori() {
        return idProfesori;
    }

    public int getIdStudenti() {
        return idStudenti;
    }

    public Timestamp getData() {
        return data;
    }

    public int getVleresimi() {
        return vleresimi;
    }

    public String getKomenti() {
        return komenti;
    }
}
