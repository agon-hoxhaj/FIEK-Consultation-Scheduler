package models.DTO_data_transfer_object;

import enums.Gender;

public class CreateStafiAdministrativDto {
    private String numriPersonal;
    private Gender gender;
    private String emri;
    private String mbiemri;
    private String email;
    private String numriTelefonit;
    private int idPrejardhja;
    private int userId;
    private String titulli;

    public CreateStafiAdministrativDto( String numriPersonal, Gender gender, String emri, String mbiemri,
                                        String email, String numriTelefonit, int idPrejardhja,
                                        int userId, String titulli){
        this.numriPersonal = numriPersonal;
        this.gender = gender;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.numriTelefonit = numriTelefonit;
        this.idPrejardhja = idPrejardhja;
        this.userId = userId;
        this.titulli = titulli;
    }

    public String getNumriPersonal() {
        return this.numriPersonal;
    }

    public void setNumriPersonal(String numriPersonal) {
        this.numriPersonal = numriPersonal;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmri() {
        return this.emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiemri() {
        return this.mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumriTelefonit() {
        return this.numriTelefonit;
    }

    public void setNumriTelefonit(String numriTelefonit) {
        this.numriTelefonit = numriTelefonit;
    }

    public int getPrejardhja() {
        return this.idPrejardhja;
    }

    public void setPrejardhja(int idPrejardhja) {
        this.idPrejardhja = idPrejardhja;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitulli() {
        return this.titulli;
    }

    public void setTitulli(String titulli) {
        this.titulli = titulli;
    }

}
