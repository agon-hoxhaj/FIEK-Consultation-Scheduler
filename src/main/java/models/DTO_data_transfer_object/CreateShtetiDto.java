package models.DTO_data_transfer_object;

public class CreateShtetiDto {
    private int id;
    private String shteti;
    private String shtetiEnglish;

    private CreateShtetiDto(int id, String shtetiEnglish){
        this.id = id;
        this.shtetiEnglish = shtetiEnglish;
    }

    public int getId() { return id; }

    public String getShteti() { return shteti; }

    public void setShteti(String shteti) { this.shteti = shteti; }

    public String getShtetiEnglish() { return shtetiEnglish; }

    public void setShtetiEnglish(String shtetiEnglish) { this.shtetiEnglish = shtetiEnglish; }

}
