package repository;

import models.DTO_data_transfer_object.CreateDNSLDto;
import models.DTO_data_transfer_object.UpdateDNSLDto;
import models.DrejtimiNiveliSemestriLenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DNSLRepository extends BaseRepository<DrejtimiNiveliSemestriLenda, CreateDNSLDto, UpdateDNSLDto> {

    public DNSLRepository() {
        super("drejtimet_nivelet_semestrat_lendet");
    }

    @Override
    public DrejtimiNiveliSemestriLenda fromResultSet(ResultSet res) throws SQLException {
        return DrejtimiNiveliSemestriLenda.getInstance(res);
    }

    @Override
    public DrejtimiNiveliSemestriLenda create(CreateDNSLDto dto) {
        String query = """
                INSERT INTO drejtimet_nivelet_semestrat_lendet(id_drejtimi_niveli_semestri, id_profesori_lenda)
                VALUES(?, ?)
                """;
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setInt(1, dto.getIdDrejtimiNiveliSemestri());
            ppstm.setInt(2, dto.getIdProfesoriLenda());
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

    @Override
    public DrejtimiNiveliSemestriLenda update(UpdateDNSLDto dto) {
        String query = """
                UPDATE drejtimet_nivelet_semestrat_lendet
                SET id_drejtimi_niveli_semestri = ?, id_profesori_lenda = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1, dto.getIdDrejtimiNiveliSemestri());
            ppstm.setInt(2, dto.getIdProfesoriLenda());
            ppstm.setInt(3, dto.getId());
            int updatedRecords = ppstm.executeUpdate();
            if (updatedRecords == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdByIdDNSIdPL(int idDNS ,int idPL){
        String query = """
                SELECT id FROM drejtimet_nivelet_semestrat_lendet
                WHERE id_drejtimi_niveli_semestri = ? AND id_profesori_lenda = ?
                """;
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1,idDNS);
            ppstm.setInt(2,idPL);
            ResultSet resultSet = ppstm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public ArrayList<DrejtimiNiveliSemestriLenda> getDNSL(ArrayList<Integer> idProfesoriLendet){
        return getAllByIds(idProfesoriLendet,"id_profesori_lenda");
    }
}
