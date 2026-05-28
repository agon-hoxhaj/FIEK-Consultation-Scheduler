package models.DTO_data_transfer_object;

public class UpdateLendaDto {
    private int id;
    private String emri;
    private String emriEnglish;

    public UpdateLendaDto(String emri, String emriEnglish) {
        this.emri = emri;
        this.emriEnglish = emriEnglish;
    }
    public int getId() { return id; }

    public String getEmri() { return emri; }

    public void setEmri(String emri) { this.emri = emri; }

    public String getEmriEnglish() { return emriEnglish; }

    public void setEmriEnglish(String emriEnglish) { this.emriEnglish = emriEnglish; }
}
