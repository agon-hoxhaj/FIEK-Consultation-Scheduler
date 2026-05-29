package models.DTO_data_transfer_object;

public class CreateVleresimiDto {
    private int id;
    private String emertimi;

    private CreateVleresimiDto(int id, String emertimi){
        this.id = id;
        this.emertimi = emertimi;
    }

    public int getId() { return id; }

    public String getEmertimi() { return emertimi; }

    public void setEmertimi(String emertimi) { this.emertimi = emertimi; }
}
