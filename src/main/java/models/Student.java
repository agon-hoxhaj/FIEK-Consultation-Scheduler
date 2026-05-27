package models;

import enums.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Student {
    private int id;
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

    private Student(int id, String numriPersonal, Gender gender, String emri,
                    String mbiemri, String email, String numriTelefonit, int idPrejardhja,
                    int idStudimi, int userid, boolean student_aktiv){
        this.id = id;
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

    public static Student getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String numriPersonal = resultSet.getString("numri_personal");
        String emri = resultSet.getString("emri");
        String mbiemri = resultSet.getString("mbiemri");
        String email = resultSet.getString("email");
        String numriTelefonit = resultSet.getString("numri_telefonit");
        int idPrejardhja = resultSet.getInt("prejardhja");
        int idStudimi = resultSet.getInt("studimi");
        int userid = resultSet.getInt("perdoruesi");
        boolean student_aktiv = resultSet.getBoolean("student_aktiv");
        String genderStr = resultSet.getString("gjinia");

        Gender gender = (genderStr != null) ? Gender.valueOf(genderStr) : null;

        return new Student(id, numriPersonal, gender, emri, mbiemri, email,
                numriTelefonit, idPrejardhja, idStudimi,userid, student_aktiv);
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
        return this.idPrejardhja;
    }

    public int getStudimi(){
        return this.idStudimi;
    }

    public int getUserid(){
        return userid;
    }

    public boolean isStudent_aktiv(){
        return student_aktiv;
    }

}
