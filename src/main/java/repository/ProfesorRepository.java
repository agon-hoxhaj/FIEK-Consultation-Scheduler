package repository;

import models.DTO_data_transfer_object.CreateProfesorDto;
import models.DTO_data_transfer_object.UpdateProfesorDto;
import models.Profesor;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProfesorRepository extends BaseRepository<Profesor, CreateProfesorDto, UpdateProfesorDto>{
    public ProfesorRepository(){
        super("profesoret");
    }

    public Profesor fromResultSet(ResultSet res) throws SQLException {
        return Profesor.getInstance(res);
    }

    public Profesor getByUserId(int userId) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM profesoret WHERE perdoruesi = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Profesor.getInstance(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Profesor create(CreateProfesorDto profesorDto){
        String query = """
                INSERT INTO
                profesoret(numri_personal, gjinia, emri, mbiemri, email,numri_telefonit, prejardhja ,kabineti_personal, perdoruesi, profesor_aktiv)
                VALUES(?,?,?,?,?,?,?,?,?,?)
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1, profesorDto.getNumriPersonal());
            ppstm.setObject(2, profesorDto.getGender(), Types.OTHER);
            ppstm.setString(3, profesorDto.getEmri());
            ppstm.setString(4, profesorDto.getMbiemri());
            ppstm.setString(5, profesorDto.getEmail());
            ppstm.setString(6, profesorDto.getNumriTelefonit());
            ppstm.setInt(7, profesorDto.getPrejardhja());
            ppstm.setInt(8, profesorDto.getKabinetiPersonal());
            ppstm.setInt(9, profesorDto.getUserid());
            ppstm.setBoolean(10, profesorDto.isProfesor_aktiv());
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

    public Profesor update(UpdateProfesorDto profesorDto){
        String query = """
                UPDATE profesoret
                SET numri_telefonit=?, profesor_aktiv=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1, profesorDto.getNumriTelefonit());
            ppstm.setBoolean(2, profesorDto.isProfesor_aktiv());
            int updatedRecords = ppstm.executeUpdate();
            if(updatedRecords == 1){
                return this.getById(profesorDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getProfIdByEmriMbiemri(String emri, String mbiemri){
        String query = """
                SELECT id 
                FROM profesoret
                WHERE emri=? AND mbiemri=?
                """;

        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1,emri);
            ppstm.setString(2,mbiemri);
            ResultSet res = ppstm.executeQuery();
            if(res.next()){
                return res.getInt("id");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public Map<String, Integer> merrNumrinEProfPerDrejtim() {
        Map<String, Integer> statistika = new HashMap<>();
        String sql = """
                    SELECT d.drejtimi, COUNT(DISTINCT p.id) AS numer_profesoresh
                    FROM profesoret p
                    JOIN profesoret_lendet pl ON pl.id_profesori = p.id
                    JOIN drejtimet_nivelet_semestrat_lendet dnsl ON dnsl.id_profesori_lenda = pl.id
                    JOIN drejtimet_nivelet_semestrat dns ON dns.id = dnsl.id_drejtimi_niveli_semestri
                    JOIN drejtimet d ON d.id = dns.id_drejtimi
                    GROUP BY d.drejtimi
                    """;

        try {
             PreparedStatement stmt = this.connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                statistika.put(rs.getString("drejtimi"), rs.getInt("numer_profesoresh"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistika;
    }
}
