package services;

import models.DTO_data_transfer_object.CreateTerminetDto;
import models.DTO_data_transfer_object.UpdateTerminetDto;
import models.Terminet;
import repository.TerminetRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TerminetService extends  BaseService<Terminet,CreateTerminetDto,UpdateTerminetDto,TerminetRepository>{
    private final TerminetRepository terminetRepository;

    public TerminetService(){
        super("terminet",new TerminetRepository());
        this.terminetRepository=this.modelRepository;
    }

    public Terminet create(CreateTerminetDto dto) {
        if (!isValidCreateDto(dto)) {
            throw new IllegalArgumentException("Të dhënat për krijimin e terminit janë të pavlefshme.");
        }
        return terminetRepository.create(dto);
    }

    public Terminet update(UpdateTerminetDto dto) {
        if (!isValidUpdateDto(dto)) {
            throw new IllegalArgumentException("Të dhënat për përditësim janë të pavlefshme.");
        }
        return terminetRepository.update(dto);
    }

    public boolean delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID e pavlefshme.");
        }
        return terminetRepository.delete(id);
    }

    public ArrayList<Terminet> getAllTerminetByProfId(int idProfesori){
        if(idProfesori<=0){
            throw new IllegalArgumentException("ID e pavlefshme.");
        }
        return terminetRepository.getAllTerminetByProfId(idProfesori);
    }

    private boolean isValidCreateDto(CreateTerminetDto dto) {
        return dto.getIdOrari() > 0 &&
                dto.getIdStudenti() > 0 &&
                dto.getIntervaliKohor() != null &&
                !dto.getIntervaliKohor().before(Timestamp.from(Instant.now())) &&
                dto.getArsyeja() != null && !dto.getArsyeja().isBlank();
    }

    private boolean isValidUpdateDto(UpdateTerminetDto dto) {
        return dto.getId() > 0 &&
                dto.getIdOrari() > 0 &&
                dto.getIdStudenti() > 0 &&
                dto.getIntervaliKohor() != null &&
                !dto.getIntervaliKohor().after(Timestamp.from(Instant.now())) &&
                dto.getArsyeja() != null && !dto.getArsyeja().isBlank();
    }

    public List<Terminet> getReservedValidAppointmentsByStudent(int studentId) {
        return this.modelRepository.getReservedValidAppointmentsByStudent(studentId);
    }

    public boolean hasStudentReservedAppointment(int studentId, LocalDateTime appointmentDateTime) {
        return this.modelRepository.hasStudentReservedAppointment(studentId, appointmentDateTime);
    }

    public void removeInvalidTermin(){
        terminetRepository.removeInvalidTermin();
    }
}
