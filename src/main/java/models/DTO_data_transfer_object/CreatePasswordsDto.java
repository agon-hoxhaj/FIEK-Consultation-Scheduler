package models.DTO_data_transfer_object;

public class CreatePasswordsDto {
    private int id;
    private String passwordHash;
    private String salt;
    private int iterations;
    private String hashFunction;

    public CreatePasswordsDto(String passwordHash, String salt, int iterations, String hashFunction){
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.iterations = iterations;
        this.hashFunction = hashFunction;
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

    public int getIterations(){
        return this.iterations;
    }

    public void setIterations(int iterations){
        this.iterations = iterations;
    }

    public String getHashFunction(){
        return this.hashFunction;
    }

    public void setHashFunction(String hashFunction){
        this.hashFunction = hashFunction;
    }
}
