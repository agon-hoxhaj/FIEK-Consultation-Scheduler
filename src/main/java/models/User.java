package models;

import enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String username;
    private int idPassword;
    private Role roli;

    private User(int id, String username, int idPassword, Role roli){
        this.id = id;
        this.username = username;
        this.idPassword = idPassword;
        this.roli = roli;
    }

    public static User getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        int idPassword = resultSet.getInt("id_passw");
        String roliStr = resultSet.getString("roli");

        Role roli = (roliStr != null) ? Role.valueOf(roliStr) : null;

        return new User(id, username, idPassword, roli);
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPassword() {
        return this.idPassword;
    }

    public Role getRole() {
        return this.roli;
    }


}

