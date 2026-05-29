package repository;

import models.DTO_data_transfer_object.CreateStafiAdministrativDto;
import models.DTO_data_transfer_object.UpdateStafiAdministrativDto;
import models.StafiAdministrativ;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StafiAdministrativRepository extends BaseRepository<StafiAdministrativ, CreateStafiAdministrativDto, UpdateStafiAdministrativDto> {

    public StafiAdministrativRepository() {
        super("stafi_administrativ");
    }

    public StafiAdministrativ fromResultSet(ResultSet res) throws SQLException{
        return StafiAdministrativ.getInstance(res);
    }

    public StafiAdministrativ create( CreateStafiAdministrativDto stafiDto){
        String query = """
                INSERT INTO 
                stafi_administrativ( numri_personal, gjinia, emri, mbiemri, email, numri_telefonit, prejardhja, perdoruesi, titulli)
                VALUES(?,?,?,?,?,?,?,?,?)
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1, stafiDto.getNumriPersonal());
            ppstm.setObject(2, stafiDto.getGender(),java.sql.Types.OTHER);
            ppstm.setString(3, stafiDto.getEmri());
            ppstm.setString(4, stafiDto.getMbiemri());
            ppstm.setString(5, stafiDto.getEmail());
            ppstm.setString(6, stafiDto.getNumriTelefonit());
            ppstm.setInt(7, stafiDto.getPrejardhja());
            ppstm.setInt(8, stafiDto.getUserId());
            ppstm.setString(9, stafiDto.getTitulli());
            ppstm.execute();
            ResultSet res = ppstm.getGeneratedKeys();
            if(res.next()){
                int id = res.getInt(1);
                return this.getById(id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public StafiAdministrativ update(UpdateStafiAdministrativDto stafiDto){
        String query = """
                UPDATE stafi_administrativ
                SET numri_telefonit=?, titulli=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1, stafiDto.getNumriTelefonit());
            ppstm.setString(2, stafiDto.getTitulli());
            int updatedRecords = ppstm.executeUpdate();
            if(updatedRecords == 1){
                return this.getById(stafiDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
