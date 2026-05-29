package models.DTO_data_transfer_object;

import java.sql.Timestamp;

public class UpdateFeedbackDto {

    private int id;
    private int vleresimi;
    private String komenti;
    private Timestamp data;

    public UpdateFeedbackDto(int id, int vleresimi, String komenti, Timestamp data) {
        this.id = id;
        this.vleresimi = vleresimi;
        this.komenti = komenti;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
