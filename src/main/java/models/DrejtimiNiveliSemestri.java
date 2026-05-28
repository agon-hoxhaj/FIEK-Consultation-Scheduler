package models;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DrejtimiNiveliSemestri {
    private int id;
    private int idDrejtimi;
    private int idNiveli;
    private int semestri;

    private DrejtimiNiveliSemestri(int id, int idDrejtimi, int idNiveli, int semestri) {
        this.id = id;
        this.idDrejtimi = idDrejtimi;
        this.idNiveli = idNiveli;
        this.semestri = semestri;
    }
    public static DrejtimiNiveliSemestri getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int idDrejtimi = resultSet.getInt("id_drejtimi");
        int idNiveli = resultSet.getInt("id_niveli");
        int semestri = resultSet.getInt("semestri");

        return new DrejtimiNiveliSemestri(id, idDrejtimi, idNiveli, semestri);
    }

    public int getId() {return id;}
    public int getIdDrejtimi() {return idDrejtimi;}
    public int getIdNiveli() {return idNiveli;}
    public int getSemestri() {return semestri;}

}
