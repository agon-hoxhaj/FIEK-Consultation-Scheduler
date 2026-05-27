package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesorLenda {
    private int id;
    private int idProfesor;
    private int idLenda;

    private ProfesorLenda(int idProfesor, int idLenda) {
        this.idProfesor = idProfesor;
        this.idLenda = idLenda;
    }
    public static ProfesorLenda getInstance(ResultSet resultSet) throws SQLException {
        int idProfesor = resultSet.getInt("id_profesori");
        int idLenda = resultSet.getInt("id_lenda");

        return new ProfesorLenda(idProfesor, idLenda);
    }
    public int getId() {return this.id;}
    public int getIdProfesor() {return idProfesor;}
    public int getIdLenda() {return idLenda;}
}
