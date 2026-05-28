package models.DTO_data_transfer_object;

public class UpdateOrariDto {
    private int id;
    private boolean orariValid;

    public UpdateOrariDto(int id ,boolean valid){
        this.id=id;
        this.orariValid=valid;
    }

    public int getId() { return id; }

    public boolean isOrariValid() { return orariValid; }

    public void setOrariValid(boolean orariValid) { this.orariValid = orariValid; }
}
