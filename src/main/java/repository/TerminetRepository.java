package repository;

import models.DTO_data_transfer_object.CreateTerminetDto;
import models.DTO_data_transfer_object.UpdateTerminetDto;
import models.Terminet;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TerminetRepository extends BaseRepository<Terminet, CreateTerminetDto, UpdateTerminetDto> {

    public TerminetRepository() {
        super("terminet");
    }

    @Override
    public Terminet fromResultSet(ResultSet resultSet) throws SQLException {
        return Terminet.getInstance(resultSet);
    }

    @Override
    public Terminet create(CreateTerminetDto dto) {
        String query = """
                INSERT INTO terminet(id_orari, id_studenti, intervali_kohor, arsyeja, rezervuar)
                VALUES (?, ?, ?, ?, ?)
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getIdOrari());
            ps.setInt(2, dto.getIdStudenti());
            ps.setTimestamp(3, dto.getIntervaliKohor());
            ps.setString(4, dto.getArsyeja());
            ps.setBoolean(5, dto.isRezervuar());

            ps.execute();
            ResultSet res = ps.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Terminet update(UpdateTerminetDto dto) {
        String query = """
                UPDATE terminet
                SET id_orari = ?, id_studenti = ?, intervali_kohor = ?, arsyeja = ?, rezervuar = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, dto.getIdOrari());
            ps.setInt(2, dto.getIdStudenti());
            ps.setTimestamp(3, dto.getIntervaliKohor());
            ps.setString(4, dto.getArsyeja());
            ps.setBoolean(5, dto.isRezervuar());
            ps.setInt(6, dto.getId());

            int updated = ps.executeUpdate();
            if (updated == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Terminet> getAllTerminetByProfId(int idProfesori){
        String query= """
                SELECT
                    t.id,
                    t.id_orari,
                    t.id_studenti,
                    t.intervali_kohor,
                    t.arsyeja,
                    t.rezervuar
                FROM terminet t
                JOIN oraret_data o ON t.id_orari = o.id
                JOIN oraret ora ON o.id_orari = ora.id
                JOIN drejtimet_nivelet_semestrat_lendet d ON ora.id_drejtimi_niveli_semestri_lenda = d.id
                JOIN profesoret_lendet pl ON d.id_profesori_lenda = pl.id
                WHERE pl.id_profesori = ?
                  AND t.rezervuar = TRUE;
                """;
        ArrayList<Terminet> terminet = new ArrayList<>();
        try{
            PreparedStatement ppstm= this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setInt(1, idProfesori);
            ResultSet res = ppstm.executeQuery();
            while(res.next()){
                Terminet termini = Terminet.getInstance(res);
                terminet.add(termini);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return terminet;
    }

    public boolean hasStudentReservedAppointment(int studentId, LocalDateTime appointmentDateTime) {
        String sql = "SELECT COUNT(*) FROM terminet WHERE id_studenti = ? AND intervali_kohor = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.setTimestamp(2, Timestamp.valueOf(appointmentDateTime));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Terminet> getReservedValidAppointmentsByStudent(int studentId) {
        List<Terminet> reservedAppointments = new ArrayList<>();
        String sql = "SELECT * FROM terminet WHERE id_studenti = ? AND intervali_kohor >= ? AND rezervuar = true ORDER BY intervali_kohor";

        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Terminet t = Terminet.getInstance(rs);
                reservedAppointments.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservedAppointments;
    }

    public void removeInvalidTermin(){
        String sql = "SELECT deactivate_expired_terminet()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


