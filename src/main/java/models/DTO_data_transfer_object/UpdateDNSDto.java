package models.DTO_data_transfer_object;

public class UpdateDNSDto {
    private int id;
    private int idDrejtimi;
    private int idNiveli;
    private int semestri;


    public UpdateDNSDto(int id, int idDrejtimi, int idNiveli, int semestri) {
        this.id = id;
        this.idDrejtimi = idDrejtimi;
        this.idNiveli = idNiveli;
        this.semestri = semestri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDrejtimi() {
        return idDrejtimi;
    }

    public void setIdDrejtimi(int idDrejtimi) {
        this.idDrejtimi = idDrejtimi;
    }

    public int getIdNiveli() {
        return idNiveli;
    }

    public void setIdNiveli(int idNiveli) {
        this.idNiveli = idNiveli;
    }

    public int getSemestri() {
        return semestri;
    }

    public void setSemestri(int semestri) {
        this.semestri = semestri;
    }

}

