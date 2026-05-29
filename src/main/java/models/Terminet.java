package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Terminet {
    private int id;
    private int idOrari;
    private int idStudenti;
    private Timestamp intervaliKohor;
    private String arsyeja;
    private boolean rezervuar;

    private Terminet(int id, int idOrari, int idStudenti, Timestamp intervaliKohor, String arsyeja, boolean rezervuar) {
        this.id = id;
        this.idOrari = idOrari;
        this.idStudenti = idStudenti;
        this.intervaliKohor = intervaliKohor;
        this.arsyeja = arsyeja;
        this.rezervuar = rezervuar;
    }

    public static Terminet getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int idOrari = resultSet.getInt("id_orari");
        int idStudenti = resultSet.getInt("id_studenti");
        Timestamp intervaliKohor = resultSet.getTimestamp("intervali_kohor");
        String arsyeja = resultSet.getString("arsyeja");
        boolean rezervuar = resultSet.getBoolean("rezervuar");

        return new Terminet(id, idOrari, idStudenti, intervaliKohor, arsyeja, rezervuar);
    }

    public int getId() {
        return id;
    }

    public int getIdOrari() {
        return idOrari;
    }

    public int getIdStudenti() {
        return idStudenti;
    }

    public Timestamp getIntervaliKohor() {
        return intervaliKohor;
    }

    public String getArsyeja() {
        return arsyeja;
    }

    public boolean isRezervuar() {
        return rezervuar;
    }
}
