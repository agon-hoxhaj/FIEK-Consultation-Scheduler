package models.DTO_data_transfer_object;

public class UpdateProfesorDto {
    private int id;
    private int idUser;
    private String numriTelefonit;
    private int kabinetiPersonal;
    private boolean profesor_aktiv;

    public UpdateProfesorDto(String numriTelefonit, int kabinetiPersonal, boolean profesor_aktiv){
        this.numriTelefonit = numriTelefonit;
        this.kabinetiPersonal = kabinetiPersonal;
        this.profesor_aktiv = profesor_aktiv;
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

    public String getNumriTelefonit(){
        return this.numriTelefonit;
    }

    public void setNumriTelefonit(String numriTelefonit){
        this.numriTelefonit = numriTelefonit;
    }

    public int getKabinetiPersonal(){
        return this.kabinetiPersonal;
    }

    public void setKabinetiPersonal(int kabinetiPersonal){
        this.kabinetiPersonal = kabinetiPersonal;
    }

    public boolean isProfesor_aktiv(){
        return this.profesor_aktiv;
    }

    public void setProfesor_aktiv(boolean profesor_aktiv){
        this.profesor_aktiv = profesor_aktiv;
    }

}
