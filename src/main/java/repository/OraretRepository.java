package repository;

import models.DTO_data_transfer_object.CreateOrariDto;
import models.DTO_data_transfer_object.UpdateOrariDto;
import models.Orari;

import java.sql.*;
import java.util.ArrayList;

public class OraretRepository extends BaseRepository<Orari, CreateOrariDto, UpdateOrariDto>{

    public OraretRepository(){super("oraret");}


    @Override
    public Orari fromResultSet(ResultSet res)throws SQLException {
        return Orari.getInstance(res);
    }

    @Override
    public Orari create(CreateOrariDto orariDto){
        String query = """
                INSERT INTO
                oraret( id_drejtimi_niveli_semestri_lenda ,dita,dita_english,ora_fillimit, ora_mbarimit)
                VALUES (?,?,?,?,?)
                """;
        try{
            PreparedStatement ppstm=this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setInt(1, orariDto.getIdDrejtimiNiveliSemestriLenda());
            ppstm.setObject(2, orariDto.getDita(), Types.OTHER);
            ppstm.setObject(3, orariDto.getDitaEnglish(), Types.OTHER);
            ppstm.setTime(4, Time.valueOf(orariDto.getOraFillimit()));
            ppstm.setTime(5, Time.valueOf(orariDto.getOraMbarimit()));

            ppstm.execute();
            ResultSet res =ppstm.getGeneratedKeys();
            if(res.next()){
                int id= res.getInt(1);
                return this.getById(id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Orari update(UpdateOrariDto orariDto){
        String query= """
                UPDATE oraret
                Set statusi_aktiv=?
                WHERE id=?
                """;
        try{

            PreparedStatement ppstm=this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ppstm.setBoolean(1,orariDto.isOrariValid());
            ppstm.setInt(2, orariDto.getId());

            int updateRecord=ppstm.executeUpdate();
            if(updateRecord == 1){
                return this.getById(orariDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Orari> getActiveOraret(int idDNSL){
        ArrayList<Orari> result =getAll("WHERE id_drejtimi_niveli_semestri_lenda = "+idDNSL+" AND statusi_aktiv = true");
        return result;
    }
    public int count(int idDNSL){
        return count("WHERE id_drejtimi_niveli_semestri_lenda = "+idDNSL+" AND statusi_aktiv = true");
    }

    public int countReservedTerminet(int orariDataId) {
        String sql = """
        SELECT COUNT(*) AS total
        FROM terminet
        WHERE id_orari = ?
          AND rezervuar = TRUE
    """;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, orariDataId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
