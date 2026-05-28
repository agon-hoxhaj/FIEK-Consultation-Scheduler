package models.DTO_data_transfer_object;

public class UpdateOrariDataDto {
    private int id;
    boolean orariValid;

    public UpdateOrariDataDto(boolean orariValid) {
        this.orariValid = orariValid;
    }

    public int getId() { return id; }

    public boolean isOrariValid() { return orariValid; }

    public void setOrariValid(boolean orariValid) { this.orariValid = orariValid; }
}
