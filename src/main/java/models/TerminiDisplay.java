package models;

import javafx.beans.property.SimpleStringProperty;

public class TerminiDisplay {
    private final SimpleStringProperty koha;
    private final SimpleStringProperty lenda;
    private final SimpleStringProperty studenti;
    private final SimpleStringProperty arsyeja;


    public TerminiDisplay(String koha, String lenda, String studenti ,String arsyeja) {
        this.koha = new SimpleStringProperty(koha);
        this.lenda = new SimpleStringProperty(lenda);
        this.studenti = new SimpleStringProperty(studenti);
        this.arsyeja = new SimpleStringProperty(arsyeja);

    }

    public String getKoha() {
        return koha.get();
    }

    public String getLenda() {
        return lenda.get();
    }

    public String getStudenti() {
        return studenti.get();
    }

    public String getArsyea(){
        return arsyeja.get();
    };
}
