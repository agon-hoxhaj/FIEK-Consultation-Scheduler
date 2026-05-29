package repository;

import models.DTO_data_transfer_object.CreateVleresimiDto;
import models.DTO_data_transfer_object.UpdateVleresimiDto;
import models.Vleresimi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VleresimiRepository extends BaseRepository<Vleresimi, CreateVleresimiDto, UpdateVleresimiDto> {


    public VleresimiRepository(){ super("vleresimi"); }

    @Override
    public Vleresimi fromResultSet(ResultSet res) throws SQLException {
        return Vleresimi.getInstance(res);
    }

    @Override
    public Vleresimi create(CreateVleresimiDto vleresimiDto){
        String query= """
                INSERT INTO
                vleresimi(emertimi)
                VALUES (?)
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1,vleresimiDto.getEmertimi());
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
    public Vleresimi update(UpdateVleresimiDto vleresimiDto){
        String query= """
                UPDATE vleresimi
                SET emertimi=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1,vleresimiDto.getEmertimi());
            int updateRecord=ppstm.executeUpdate();
            if(updateRecord == 1){
                return this.getById(vleresimiDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
