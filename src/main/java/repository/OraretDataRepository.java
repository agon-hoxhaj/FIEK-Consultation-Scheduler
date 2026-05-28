package repository;

import models.DTO_data_transfer_object.CreateOrariDataDto;
import models.DTO_data_transfer_object.UpdateOrariDataDto;
import models.OrariData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OraretDataRepository extends BaseRepository<OrariData,CreateOrariDataDto, UpdateOrariDataDto>{

    public OraretDataRepository(){ super("oraret_data"); }

    @Override
    public OrariData fromResultSet(ResultSet res) throws SQLException {
        return OrariData.getInstance(res);
    }

    @Override
    public OrariData create(CreateOrariDataDto orariDataDto){
        String query = """
                INSERT INTO  
                orari_data(id_orari, data, orari_valid)
                VALUES (?,?,?)
                """;
        try{
            PreparedStatement ppstm= this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setInt(1, orariDataDto.getIdOrari());
            ppstm.setDate(2, orariDataDto.getData());
            ppstm.setBoolean(3, orariDataDto.isOrariValid());
            ppstm.execute();
            ResultSet res =ppstm.getGeneratedKeys();
            if(res.next()){
                int id= res.getInt(1);
                return this.getById(id);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrariData update(UpdateOrariDataDto orariDataDto){
        String query= """
                UPDATE orari_data
                SET orari_valid=?
                WHERE id=?
                """;
        try{

            PreparedStatement ppstm=this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ppstm.setBoolean(1,orariDataDto.isOrariValid());
            ppstm.setInt(2, orariDataDto.getId());
            int updateRecord=ppstm.executeUpdate();
            if(updateRecord == 1){
                return this.getById(orariDataDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
    public void generateValidOrareData(){
        String query= """
                SELECT generate_oraret_data();
                SELECT invalidate_past_oraret_data();
                """;
        // Keto funksione gjenden ne databaze
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public List<OrariData> getValidOrariDataByOrarId(int orariId) {
        List<OrariData> dataList = new ArrayList<>();

        String sql = """
        SELECT MIN(id) as id, id_orari, data, orari_valid
        FROM oraret_data
        WHERE id_orari = ?
          AND orari_valid = TRUE
          AND data >= CURRENT_DATE
        GROUP BY id_orari, data, orari_valid
        ORDER BY data ASC
    """;

        try {
             PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, orariId);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                OrariData data = OrariData.getInstance(res);
                dataList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
