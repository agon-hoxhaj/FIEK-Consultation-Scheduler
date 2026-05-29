package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Passwords {
    private int id;
    private String passwordHash;
    private String salt;
    private int iterations;
    private String hashFunction;

    private Passwords(int id, String passwordHash, String salt, int iterations, String hashFunction){
        this.id = id;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.iterations = iterations;
        this.hashFunction = hashFunction;
    }

    public static Passwords getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String passwordHash = resultSet.getString("password_hash");
        String salt = resultSet.getString("salt");
        int iterations = resultSet.getInt("iterations");
        String hashFunction = resultSet.getString("hash_function");
        return new Passwords(id, passwordHash, salt, iterations, hashFunction);
    }

    public int getId(){
        return this.id;
    }

    public String getPasswordHash(){
        return this.passwordHash;
    }

    public String getSalt(){
        return this.salt;
    }

    public int getIterations(){
        return this.iterations;
    }

    public String getHashFunction(){
        return this.hashFunction;
    }

}
