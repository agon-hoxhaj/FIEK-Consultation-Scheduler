package models.DTO_data_transfer_object;

public class CreateProfesorLendaDto {

    private int profesor;
    private int lenda;

    public CreateProfesorLendaDto( int profesor, int lenda) {
        this.profesor = profesor;
        this.lenda = lenda;
    }
    public int getIdProfesor() {return profesor;}

    public void setIdProfesor(int profesor) {this.profesor = profesor;}

    public int getIdLenda() {return lenda;}

    public void setIdLenda(int lenda) {this.lenda = lenda;}

}
