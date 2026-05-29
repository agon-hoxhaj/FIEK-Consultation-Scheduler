package services;

import models.DTO_data_transfer_object.CreateDNSDto;
import models.DTO_data_transfer_object.UpdateDNSDto;
import models.DrejtimiNiveliSemestri;
import repository.DNSRepository;

public class DNSService extends BaseService<DrejtimiNiveliSemestri, CreateDNSDto, UpdateDNSDto, DNSRepository>{
    private final DNSRepository dnsrepository;
    private final DrejtimetService drejtimetService;
    private final NiveletService niveletService;

    public DNSService() {
        super("drejtimet_nivelet_semestrat", new DNSRepository());
        this.dnsrepository = new DNSRepository();
        this.drejtimetService= new DrejtimetService();
        this.niveletService=new NiveletService();

    }

    public boolean combinationExists(int idDrejtimi, int idNiveli, int semestri) {
        return dnsrepository.existsByCombination(idDrejtimi, idNiveli, semestri);
    }

    @Override
    protected DrejtimiNiveliSemestri create(CreateDNSDto createDNSDto) {
        return null;
    }

    @Override
    DrejtimiNiveliSemestri update(UpdateDNSDto updateDNSDto) {
        return null;
    }

    public String getDrejtimiNiveliSemestriName(int idDNS){
        DrejtimiNiveliSemestri DNS=dnsrepository.getById(idDNS);

        int idDrejtimi= DNS.getIdDrejtimi();
        String drejtimiName=drejtimetService.getDrejtimiName(idDrejtimi);

        int idNiveli=DNS.getIdNiveli();
        String niveliName=niveletService.getNiveliName(idNiveli);

        int semetri=DNS.getSemestri();

        String name= drejtimiName+ " - "+niveliName+ " - Sem. "+semetri;
        return name;
    }

    public String getDrejtimiNiveliSemestriNameEnglish(int idDNS){
        DrejtimiNiveliSemestri DNS=dnsrepository.getById(idDNS);

        int idDrejtimi= DNS.getIdDrejtimi();
        String drejtimiName=drejtimetService.getDrejtimiNameEnglish(idDrejtimi);

        int idNiveli=DNS.getIdNiveli();
        String niveliName=niveletService.getNiveliName(idNiveli);

        int semetri=DNS.getSemestri();

        String name= drejtimiName+ " - "+niveliName+ " - Sem. "+semetri;
        return name;
    }

    public int getIdStudimi(int idDrejtimi, int idNiveli, int semestri) {
        return this.modelRepository.findIdByParams(idDrejtimi, idNiveli, semestri);
    }
}