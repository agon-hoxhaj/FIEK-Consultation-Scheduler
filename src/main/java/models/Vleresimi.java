package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vleresimi {
    private int id;
    private String emertimi;

    private Vleresimi(int id, String emertimi){
        this.id = id;
        this.emertimi = emertimi;
    }

    public static Vleresimi getInstance(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String emertimi = resultSet.getString("emertimi");

            return new Vleresimi(id, emertimi);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() { return id; }

    public String getEmertimi() { return emertimi; }
}
