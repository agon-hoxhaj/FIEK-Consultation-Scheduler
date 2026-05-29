package services;


import enums.Role;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdateUserDto;
import models.User;
import repository.PasswordsRepository;
import repository.UserRepository;

public class UserService extends BaseService<User, CreateUserDto, UpdateUserDto, UserRepository> {
    private UserRepository userRepository;
    private PasswordsRepository passwordsRepository;

    public UserService(){
        super("perdoruesit", new UserRepository());
        this.userRepository = this.modelRepository;
        this.passwordsRepository = new PasswordsRepository();
    }

    @Override
    public User create(CreateUserDto dto){
        try{
            if(dto.getUsername().isEmpty()){
                throw new Exception("Informatat per userin nuk jane valide!");
            }
            if(userRepository.existsById(dto.getId())){
                throw new IllegalArgumentException("Useri me id " + dto.getId() + " ekziston!");
            }
            if(!isValidRole(dto.getRoli())){
                throw new IllegalArgumentException("Roli invalid!");
            }
            User user = this.modelRepository.create(dto);
            if(user == null) {
                throw new Exception("User nuk u krijua!");
            }
            return user;
            }catch(Exception e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public User update(UpdateUserDto dto){
        try{
            if(dto == null || dto.getId()<=0){
                throw new IllegalArgumentException("UpdateUserDto invalide!");
            }
            User existingUser = this.modelRepository.getById(dto.getId());
            if(existingUser == null){
                throw new Exception("User me id " + dto.getId() + " nuk ekziston!");
            }
            String newUsername = dto.getUsername()!= null ? dto.getUsername() : existingUser.getUsername();
            int newPasswordId = existingUser.getPassword();
            if(dto.getPassword() > 0){
                boolean passwordExists = this.passwordsRepository.existsById(dto.getPassword());
                if(!passwordExists){
                    throw new Exception("Password me id " + dto.getPassword() + " nuk ekziston!");
                }
                newPasswordId = dto.getPassword();
            }
            Role newRole = dto.getRole() != null ? dto.getRole() : existingUser.getRole();
            if (!isValidRole(newRole)) {
                throw new IllegalArgumentException("Invalid role!");
            }
            UpdateUserDto updatedUser = new UpdateUserDto(
                    newUsername,
                    newPasswordId,
                    newRole
            );
            return this.modelRepository.update(updatedUser);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIdByUsername(String username){
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username nuk mund te jete null apo i zbrazet!");
        }

        try{
            return userRepository.getUserIdByUsername(username);
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public Role getUserRoleByUserId(int userId){
        return userRepository.getUserRoleByUserId(userId);
    }

    public boolean doesUsernameExist(String username){
        return userRepository.doesUsernameExist(username);
    }

    public boolean validatePersonalNumberAndUser(String username, String personalNumber){
        return userRepository.isUsernameAndPersonalNumMatching(username,personalNumber);
    }

    public User updatePassword(int id, UpdateUserDto dto){
        try{
            if(dto == null){
                throw new IllegalArgumentException("UpdateUserDto invalide!");
            }
            User existingUser = this.modelRepository.getById(id);
            if(existingUser == null){
                throw new Exception("User me id " + id + " nuk ekziston!");
            }
            int newPasswordId = existingUser.getPassword();
            if(dto.getPassword() > 0){
                boolean passwordExists = this.passwordsRepository.existsById(dto.getPassword());
                if(!passwordExists){
                    throw new Exception("Password me id " + dto.getPassword() + " nuk ekziston!");
                }
                newPasswordId = dto.getPassword();
            }
            UpdateUserDto updatedUser = new UpdateUserDto(
                    newPasswordId
            );
            return this.modelRepository.updatePassword(id,updatedUser);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
