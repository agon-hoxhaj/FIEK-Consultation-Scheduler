package services;


import enums.Role;
import models.DTO_data_transfer_object.CreateStudentDto;
import models.DTO_data_transfer_object.CreateUserDto;
import models.DTO_data_transfer_object.UpdateStudentDto;
import models.Student;
import models.User;
import repository.StudentRepository;
import repository.UserRepository;

import java.util.List;

public class StudentService extends BaseService<Student, CreateStudentDto, UpdateStudentDto, StudentRepository>{
    private StudentRepository studentRepository;
    private UserRepository userRepository;

    public StudentService(){
        super("studentat", new StudentRepository());
        this.studentRepository = this.modelRepository;
        this.userRepository = new UserRepository();
    }

    public List<Student> getStudentetByProfesorId(int profesorId) {
        return studentRepository.getStudentetByProfesorId(profesorId);
    }

    @Override
    public Student create(CreateStudentDto dto){
        try {
            if (dto == null || dto.getStudimi() <= 0 || dto.getUserid() <= 0 || !dto.isStudent_aktiv()) {
                throw new IllegalArgumentException("CreateStudentDto invalide!");
            }
            if (userRepository.getById(dto.getUserid()) == null) {
                UserService userService = new UserService();
                CreateUserDto createUser = new CreateUserDto(
                        dto.getEmri() + dto.getMbiemri(),
                        userService.getLastAdded().getId() + 1,
                        Role.student
                );
                userService.create(createUser);
            }
            Student student = this.studentRepository.create(dto);
            if (student == null) {
                throw new Exception("Krijimi i studentit te ri deshtoi!");
            }
            return student;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
        }

        @Override
    public Student update(UpdateStudentDto dto){
        try{
            if(dto == null || dto.getStudimi()<=0 || dto.getIdUser()<=0 || !dto.isStudent_aktiv()){
                throw new IllegalArgumentException("UpdateStudentDto invalide!");
            }

            User user = userRepository.getById(dto.getIdUser());
            if(user == null){
                throw new Exception("User me id " + dto.getIdUser() + " nuk ekziston!");
            }

            Student existingStudent = studentRepository.getById(dto.getId());
            if(existingStudent == null){
                throw new Exception("Studenti me id " + dto.getId() + " nuk ekziston!");
            }

            UpdateStudentDto updatedStudent = new UpdateStudentDto(
                    dto.getNumriTelefonit(),
                    dto.getStudimi(),
                    dto.isStudent_aktiv()
            );
            if(this.studentRepository.update(updatedStudent) == null){
                throw new Exception("Nuk u perditsua studenti!");
            }
            return this.studentRepository.update(updatedStudent);
        }catch(Exception e){
            e.printStackTrace();
        }
      return null;
    }

    public int merrNumrinEStudentPerDrejtim(int idDrejtimi) {
        return studentRepository.merrNumrinEStudentPerDrejtim(idDrejtimi);
    }

    public int merrNumrinTotalTeStudenteve(){
        return studentRepository.merrNumrinTotalTeStudenteve();
    }
    }

