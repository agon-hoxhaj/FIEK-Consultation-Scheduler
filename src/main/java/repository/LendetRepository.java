package repository;

import models.DTO_data_transfer_object.CreateLendaDto;
import models.DTO_data_transfer_object.UpdateLendaDto;
import models.Lenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LendetRepository extends BaseRepository<Lenda, CreateLendaDto, UpdateLendaDto> {

    public LendetRepository(){ super("lendet"); }

    @Override
    public Lenda fromResultSet(ResultSet res)throws SQLException {
        return Lenda.getInstance(res);
    }

    @Override
    public Lenda create(CreateLendaDto lendaDto){
        String query= """
                INSERT INTO
                lendet(emri,emri_english )
                VALUES (?,?)
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1,lendaDto.getEmri());
            ppstm.setString(1, lendaDto.getEmriEnglish());
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
    public Lenda update(UpdateLendaDto lendaDto){
        String query= """
                UPDATE lendet
                SET emri=?,emri_english=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1,lendaDto.getEmri());
            ppstm.setString(2,lendaDto.getEmriEnglish());
            ppstm.setInt(3,lendaDto.getId());
            int updateRecord=ppstm.executeUpdate();
            if(updateRecord == 1){
                return this.getById(lendaDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
