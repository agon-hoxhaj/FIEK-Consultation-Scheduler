package models.DTO_data_transfer_object;

import enums.DaysOfWeek;
import enums.DitaJaves;

import java.time.LocalTime;

public class CreateOrariDto {
    private int id;
    private int idDrejtimiNiveliSemestriLenda;
    private DitaJaves dita;
    private DaysOfWeek ditaEnglish;
    private LocalTime oraFillimit;
    private LocalTime oraMbarimit;
    private boolean statusiAktiv;

    public CreateOrariDto( int idDrejtimiNiveliSemestriLenda, DitaJaves dita,
                   DaysOfWeek ditaEnglish, LocalTime oraFillimit, LocalTime oraMbarimit, Boolean statusiAktiv) {

        this.idDrejtimiNiveliSemestriLenda = idDrejtimiNiveliSemestriLenda;
        this.dita = dita;
        this.ditaEnglish = ditaEnglish;
        this.oraFillimit = oraFillimit;
        this.oraMbarimit = oraMbarimit;
        this.statusiAktiv = statusiAktiv;
    }
    public int getId() { return id; }

    public int getIdDrejtimiNiveliSemestriLenda() { return idDrejtimiNiveliSemestriLenda; }

    public void setIdDrejtimiNiveliSemestriLenda(int idDrejtimiNiveliSemestriLenda) { this.idDrejtimiNiveliSemestriLenda = idDrejtimiNiveliSemestriLenda; }

    public String getDita() { return dita.name(); }

    public void setDita(DitaJaves dita) { this.dita = dita; }

    public String getDitaEnglish() { return ditaEnglish.name(); }

    public void setDitaEnglish(DaysOfWeek ditaEnglish) { this.ditaEnglish = ditaEnglish; }

    public LocalTime getOraFillimit() { return oraFillimit; }

    public void setOraFillimit(LocalTime oraFillimit) { this.oraFillimit = oraFillimit; }

    public LocalTime getOraMbarimit() { return oraMbarimit; }

    public void setOraMbarimit(LocalTime oraMbarimit) { this.oraMbarimit = oraMbarimit; }

    public boolean isStatusiAktiv() { return statusiAktiv; }

    public void setStatusiAktiv(boolean statusiAktiv) { this.statusiAktiv = statusiAktiv; }
}
