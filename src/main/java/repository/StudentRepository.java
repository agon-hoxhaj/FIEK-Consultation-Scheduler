package repository;

import models.DTO_data_transfer_object.CreateStudentDto;
import models.DTO_data_transfer_object.UpdateStudentDto;
import models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends BaseRepository<Student, CreateStudentDto, UpdateStudentDto>{
    public StudentRepository(){
        super("studentat");
    }

    public Student fromResultSet(ResultSet res) throws SQLException {
        return Student.getInstance(res);
    }

    public List<Student> getStudentetByProfesorId(int profesorId) {
        List<Student> studentet = new ArrayList<>();
        String sql = """
        SELECT DISTINCT s.*
        FROM studentat s
        JOIN drejtimet_nivelet_semestrat dnl ON s.studimi = dnl.id
        JOIN drejtimet_nivelet_semestrat_lendet dnll ON dnl.id = dnll.id_drejtimi_niveli_semestri
        JOIN profesoret_lendet pl ON dnll.id_profesori_lenda = pl.id
        WHERE pl.id_profesori = ?
    """;

        try {
             PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, profesorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentet.add(Student.getInstance(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentet;
    }

    public Student create(CreateStudentDto studentDto){
        String query = """
                INSERT INTO
                studentat(numri_personal, gjinia, emri, mbiemri, email,numri_telefonit,prejardhja,studimi,perdoruesi, student_aktiv)
                VALUES(?,?,?,?,?,?,?,?,?,?)
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1, studentDto.getNumriPersonal());
            ppstm.setObject(2, studentDto.getGender(), Types.OTHER);
            ppstm.setString(3, studentDto.getEmri());
            ppstm.setString(4, studentDto.getMbiemri());
            ppstm.setString(5, studentDto.getEmail());
            ppstm.setString(6, studentDto.getNumriTelefonit());
            ppstm.setInt(7, studentDto.getPrejardhja());
            ppstm.setInt(8, studentDto.getStudimi());
            ppstm.setInt(9, studentDto.getUserid());
            ppstm.setBoolean(10, studentDto.isStudent_aktiv());
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

    public Student update(UpdateStudentDto studentDto){
        String query = """
                UPDATE studentat
                SET numri_telefonit=?, studimi=?, student_aktiv=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1, studentDto.getNumriTelefonit());
            ppstm.setInt(2, studentDto.getStudimi());
            ppstm.setBoolean(3, studentDto.isStudent_aktiv());
            int updatedRecords = ppstm.executeUpdate();
            if(updatedRecords == 1){
                return this.getById(studentDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public int merrNumrinEStudentPerDrejtim(int idDrejtimi) {
        int numriStudentave = 0;
        String sql = """
            SELECT COUNT(s.id) AS numri_studentave
            FROM studentat s
            JOIN drejtimet_nivelet_semestrat dns ON s.studimi = dns.id
            WHERE dns.id_drejtimi = ?
            AND s.student_aktiv = TRUE
            """;

            try {
                PreparedStatement stmt = this.connection.prepareStatement(sql);
                stmt.setInt(1, idDrejtimi);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        numriStudentave = rs.getInt("numri_studentave");
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numriStudentave;
    }
    public int merrNumrinTotalTeStudenteve() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS totali FROM studentat WHERE student_aktiv = TRUE";

        try {
             PreparedStatement stmt = this.connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("totali");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

}
