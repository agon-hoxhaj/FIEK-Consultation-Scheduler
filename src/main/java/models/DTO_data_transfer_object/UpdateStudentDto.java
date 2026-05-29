package models.DTO_data_transfer_object;


public class UpdateStudentDto {
    private int id;
    private int idUser;
    private String numriTelefonit;
    private int idStudimi;
    private boolean student_aktiv;

    public UpdateStudentDto(String numriTelefonit, int idStudimi, boolean student_aktiv){
        this.numriTelefonit = numriTelefonit;
        this.idStudimi = idStudimi;
        this.student_aktiv = student_aktiv;
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

    public int getStudimi(){
        return this.idStudimi;
    }

    public void setStudimi(int idStudimi){
        this.idStudimi = idStudimi;
    }

    public boolean isStudent_aktiv(){
        return this.student_aktiv;
    }

    public void setStudent_aktiv(boolean student_aktiv){
        this.student_aktiv = student_aktiv;
    }
}
