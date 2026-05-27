package models;

import enums.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Profesor {
    private int id;
    private String numriPersonal;
    private Gender gender;
    private String emri;
    private String mbiemri;
    private String email;
    private String numriTelefonit;
    private int prejardhja;
    private int userid;
    private int kabinetiPersonal;
    private boolean profesor_aktiv;

    private Profesor(int id, String numriPersonal, Gender gender, String emri, String mbiemri, String email,
                     String numriTelefonit, int prejardhja, int userid, int kabinetiPersonal, boolean profesor_aktiv){
        this.id = id;
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

    public static Profesor getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String numriPersonal = resultSet.getString("numri_personal");
        String emri = resultSet.getString("emri");
        String mbiemri = resultSet.getString("mbiemri");
        String email = resultSet.getString("email");
        String numriTelefonit = resultSet.getString("numri_telefonit");
        int prejardhja = resultSet.getInt("prejardhja");
        int kabinetiPersonal = resultSet.getInt("kabineti_personal");
        int userid = resultSet.getInt("perdoruesi");
        boolean profesor_aktiv = resultSet.getBoolean("profesor_aktiv");

        String genderStr = resultSet.getString("gjinia");

        Gender gender = (genderStr != null) ? Gender.valueOf(genderStr) : null;

        return new Profesor(id, numriPersonal, gender, emri, mbiemri, email,
                numriTelefonit, prejardhja, userid, kabinetiPersonal, profesor_aktiv);
    }

    public int getId(){
        return id;
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
        return this.prejardhja;
    }

    public int getKabinetiPersonal(){ return this.kabinetiPersonal;}

    public int getUserid(){
        return userid;
    }

    public boolean isProfesor_aktiv(){
        return profesor_aktiv;
    }

    public String toString() {
        return emri + " " + mbiemri;
    }
}
