package repository;

import models.DTO_data_transfer_object.CreateProfesorLendaDto;
import models.DTO_data_transfer_object.UpdateProfesorLendaDto;
import models.ProfesorLenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProfesoretLendetRepository extends BaseRepository<ProfesorLenda, CreateProfesorLendaDto, UpdateProfesorLendaDto> {

    public ProfesoretLendetRepository() {
        super("profesoret_lendet");
    }

    @Override
    public ProfesorLenda fromResultSet(ResultSet res) throws SQLException {
        return ProfesorLenda.getInstance(res);
    }

    public ProfesorLenda create(CreateProfesorLendaDto profesorLendaDto) {
        String query = """
                INSERT INTO
                profesoret_lendet(id_profesori, id_lenda)
                VALUES(?, ?)
                """;
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setInt(1, profesorLendaDto.getIdProfesor());
            ppstm.setInt(2, profesorLendaDto.getIdLenda());
            ppstm.execute();
            ResultSet res = ppstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getIdsById(int idProfesori){
        return getIdsById(idProfesori,"id_profesori");
    }

    public ProfesorLenda update(UpdateProfesorLendaDto profesorLendaDto) {
        String query = """
                UPDATE profesoret_lendet
                SET id_profesori = ?, id_lenda = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1, profesorLendaDto.getIdProfesor());
            ppstm.setInt(2, profesorLendaDto.getIdLenda());
            ppstm.setInt(3, profesorLendaDto.getId());
            int updatedRecords = ppstm.executeUpdate();
            if (updatedRecords == 1) {
                return this.getById(profesorLendaDto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
