package repository;

import models.DTO_data_transfer_object.CreateFeedbackDto;
import models.DTO_data_transfer_object.UpdateFeedbackDto;
import models.Feedback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeedbackRepository extends BaseRepository<Feedback, CreateFeedbackDto, UpdateFeedbackDto> {

    public FeedbackRepository() {
        super("feedback");
    }

    @Override
    public Feedback fromResultSet(ResultSet res) throws SQLException {
        return Feedback.getInstance(res);
    }

    @Override
    public Feedback create(CreateFeedbackDto dto) {
        String query = """
                INSERT INTO feedback(id_profesori, id_studenti, vleresimi, komenti)
                VALUES (?, ?, ?, ?)
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getIdProfesori());
            ps.setInt(2, dto.getIdStudenti());
            ps.setInt(3, dto.getVleresimi());
            ps.setString(4, dto.getKomenti());
            ps.execute();

            ResultSet res = ps.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(3);
                return this.getById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Feedback update(UpdateFeedbackDto dto) {
        String query = """
                UPDATE feedback
                SET vleresimi = ?, komenti = ?, data = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, dto.getVleresimi());
            ps.setString(2, dto.getKomenti());
            ps.setTimestamp(3, dto.getData());
            ps.setInt(4, dto.getId());

            int updated = ps.executeUpdate();
            if (updated == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
