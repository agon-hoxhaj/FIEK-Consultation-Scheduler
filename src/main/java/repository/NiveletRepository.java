package repository;

import models.DTO_data_transfer_object.CreateNiveletDto;
import models.DTO_data_transfer_object.UpdateNiveletDto;
import models.Nivelet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NiveletRepository extends BaseRepository<Nivelet, CreateNiveletDto, UpdateNiveletDto> {

    public NiveletRepository() {
        super("nivelet");
    }

    @Override
    public Nivelet fromResultSet(ResultSet res) throws SQLException {
        return Nivelet.getInstance(res);
    }

    @Override
    public Nivelet create(CreateNiveletDto dto) {
        String query = """
                INSERT INTO nivelet(niveli, niveli_english)
                VALUES (?, ?)
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getNiveli());
            ps.setString(2, dto.getNiveliEnglish());
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
    public Nivelet update(UpdateNiveletDto dto) {
        String query = """
                UPDATE nivelet
                SET niveli = ?, niveli_english = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, dto.getNiveli());
            ps.setString(2, dto.getNiveliEnglish());
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

    public boolean existsByNiveli(String niveli) {
        String query = "SELECT COUNT(*) FROM nivelet WHERE niveli = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, niveli);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByNiveliEnglish(String niveliEnglish) {
        String query = "SELECT COUNT(*) FROM nivelet WHERE niveli_english = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, niveliEnglish);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}