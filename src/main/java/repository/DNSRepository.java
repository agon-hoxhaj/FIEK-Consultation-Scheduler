package repository;

import models.DrejtimiNiveliSemestri;
import models.DTO_data_transfer_object.CreateDNSDto;
import models.DTO_data_transfer_object.UpdateDNSDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DNSRepository extends BaseRepository<DrejtimiNiveliSemestri, CreateDNSDto, UpdateDNSDto> {

    public DNSRepository() {
        super("drejtimet_nivelet_semestrat");
    }

    @Override
    public DrejtimiNiveliSemestri fromResultSet(ResultSet res) throws SQLException {
        return DrejtimiNiveliSemestri.getInstance(res);
    }

    @Override
    public DrejtimiNiveliSemestri create(CreateDNSDto dto) {
        String query = """
                INSERT INTO drejtimi_niveli_semestri(id_drejtimi, id_niveli, semestri)
                VALUES (?, ?, ?)
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getIdDrejtimi());
            ps.setInt(2, dto.getIdNiveli());
            ps.setInt(3, dto.getSemestri());
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
    public DrejtimiNiveliSemestri update(UpdateDNSDto dto) {
        String query = """
                UPDATE drejtimi_niveli_semestri
                SET id_drejtimi = ?, id_niveli = ?, semestri = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, dto.getIdDrejtimi());
            ps.setInt(2, dto.getIdNiveli());
            ps.setInt(3, dto.getSemestri());
            ps.setInt(4, dto.getId());

            int updatedRows = ps.executeUpdate();
            if (updatedRows == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existsByCombination(int idDrejtimi, int idNiveli, int semestri) {
        String query = """
                SELECT COUNT(*) 
                FROM drejtimi_niveli_semestri
                WHERE id_drejtimi = ? AND id_niveli = ? AND semestri = ?
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, idDrejtimi);
            ps.setInt(2, idNiveli);
            ps.setInt(3, semestri);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int findIdByParams(int idDrejtimi, int idNiveli, int semestri) {
        String query = "SELECT id FROM drejtimet_nivelet_semestrat WHERE id_drejtimi = ? AND id_niveli = ? AND semestri = ?";
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1, idDrejtimi);
            ppstm.setInt(2, idNiveli);
            ppstm.setInt(3, semestri);

            ResultSet res = ppstm.executeQuery();
            if (res.next()) {
                return res.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return -1;
    }



}


