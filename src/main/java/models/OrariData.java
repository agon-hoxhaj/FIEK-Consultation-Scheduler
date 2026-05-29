package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OrariData {
    private int id;
    private int idOrari;
    Date data;
    boolean orariValid;

    private OrariData(int id, int idOrari , Date data ,boolean orariValid){
        this.id=id;
        this.idOrari=idOrari;
        this.data= data;
        this.orariValid=orariValid;

    }

    public static OrariData getInstance(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            int idOrari = resultSet.getInt("id_orari");
            Date data = resultSet.getDate("data");
            boolean orariValid = resultSet.getBoolean("orari_valid");

            return new OrariData(id,idOrari,data,orariValid);

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int getId() { return id; }

    public int getIdOrari() { return idOrari; }

    public Date getData() { return data; }

    public boolean isOrariValid() { return orariValid; }
}
