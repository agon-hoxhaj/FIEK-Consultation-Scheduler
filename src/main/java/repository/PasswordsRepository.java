package repository;

import models.DTO_data_transfer_object.CreatePasswordsDto;
import models.DTO_data_transfer_object.UpdatePasswordsDto;
import models.Passwords;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PasswordsRepository extends BaseRepository<Passwords, CreatePasswordsDto, UpdatePasswordsDto>{


    public PasswordsRepository(){
        super("passwords");
    }

    public Passwords fromResultSet(ResultSet res) throws SQLException {
        return Passwords.getInstance(res);
    }

    public Passwords create(CreatePasswordsDto dto){
        String query = """
                INSERT INTO 
                passwords(password_hash, salt, iterations, hash_function)
                VALUES(?,?,?,?)
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1, dto.getPasswordHash());
            ppstm.setString(2, dto.getSalt());
            ppstm.setInt(3, dto.getIterations());
            ppstm.setString(4, dto.getHashFunction());
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


    public Passwords update(UpdatePasswordsDto dto){
        String query = """
                UPDATE passwords
                SET password_hash=?, salt=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1, dto.getPasswordHash());
            ppstm.setString(2, dto.getSalt());
            ppstm.setInt(3, dto.getId());
            int updatedRecords = ppstm.executeUpdate();
            if(updatedRecords ==1 ){
                return this.getById(dto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


        public Passwords getPasswordById(int passwordId){
        String query= """
                SELECT *
                FROM passwords
                WHERE id=?
                """;
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1, passwordId);
            ResultSet res = ppstm.executeQuery();
            if (res.next()) {
                return Passwords.getInstance(res);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
        }


    public boolean updateUserId(int passwordId, int userId) {
        String query = "UPDATE perdoruesit SET id_passw = ? WHERE id = ?";
        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1, passwordId);
            ppstm.setInt(2, userId);
            return ppstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user_id for password", e);
        }
    }

    public int getPasswordIdByUsername(String username){
        String query = """
                SELECT id_passw
                FROM perdoruesit
                WHERE username = ?
                """;

        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1,username);
            ResultSet res = ppstm.executeQuery();
            if(res.next()){
                return res.getInt("id_passw");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

}

