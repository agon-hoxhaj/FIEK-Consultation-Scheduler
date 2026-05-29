package models.DTO_data_transfer_object;

public class UpdatePasswordsDto {
    private int id;
    private String passwordHash;
    private String salt;

    public UpdatePasswordsDto(int id,String passwordHash, String salt){
        this.id = id;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public int getId(){
        return this.id;
    }

    public String getPasswordHash(){
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    public String getSalt(){
        return this.salt;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

}
