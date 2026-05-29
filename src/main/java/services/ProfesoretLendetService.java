package services;

import models.DTO_data_transfer_object.CreateProfesorLendaDto;
import models.DTO_data_transfer_object.UpdateProfesorLendaDto;
import models.ProfesorLenda;
import repository.LendetRepository;
import repository.ProfesorRepository;
import repository.ProfesoretLendetRepository;

import java.util.ArrayList;

public class ProfesoretLendetService extends BaseService<ProfesorLenda, CreateProfesorLendaDto, UpdateProfesorLendaDto, ProfesoretLendetRepository> {
    private final ProfesoretLendetRepository profesoretLendetRepository;
    private final ProfesorRepository profesorRepository;
    private final LendetRepository lendetRepository;

    public ProfesoretLendetService() {
        super("profesoret_lendet",new ProfesoretLendetRepository());
        this.profesoretLendetRepository = this.modelRepository;
        this.profesorRepository= new ProfesorRepository();
        this.lendetRepository=new LendetRepository();
    }

    protected ProfesorLenda create(CreateProfesorLendaDto createProfesorLendaDto) {return null;}
    public ProfesorLenda update(UpdateProfesorLendaDto updateProfesorLendaDto) {return null;}

    public String getLendaName(int idpl){
        int idLenda=profesoretLendetRepository.getById(idpl).getIdLenda();
        return lendetRepository.getById(idLenda).getEmri();
    }

    public String getLendaNameEnglish(int idpl){
        int idLenda=profesoretLendetRepository.getById(idpl).getIdLenda();
        return lendetRepository.getById(idLenda).getEmriEnglish();
    }

    public String getProfesorName( int idpl){
        int idProfesori=profesoretLendetRepository.getById(idpl).getIdProfesor();
        return profesorRepository.getById(idProfesori).getEmri();
    }

    public String getProfesorLendaName(int idpl){
        return getProfesorName(idpl)+" - "+getLendaName(idpl);
    }

    public String getProfesorLendaNameEnglish(int idpl){
        return getProfesorName(idpl)+" - "+getLendaNameEnglish(idpl);
    }

    public ArrayList<Integer> getIdsById(int idProfesori){
        return profesoretLendetRepository.getIdsById(idProfesori);
    }


}
