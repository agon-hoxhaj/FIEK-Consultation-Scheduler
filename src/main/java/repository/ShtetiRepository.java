package repository;

import models.DTO_data_transfer_object.CreateShtetiDto;
import models.DTO_data_transfer_object.UpdateShtetiDto;
import models.Shteti;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShtetiRepository extends BaseRepository<Shteti,CreateShtetiDto,UpdateShtetiDto> {

    public ShtetiRepository(){ super("shtetet"); }

    @Override
    public Shteti fromResultSet(ResultSet res)throws SQLException {
        return Shteti.getInstance(res);
    }

    @Override
    public Shteti create(CreateShtetiDto shtetiDto){
        String query= """
                INSERT INTO
                shtetet(shteti,country )
                VALUES (?,?)
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1,shtetiDto.getShteti());
            ppstm.setString(1, shtetiDto.getShtetiEnglish());
            ppstm.execute();
            ResultSet res =ppstm.getGeneratedKeys();
            if(res.next()){
                int id= res.getInt(1);
                return this.getById(id);
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Shteti update(UpdateShtetiDto shtetiDto){
        String query= """
                UPDATE shtetet
                SET shteti=?,country=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1,shtetiDto.getShteti());
            ppstm.setString(2,shtetiDto.getShtetiEnglish());
            ppstm.setInt(3,shtetiDto.getId());
            int updateRecord=ppstm.executeUpdate();
            if(updateRecord == 1){
                return this.getById(shtetiDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
