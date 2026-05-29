package models.DTO_data_transfer_object;

public class UpdateNiveletDto {

    private int id;
    private String niveli;
    private String niveliEnglish;

    public UpdateNiveletDto(int id, String niveli, String niveliEnglish) {
        this.id = id;
        this.niveli = niveli;
        this.niveliEnglish = niveliEnglish;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNiveli() {return niveli;}

    public void setNiveli(String niveli) {this.niveli = niveli;}

    public String getNiveliEnglish() {return niveliEnglish;}

    public void setNiveliEnglish(String niveliEnglish) {this.niveliEnglish = niveliEnglish;}

}
