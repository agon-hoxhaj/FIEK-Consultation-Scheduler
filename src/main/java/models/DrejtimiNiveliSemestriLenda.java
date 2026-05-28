package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrejtimiNiveliSemestriLenda {
    private int id;
    private int idDrejtimi_Niveli_Semestri;
    private int idProfesori_Lenda;

    private DrejtimiNiveliSemestriLenda(int id, int idDrejtimi_Niveli_Semestri, int idProfesori_Lenda) {
        this.id = id;
        this.idDrejtimi_Niveli_Semestri = idDrejtimi_Niveli_Semestri;
        this.idProfesori_Lenda = idProfesori_Lenda;
    }
    public static DrejtimiNiveliSemestriLenda getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int idDrejtimi_Niveli_Semestri = resultSet.getInt("id_drejtimi_niveli_semestri");
        int idProfesori_Lenda = resultSet.getInt("id_profesori_lenda");

        return new DrejtimiNiveliSemestriLenda(id, idDrejtimi_Niveli_Semestri, idProfesori_Lenda);
    }

    public int getId() {return id;}
    public int getIdDrejtimi_Niveli_Semestri() {return idDrejtimi_Niveli_Semestri;}
    public int getIdProfesori_Lenda() {return idProfesori_Lenda;}
}
