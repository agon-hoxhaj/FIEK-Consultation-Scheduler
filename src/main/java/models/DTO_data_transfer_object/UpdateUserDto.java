package models.DTO_data_transfer_object;

import enums.Role;

public class UpdateUserDto {
    private int id;
    private String username;
    private int idPassword;
    private Role roli;

    public UpdateUserDto(String username, int idPassword, Role roli){
        this.username = username;
        this.idPassword = idPassword;
        this.roli = roli;
    }

    public UpdateUserDto(int idPassword){
        this.idPassword=idPassword;
    }

    public int getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getPassword(){
        return this.idPassword;
    }

    public void setPassword(int idPassword){
        this.idPassword = idPassword;
    }

    public Role getRole(){
        return this.roli;
    }

    public void setRole(Role roli){
        this.roli = roli;
    }
}
