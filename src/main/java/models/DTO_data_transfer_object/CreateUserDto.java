package models.DTO_data_transfer_object;

import enums.Role;

public class CreateUserDto {
    private int id;
    private String username;
    private int idPassword;
    private Role roli;

    public CreateUserDto(String username, int idPassword, Role roli){
        this.username = username;
        this.idPassword = idPassword;
        this.roli = roli;
}

    public int getId(){
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getPassword() {
        return this.idPassword;
    }

    public void setPassword(int idPassword){
        this.idPassword = idPassword;
    }

    public Role getRoli() {
        return this.roli;
    }

    public void setRoli(Role roli){
        this.roli = roli;
    }

}