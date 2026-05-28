package models;

import java.sql.ResultSet;
import java.time.LocalTime;
import enums.*;
import java.sql.SQLException;

public class Orari {
    private int id;
    private int idDrejtimiNiveliSemestriLenda;
    private DitaJaves dita;
    private DaysOfWeek ditaEnglish;
    private LocalTime oraFillimit;
    private LocalTime oraMbarimit;
    private boolean statusiAktiv;

    private Orari(int id, int idDrejtimiNiveliSemestriLenda, DitaJaves dita,
                 DaysOfWeek ditaEnglish, LocalTime oraFillimit, LocalTime oraMbarimit, Boolean statusiAktiv) {
        this.id = id;
        this.idDrejtimiNiveliSemestriLenda = idDrejtimiNiveliSemestriLenda;
        this.dita = dita;
        this.ditaEnglish = ditaEnglish;
        this.oraFillimit = oraFillimit;
        this.oraMbarimit = oraMbarimit;
        this.statusiAktiv = statusiAktiv;
    }

    public static Orari getInstance(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int idDrejtimiNiveliSemestriLenda = resultSet.getInt("id_drejtimi_niveli_semestri_lenda");
            DitaJaves dita = DitaJaves.valueOf(resultSet.getString("dita"));
            DaysOfWeek ditaEnglish = DaysOfWeek.valueOf(resultSet.getString("dita_english"));
            LocalTime oraFillimit = resultSet.getTime("ora_fillimit").toLocalTime();
            LocalTime oraMbarimit = resultSet.getTime("ora_mbarimit").toLocalTime();
            boolean statusiAktiv = resultSet.getBoolean("statusi_aktiv");

            return new Orari(id,idDrejtimiNiveliSemestriLenda,dita,ditaEnglish, oraFillimit,oraMbarimit,statusiAktiv);

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int getId() { return id; }

    public int getIdDrejtimiNiveliSemestriLenda() { return idDrejtimiNiveliSemestriLenda; }

    public DitaJaves getDita() { return dita; }

    public DaysOfWeek getDitaEnglish() { return ditaEnglish; }

    public LocalTime getOraFillimit() { return oraFillimit; }

    public LocalTime getOraMbarimit() { return oraMbarimit; }

    public boolean getStatusiAktiv() { return statusiAktiv; }
}
