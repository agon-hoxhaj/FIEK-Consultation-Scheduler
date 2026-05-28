package models.DTO_data_transfer_object;

import java.sql.Timestamp;

public class CreateFeedbackDto {
    private int idProfesori;
    private int idStudenti;
    private int vleresimi;
    private String komenti;
    private Timestamp data;

    public CreateFeedbackDto(int idProfesori, int idStudenti, int vleresimi, String komenti) {
        this.idProfesori = idProfesori;
        this.idStudenti = idStudenti;
        this.vleresimi = vleresimi;
        this.komenti = komenti;
    }

    public int getIdProfesori() {
        return idProfesori;
    }

    public void setIdProfesori(int idProfesori) {
        this.idProfesori = idProfesori;
    }

    public int getIdStudenti() {
        return idStudenti;
    }

    public void setIdStudenti(int idStudenti) {
        this.idStudenti = idStudenti;
    }

    public int getVleresimi() {
        return vleresimi;
    }

    public void setVleresimi(int vleresimi) {
        this.vleresimi = vleresimi;
    }

    public String getKomenti() {
        return komenti;
    }

    public void setKomenti(String komenti) {
        this.komenti = komenti;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }
}
