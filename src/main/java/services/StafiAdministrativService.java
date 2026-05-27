package services;

import enums.Role;
import models.DTO_data_transfer_object.CreateStafiAdministrativDto;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdateStafiAdministrativDto;
import models.StafiAdministrativ;
import models.User;
import repository.StafiAdministrativRepository;
import repository.UserRepository;

public class StafiAdministrativService extends BaseService<StafiAdministrativ, CreateStafiAdministrativDto, UpdateStafiAdministrativDto, StafiAdministrativRepository> {
    private StafiAdministrativRepository stafiAdministrativRepository;
    private UserRepository userRepository;

    public StafiAdministrativService(){
        super("stafi_administrativ", new StafiAdministrativRepository());
        this.stafiAdministrativRepository = this.modelRepository;
        this.userRepository = new UserRepository();
    }

    @Override
    public StafiAdministrativ create(CreateStafiAdministrativDto dto){
        try{
            if(dto == null || dto.getUserId() <=0) {
                throw new Exception("Informatat per stafin nuk jane valide!");
            }
            if(userRepository.getById(dto.getUserId()) == null){
                UserService userService = new UserService();
                CreateUserDto createUser = new CreateUserDto(
                        dto.getEmri() + dto.getMbiemri(),
                        userService.getLastAdded().getId() + 1,
                        Role.staf_administrativ
                );
                userService.create(createUser);
            }
            StafiAdministrativ stafi = this.stafiAdministrativRepository.create(dto);
            if (stafi == null) {
                throw new Exception("Krijimi i stafit te ri deshtoi!");
            }
            return stafi;
        }catch(Exception e){
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public StafiAdministrativ update(UpdateStafiAdministrativDto dto){
        try{
            if(dto == null || dto.getId()<=0){
                throw new Exception("UpdateStafiAdministrativDto invalide!");
            }
            User user = userRepository.getById(dto.getIdUser());
            if(user == null){
                throw new Exception("User me id " + dto.getIdUser() + " nuk ekziston!");
            }

            StafiAdministrativ existingStaf = stafiAdministrativRepository.getById(dto.getId());
            if(existingStaf == null){
                throw new Exception("Stafi me id" + dto.getId() + "nuk ekziston!");
            }

            UpdateStafiAdministrativDto updatedStaf = new UpdateStafiAdministrativDto(
              dto.getNumriTelefonit(),
              dto.getTitulli()
            );

            if(this.stafiAdministrativRepository.update(updatedStaf)==null){
                throw new Exception("Nuk u perditsua stafi!");
            }
            return this.stafiAdministrativRepository.update(updatedStaf);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
