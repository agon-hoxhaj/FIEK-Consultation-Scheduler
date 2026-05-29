package models.DTO_data_transfer_object;

public class CreateNiveletDto {

    private String niveli;
    private String niveliEnglish;

    public CreateNiveletDto(String niveli, String niveliEnglish) {
        this.niveli = niveli;
        this.niveliEnglish = niveliEnglish;
    }

    public String getNiveli() {return niveli;}

    public void setNiveli(String niveli) {this.niveli = niveli;}

    public String getNiveliEnglish() {return niveliEnglish;}

    public void setNiveliEnglish(String niveliEnglish) {this.niveliEnglish = niveliEnglish;}





}
