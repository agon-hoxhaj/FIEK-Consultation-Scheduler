package models.DTO_data_transfer_object;

public class CreateDrejtimetDto {

    private String drejtimi;
    private String drejtimiEnglish;

    public CreateDrejtimetDto(String drejtimi, String drejtimiEnglish) {
        this.drejtimi = drejtimi;
        this.drejtimiEnglish = drejtimiEnglish;
    }

    public String getDrejtimi() {
        return drejtimi;
    }

    public void setDrejtimi(String drejtimi) {
        this.drejtimi = drejtimi;
    }

    public String getDrejtimiEnglish() {
        return drejtimiEnglish;
    }

    public void setDrejtimiEnglish(String drejtimiEnglish) {
        this.drejtimiEnglish = drejtimiEnglish;
    }
}
