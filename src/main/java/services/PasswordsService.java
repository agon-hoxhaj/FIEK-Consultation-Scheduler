package services;

import models.DTO_data_transfer_object.CreatePasswordsDto;
import models.DTO_data_transfer_object.UpdatePasswordsDto;
import models.Passwords;
import repository.PasswordsRepository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordsService extends BaseService<Passwords, CreatePasswordsDto, UpdatePasswordsDto, PasswordsRepository> {

    private PasswordsRepository passwordsRepository;
    private final int saltLength = 6;

    public PasswordsService(){
        super("passwords", new PasswordsRepository());
        this.passwordsRepository = this.modelRepository;
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        StringBuilder salt = new StringBuilder(this.saltLength);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < this.saltLength; i++) {
            int index = random.nextInt(chars.length());
            salt.append(chars.charAt(index));
        }

        return salt.toString();
    }

    public String hashPassword(String password, String salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt.getBytes(StandardCharsets.UTF_8),
                iterations,
                128
        );
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashedBytes = keyFactory.generateSecret(spec).getEncoded();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Override
    public Passwords create(CreatePasswordsDto dto){
        try{
            if (dto == null || dto.getPasswordHash() == null) {
                throw new IllegalArgumentException("CreatePasswordsDto invalide");
            }
            Passwords pass = this.modelRepository.create(dto);
            if(pass == null){
                throw new Exception("Passwordi nuk eshte krijuar!");
            }
            return pass;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

   @Override
    public Passwords update(UpdatePasswordsDto dto){
        try{
            if(dto== null || dto.getId()<=0){
                throw new IllegalArgumentException("UpdatePasswordsDto invalide!");
            }
            Passwords existingPass = this.modelRepository.getById(dto.getId());
            if(existingPass == null){
                throw new Exception("Password me id "+ dto.getId() + " nuk ekziston!");
            }
            String salt = generateSalt();
            String hashedPassword = hashPassword(dto.getPasswordHash(), salt,existingPass.getIterations());
            UpdatePasswordsDto updatedPassword = new UpdatePasswordsDto(
                    dto.getId(),
                    hashedPassword,
                    salt
            );
            return this.modelRepository.update(updatedPassword);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifyPassword(int passwordId, String providedPassword){
        try{
            Passwords storedpass = passwordsRepository.getPasswordById(passwordId);
            if(storedpass== null){
                return false;
            }

            String hashProvidedPassword = hashPassword(
                    providedPassword.trim(),
                    storedpass.getSalt().trim(),
                    storedpass.getIterations()
            );
            System.out.println("Stored hash: " + storedpass.getPasswordHash());
            System.out.println("Generated hash: " + hashProvidedPassword);

            return storedpass.getPasswordHash().trim().equals(hashProvidedPassword);
        }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserId(int passwordId, int userId) {
        if (passwordId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("Invalid password ID or user ID.");
        }
        return passwordsRepository.updateUserId(passwordId, userId);
    }

    public int getPasswordIdByUsername(String username) {
        return passwordsRepository.getPasswordIdByUsername(username);
    }
}
