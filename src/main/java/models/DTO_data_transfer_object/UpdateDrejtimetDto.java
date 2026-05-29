package models.DTO_data_transfer_object;

public class UpdateDrejtimetDto {

    private int id;
    private String drejtimi;
    private String drejtimiEnglish;

    public UpdateDrejtimetDto(int id, String drejtimi, String drejtimiEnglish) {
        this.id = id;
        this.drejtimi = drejtimi;
        this.drejtimiEnglish = drejtimiEnglish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

