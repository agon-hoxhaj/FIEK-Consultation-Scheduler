package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Nivelet {
    private int id;
    private String niveli;
    private String niveliEnglish;

    private Nivelet(int id, String niveli, String niveliEnglish) {
        this.id = id;
        this.niveli = niveli;
        this.niveliEnglish = niveliEnglish;
    }

    public static Nivelet getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String niveli = resultSet.getString("niveli");
        String niveliEnglish = resultSet.getString("niveli_english");

        return new Nivelet(id, niveli, niveliEnglish);
    }


    public int getId() {return id;}
    public String getNiveli() {return niveli;}
    public String getNiveliEnglish() {return niveliEnglish;}
}
