package repository;

import models.DTO_data_transfer_object.CreateDrejtimetDto;
import models.DTO_data_transfer_object.UpdateDrejtimetDto;
import models.Drejtimet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DrejtimetRepository extends BaseRepository<Drejtimet, CreateDrejtimetDto, UpdateDrejtimetDto> {

    public DrejtimetRepository() {
        super("drejtimet");
    }

    @Override
    public Drejtimet fromResultSet(ResultSet res) throws SQLException {
        return Drejtimet.getInstance(res);
    }

    @Override
    public Drejtimet create(CreateDrejtimetDto dto) {
        String query = """
                INSERT INTO drejtimet(drejtimi, drejtimi_english)
                VALUES (?, ?)
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getDrejtimi());
            ps.setString(2, dto.getDrejtimiEnglish());
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
    public Drejtimet update(UpdateDrejtimetDto dto) {
        String query = """
                UPDATE drejtimet
                SET drejtimi = ?, drejtimi_english = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, dto.getDrejtimi());
            ps.setString(2, dto.getDrejtimiEnglish());
            ps.setInt(3, dto.getId());
            int updatedRows = ps.executeUpdate();
            if (updatedRows == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

