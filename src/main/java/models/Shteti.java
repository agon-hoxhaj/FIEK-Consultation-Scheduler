package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Shteti {
    private int id;
    private String shteti;
    private String shtetiEnglish;

    private Shteti(int id, String shteti, String shtetiEnglish){
        this.id = id;
        this.shteti = shteti;
        this.shtetiEnglish = shtetiEnglish;
    }

    public static Shteti getInstance(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String shteti = resultSet.getString("shteti");
            String shtetiEnglish = resultSet.getString("shteti_english");

            return new Shteti(id, shteti, shtetiEnglish);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() { return id; }

    public String getShteti() { return shteti; }

    public String getShtetiEnglish() { return shtetiEnglish; }
}
