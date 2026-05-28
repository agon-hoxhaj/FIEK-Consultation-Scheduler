package services;

import models.DTO_data_transfer_object.CreateDNSLDto;
import models.DTO_data_transfer_object.UpdateDNSLDto;
import models.DrejtimiNiveliSemestriLenda;
import repository.DNSLRepository;

import java.util.ArrayList;


public class DNSLService extends BaseService<DrejtimiNiveliSemestriLenda, CreateDNSLDto, UpdateDNSLDto, DNSLRepository> {
    private final DNSLRepository drejtimiNiveliSemestriLendaRepository;
    private final ProfesoretLendetService profesoretLendetService;
    private final DNSService drejtimiNiveliSemestriService;

    public DNSLService(){
        super("drejtimet_nivelet_semestrat_lendet", new DNSLRepository());
        this.drejtimiNiveliSemestriLendaRepository = this.modelRepository;
        this.profesoretLendetService= new ProfesoretLendetService();
        this.drejtimiNiveliSemestriService=new DNSService();
    }

    @Override
    protected DrejtimiNiveliSemestriLenda create(CreateDNSLDto createDNSLDto){return null;}
    public DrejtimiNiveliSemestriLenda update(UpdateDNSLDto updateDNSLDto){return null;}

    public ArrayList<DrejtimiNiveliSemestriLenda> getAllDNSL(int idProfesori){
        if(idProfesori<0){
            throw new IllegalArgumentException("Id me e vogel se 0");
        }
        ArrayList<Integer> idPL =profesoretLendetService.getIdsById(idProfesori);
        ArrayList<DrejtimiNiveliSemestriLenda> idDNSL=drejtimiNiveliSemestriLendaRepository.getDNSL(idPL);

        return idDNSL;
    }
    public String getNameByid(int id){
        int idProfesorLenda= drejtimiNiveliSemestriLendaRepository.getById(id).getIdProfesori_Lenda();
        String lendaName =profesoretLendetService.getLendaName(idProfesorLenda);

        int idDNS= drejtimiNiveliSemestriLendaRepository.getById(id).getIdDrejtimi_Niveli_Semestri();
        String drejtimiNiveliLendaName=drejtimiNiveliSemestriService.getDrejtimiNiveliSemestriName(idDNS);

        String name = drejtimiNiveliLendaName +" :: "+lendaName;
        return name;
    }
    public String getNameEnglishByid(int id){
        int idProfesorLenda= drejtimiNiveliSemestriLendaRepository.getById(id).getIdProfesori_Lenda();
        String lendaName =profesoretLendetService.getLendaNameEnglish(idProfesorLenda);

        int idDNS= drejtimiNiveliSemestriLendaRepository.getById(id).getIdDrejtimi_Niveli_Semestri();
        String drejtimiNiveliLendaName=drejtimiNiveliSemestriService.getDrejtimiNiveliSemestriNameEnglish(idDNS);

        String name = drejtimiNiveliLendaName +" :: "+lendaName;
        return name;
    }
    public int getId(int idDNS, int idPL){
        return drejtimiNiveliSemestriLendaRepository.getIdByIdDNSIdPL(idDNS ,idPL);
    }
}