package models.DTO_data_transfer_object;

import enums.Gender;

public class CreateStudentDto {

    private String numriPersonal;
    private Gender gender;
    private String emri;
    private String mbiemri;
    private String email;
    private String numriTelefonit;
    private int idPrejardhja;
    private int idStudimi;
    private int userid;
    private boolean student_aktiv;

    public CreateStudentDto(String numriPersonal, Gender gender, String emri, String mbiemri,
                            String email, String numriTelefonit, int idPrejardhja,
                            int idStudimi, int userid, boolean student_aktiv){
        this.numriPersonal = numriPersonal;
        this.gender = gender;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.numriTelefonit = numriTelefonit;
        this.idPrejardhja = idPrejardhja;
        this.idStudimi = idStudimi;
        this.userid = userid;
        this.student_aktiv = student_aktiv;
    }

    public String getNumriPersonal(){
        return this.numriPersonal;
    }

    public void setNumriPersonal(String numriPersonal){
        this.numriPersonal = numriPersonal;
    }

    public Gender getGender(){
        return this.gender;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public String getEmri(){
        return this.emri;
    }

    public void setEmri(String emri){
        this.emri = emri;
    }

    public String getMbiemri(){
        return this.mbiemri;
    }

    public void setMbiemri(String mbiemri){
        this.mbiemri = mbiemri;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getNumriTelefonit(){
        return this.numriTelefonit;
    }

    public void setNumriTelefonit(String numriTelefonit){
        this.numriTelefonit = numriTelefonit;
    }

    public int getPrejardhja(){
        return this.idPrejardhja;
    }

    public void setPrejardhja(int idPrejardhja){
        this.idPrejardhja = idPrejardhja;
    }

    public int getStudimi(){
        return this.idStudimi;
    }

    public void setStudimi(int idStudimi){
        this.idStudimi = idStudimi;
    }

    public int getUserid(){
        return this.userid;
    }

    public void setUserid(int userid){
        this.userid = userid;
    }

    public boolean isStudent_aktiv(){
        return this.student_aktiv;
    }

    public void setStudent_aktiv(boolean student_aktiv){
        this.student_aktiv = student_aktiv;
    }
}
