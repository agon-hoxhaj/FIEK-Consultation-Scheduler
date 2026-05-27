package models;

import enums.Gender;
import enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StafiAdministrativ {
    private int id;
    private String numriPersonal;
    private Gender gender;
    private String emri;
    private String mbiemri;
    private String email;
    private String numriTelefonit;
    private int idPrejardhja;
    private int userId;
    private String titulli;

    private StafiAdministrativ(int id, String numriPersonal, Gender gender, String emri, String mbiemri, String email, String numriTelefonit,
                               int idPrejardhja, int userId, String titulli){
        this.id = id;
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

    public static StafiAdministrativ getInstance( ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String numriPersonal = res.getString("numri_personal");
        Gender gender = Gender.valueOf(res.getString("gjinia"));
        String emri = res.getString("emri");
        String mbiemri = res.getString("mbiemri");
        String email = res.getString("email");
        String numriTelefonit = res.getString("numri_telefonit");
        int idPrejardhja = res.getInt("prejardhja");
        int userId = res.getInt("perdoruesi");
        String titulli = res.getString("titulli");

        return new StafiAdministrativ(id, numriPersonal, gender, emri, mbiemri, email, numriTelefonit,
                idPrejardhja, userId, titulli);
    }

    public int getId(){
        return this.id;
    }

    public String getNumriPersonal(){
        return this.numriPersonal;
    }

    public Gender getGender(){
        return this.gender;
    }

    public String getEmri(){
        return this.emri;
    }

    public String getMbiemri(){
        return this.mbiemri;
    }

    public String getEmail(){
        return this.email;
    }

    public String getNumriTelefonit(){
        return this.numriTelefonit;
    }

    public int getPrejardhja(){
        return this.idPrejardhja;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getTitulli(){
        return this.titulli;
    }

}
