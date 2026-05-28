package models.DTO_data_transfer_object;

public class UpdateDNSLDto {
    private int id;
    private int idDrejtimiNiveliSemestri;
    private int idProfesoriLenda;

    public UpdateDNSLDto(int idDrejtimiNiveliSemestri, int idProfesoriLenda) {
        this.idDrejtimiNiveliSemestri = idDrejtimiNiveliSemestri;
        this.idProfesoriLenda = idProfesoriLenda;
    }

    public int getId() {return id;}

    public int getIdDrejtimiNiveliSemestri() {
        return idDrejtimiNiveliSemestri;
    }

    public void setIdDrejtimiNiveliSemestri(int idDrejtimiNiveliSemestri) {
        this.idDrejtimiNiveliSemestri = idDrejtimiNiveliSemestri;
    }

    public int getIdProfesoriLenda() {
        return idProfesoriLenda;
    }

    public void setIdProfesoriLenda(int idProfesoriLenda) {
        this.idProfesoriLenda = idProfesoriLenda;
    }
}
