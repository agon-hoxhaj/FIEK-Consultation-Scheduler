package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Drejtimet {
    private int id;
    private String drejtimi;
    private String drejtimiEnglish;

    private Drejtimet(int id, String drejtimi, String drejtimiEnglish) {
        this.id = id;
        this.drejtimi = drejtimi;
        this.drejtimiEnglish = drejtimiEnglish;
    }

    public static Drejtimet getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String drejtimi = resultSet.getString("drejtimi");
        String drejtimiEnglish = resultSet.getString("drejtimi_english");

        return new Drejtimet(id, drejtimi, drejtimiEnglish);
    }

    public int getId() {
        return id;
    }

    public String getDrejtimi() {
        return drejtimi;
    }

    public String getDrejtimiEnglish() {
        return drejtimiEnglish;
    }
}