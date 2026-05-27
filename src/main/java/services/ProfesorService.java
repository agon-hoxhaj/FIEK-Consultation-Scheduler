package services;

import enums.Role;
import models.DTO_data_transfer_object.CreateProfesorDto;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdateProfesorDto;
import models.Profesor;
import models.User;
import repository.ProfesorRepository;
import repository.UserRepository;

import java.util.Map;

public class ProfesorService extends BaseService<Profesor, CreateProfesorDto, UpdateProfesorDto, ProfesorRepository>{
    private ProfesorRepository profesorRepository;
    private UserRepository userRepository;

    public ProfesorService(){
        super("profesoret", new ProfesorRepository());
        this.profesorRepository = this.modelRepository;
        this.userRepository = new UserRepository();
    }

    public Profesor getByUserId(int userId) {
        return profesorRepository.getByUserId(userId);
    }

    @Override
    public Profesor create(CreateProfesorDto dto){
        try {
            if (dto == null || dto.getKabinetiPersonal() <= 0 || dto.getUserid() <= 0 || !dto.isProfesor_aktiv()) {
                throw new IllegalArgumentException("CreateProfesorDto invalide!");
            }
            if (userRepository.getById(dto.getUserid()) == null) {
                UserService userService = new UserService();
                CreateUserDto createUser = new CreateUserDto(
                        dto.getEmri() + dto.getMbiemri(),
                        userService.getLastAdded().getId() + 1,
                        Role.profesor
                );
                userService.create(createUser);
            }
            Profesor profesor = this.profesorRepository.create(dto);
            if (profesor == null) {
                throw new Exception("Krijimi i profesorit te ri deshtoi!");
            }
            return profesor;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Profesor update(UpdateProfesorDto dto){
        try{
            if(dto == null || dto.getKabinetiPersonal()<=0 || dto.getIdUser()<=0 || !dto.isProfesor_aktiv()){
                throw new IllegalArgumentException("UpdateProfesorDto invalide!");
            }

            User user = userRepository.getById(dto.getIdUser());
            if(user == null){
                throw new Exception("User me id " + dto.getIdUser() + " nuk ekziston!");
            }

            Profesor existingProfesor = profesorRepository.getById(dto.getId());
            if(existingProfesor == null){
                throw new Exception("Profesori me id " + dto.getId() + " nuk ekziston!");
            }

            UpdateProfesorDto updatedProfesor = new UpdateProfesorDto(
                    dto.getNumriTelefonit(),
                    dto.getKabinetiPersonal(),
                    dto.isProfesor_aktiv()
            );
            if(this.profesorRepository.update(updatedProfesor) == null){
                throw new Exception("Nuk u perditsua profesori!");
            }
            return this.profesorRepository.update(updatedProfesor);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getProfIdByEmriMbiemri(String emri, String mbiemri){
        return this.modelRepository.getProfIdByEmriMbiemri(emri,mbiemri);
    }

    public String getNameById(int id){
        Profesor prof=profesorRepository.getById(id);
        return prof.getEmri()+" "+prof.getMbiemri();
 }

    public Map<String, Integer> merrNumrinEProfPerDrejtim() {
        return profesorRepository.merrNumrinEProfPerDrejtim();
    }
}
