package models.DTO_data_transfer_object;


public class UpdateStafiAdministrativDto {
    private int id;
    private String numriTelefonit;
    private String titulli;
    private int idUser;

    public UpdateStafiAdministrativDto(String numriTelefonit, String titulli){
        this.numriTelefonit = numriTelefonit;
        this.titulli = titulli;
    }

    public int getId(){
        return this.id;
    }

    public int getIdUser(){
        return this.idUser;
    }

    public void setIdUser(int idUser){
        this.idUser = idUser;
    }

    public String getNumriTelefonit() {
        return this.numriTelefonit;
    }

    public void setNumriTelefonit(String numriTelefonit) {
        this.numriTelefonit = numriTelefonit;
    }

    public String getTitulli() {
        return this.titulli;
    }

    public void setTitulli(String titulli) {
        this.titulli = titulli;
    }
}
