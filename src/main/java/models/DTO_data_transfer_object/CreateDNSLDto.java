package models.DTO_data_transfer_object;

public class CreateDNSLDto {
    private int id;
    private int idDrejtimiNiveliSemestri;
    private int idProfesoriLenda;

    public CreateDNSLDto(int id, int idDrejtimiNiveliSemestri, int idProfesoriLenda) {
        this.id = id;
        this.idDrejtimiNiveliSemestri = idDrejtimiNiveliSemestri;
        this.idProfesoriLenda = idProfesoriLenda;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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