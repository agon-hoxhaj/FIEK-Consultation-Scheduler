package models;

public class ProfesorLendaDisplay {
    private final String profEmri;
    private final String lendaEmri;

    public ProfesorLendaDisplay(String profEmri, String lendaEmri) {
        this.profEmri = profEmri;
        this.lendaEmri = lendaEmri;
    }

    public String getProfEmri() {
        return profEmri;
    }

    public String getLendaEmri() {
        return lendaEmri;
    }
}
