package models.DTO_data_transfer_object;

import java.util.Date;

public class CreateOrariDataDto {
    private int id;
    private int idOrari;
    java.sql.Date data;
    boolean orariValid;

    private CreateOrariDataDto( int idOrari , Date data ,boolean orariValid){
        this.idOrari=idOrari;
        this.data= new java.sql.Date(data.getTime());
        this.orariValid=orariValid;

    }
    public int getId() { return id; }

    public int getIdOrari() { return idOrari; }

    public void setIdOrari(int idOrari) { this.idOrari = idOrari; }

    public java.sql.Date getData() { return data; }

    public void setData(Date data) { this.data = new java.sql.Date(data.getTime()); }

    public boolean isOrariValid() { return orariValid; }

    public void setOrariValid(boolean orariValid) { this.orariValid = orariValid; }
}
