package models.DTO_data_transfer_object;

public class UpdateVleresimiDto {
    private int id;
    private String emertimi;

    public UpdateVleresimiDto(String emertimi) {
        this.emertimi = emertimi;
    }
    public int getId() { return id; }

    public String getEmertimi() { return emertimi; }

    public void setEmertimi(String emertimi) { this.emertimi = emertimi; }
}
