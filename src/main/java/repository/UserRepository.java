package repository;

import enums.Role;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdateUserDto;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository extends BaseRepository<User, CreateUserDto, UpdateUserDto>{

    public UserRepository(){
        super("perdoruesit");
    }

    public User fromResultSet(ResultSet res) throws SQLException {
        return User.getInstance(res);
    }

    public User create(CreateUserDto userDto){
        String query = """
                INSERT INTO
                perdoruesit(username, id_passw, roli)
                VALUES(?,?,?)
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ppstm.setString(1, userDto.getUsername());
            ppstm.setInt(2, userDto.getPassword());
            ppstm.setObject(3, userDto.getRoli(), java.sql.Types.OTHER);
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

    public User update(UpdateUserDto userDto){
        String query = """
                UPDATE perdoruesit
                SET username =?, id_passw =?, roli=?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1,userDto.getUsername());
            ppstm.setInt(2,userDto.getPassword());
            ppstm.setObject(3, userDto.getRole(),java.sql.Types.OTHER);
            int updatedRecords = ppstm.executeUpdate();
            if(updatedRecords == 1){
                return this.getById(userDto.getId());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

        public int getUserIdByUsername(String username){
        String query = """
                SELECT id FROM perdoruesit
                WHERE username=?
                """;

        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1,username);
            ResultSet res = ppstm.executeQuery();
            if(res.next()){
                return res.getInt("id");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
        }

        public Role getUserRoleByUserId(int userId){
        String query= """
                SELECT roli
                FROM perdoruesit
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1,userId);
            ResultSet res = ppstm.executeQuery();
            if(res.next()) {
                String roleName = res.getString("roli");
                return Role.valueOf(roleName);
            }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        }

        public boolean doesUsernameExist(String username){
        String query = """
                SELECT COUNT(*) AS count
                FROM perdoruesit
                WHERE username=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1,username);
            ResultSet res = ppstm.executeQuery();
            if(res.next()){
                int count = res.getInt("count");
                return count>0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
        }

        public boolean isUsernameAndPersonalNumMatching(String username, String personalNumber){
        String query = """
                SELECT 1
                FROM perdoruesit p
                JOIN stafi_administrativ sa ON sa.perdoruesi = p.id
                WHERE p.username=? AND sa.numri_personal=?
                UNION
                SELECT 1
                FROM perdoruesit p
                JOIN studentat s ON s.perdoruesi = p.id
                WHERE p.username=? AND s.numri_personal=?
                UNION
                SELECT 1
                FROM perdoruesit p
                JOIN profesoret prof ON prof.perdoruesi = p.id
                WHERE p.username=? AND prof.numri_personal=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setString(1, username);
            ppstm.setString(2, personalNumber);
            ppstm.setString(3, username);
            ppstm.setString(4, personalNumber);
            ppstm.setString(5, username);
            ppstm.setString(6,personalNumber);
            ResultSet res = ppstm.executeQuery();
            if(res.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
        }

    public User updatePassword(int id,UpdateUserDto userDto){
        String query = """
                UPDATE perdoruesit
                SET id_passw =?
                WHERE id=?
                """;
        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1,userDto.getPassword());
            ppstm.setInt(2,id);
            int updatedRecords = ppstm.executeUpdate();
            if(updatedRecords == 1){
                return this.getById(id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}

