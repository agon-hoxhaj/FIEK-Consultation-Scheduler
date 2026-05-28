package models.DTO_data_transfer_object;

import java.sql.Timestamp;

public class CreateTerminetDto {
    private int idOrari;
    private int idStudenti;
    private Timestamp intervaliKohor;
    private String arsyeja;
    private boolean rezervuar;

    public CreateTerminetDto(int idOrari, int idStudenti, Timestamp intervaliKohor, String arsyeja, boolean rezervuar) {
        this.idOrari = idOrari;
        this.idStudenti = idStudenti;
        this.intervaliKohor = intervaliKohor;
        this.arsyeja = arsyeja;
        this.rezervuar = rezervuar;
    }

    // Getters and Setters
    public int getIdOrari() {
        return idOrari;
    }

    public void setIdOrari(int idOrari) {
        this.idOrari = idOrari;
    }

    public int getIdStudenti() {
        return idStudenti;
    }

    public void setIdStudenti(int idStudenti) {
        this.idStudenti = idStudenti;
    }

    public Timestamp getIntervaliKohor() {
        return intervaliKohor;
    }

    public void setIntervaliKohor(Timestamp intervaliKohor) {
        this.intervaliKohor = intervaliKohor;
    }

    public String getArsyeja() {
        return arsyeja;
    }

    public void setArsyeja(String arsyeja) {
        this.arsyeja = arsyeja;
    }

    public boolean isRezervuar() {
        return rezervuar;
    }

    public void setRezervuar(boolean rezervuar) {
        this.rezervuar = rezervuar;
    }
}
