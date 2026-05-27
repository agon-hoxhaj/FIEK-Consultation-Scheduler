package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Lenda {
    private int id;
    private String emri;
    private String emriEnglish;

    private Lenda(int id, String emri, String emriEnglish) {
        this.id = id;
        this.emri = emri;
        this.emriEnglish = emriEnglish;
    }

    public static Lenda getInstance(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String emri = resultSet.getString("emri");
            String emriEnglish = resultSet.getString("emri_english");

            return new Lenda(id, emri, emriEnglish);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() { return id; }

    public String getEmri() { return emri; }

    public String getEmriEnglish() { return emriEnglish; }

}
