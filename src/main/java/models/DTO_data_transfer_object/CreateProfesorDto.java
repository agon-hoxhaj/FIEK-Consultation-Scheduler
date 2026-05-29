package models.DTO_data_transfer_object;

import enums.Gender;

public class CreateProfesorDto {

    private String numriPersonal;
    private Gender gender;
    private String emri;
    private String mbiemri;
    private String email;
    private String numriTelefonit;
    private int prejardhja;
    private int kabinetiPersonal;
    private int userid;
    private boolean profesor_aktiv;

    public CreateProfesorDto(String numriPersonal, Gender gender, String emri, String mbiemri,
                            String email, String numriTelefonit, int prejardhja,
                            int kabinetiPersonal, int userid, boolean profesor_aktiv){
        this.numriPersonal = numriPersonal;
        this.gender = gender;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.numriTelefonit = numriTelefonit;
        this.prejardhja = prejardhja;
        this.kabinetiPersonal = kabinetiPersonal;
        this.userid = userid;
        this.profesor_aktiv = profesor_aktiv;
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
        return this.prejardhja;
    }

    public void setPrejardhja(int prejardhja){
        this.prejardhja = prejardhja;
    }

    public int getKabinetiPersonal(){
        return this.kabinetiPersonal;
    }

    public void setKabinetiPersonal(int kabinetiPersonal){
        this.kabinetiPersonal = kabinetiPersonal;
    }

    public int getUserid(){
        return this.userid;
    }

    public void setUserid(int userid){
        this.userid = userid;
    }

    public boolean isProfesor_aktiv(){
        return this.profesor_aktiv;
    }

    public void setProfesor_aktiv(boolean profesor_aktiv){
        this.profesor_aktiv = profesor_aktiv;
    }
}
