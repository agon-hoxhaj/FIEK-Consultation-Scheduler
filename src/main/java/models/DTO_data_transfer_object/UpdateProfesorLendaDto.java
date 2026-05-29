package models.DTO_data_transfer_object;

public class UpdateProfesorLendaDto {
    private int id;
    private int profesor;
    private int lenda;

    public UpdateProfesorLendaDto(int id, int profesor, int lenda) {
        this.id = id;
        this.profesor = profesor;
        this.lenda = lenda;
    }
    public int getId() {return this.id;}

    public int getIdProfesor() {return this.profesor;}

    public void setIdProfesor(int profesor) {this.profesor = profesor;}

    public int getIdLenda() {return this.lenda;}

    public void setIdLenda(int lenda) {this.lenda = lenda;}

}

