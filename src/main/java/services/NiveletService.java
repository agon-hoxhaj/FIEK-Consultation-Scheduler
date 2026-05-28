package services;

import models.DTO_data_transfer_object.CreateNiveletDto;
import models.DTO_data_transfer_object.UpdateNiveletDto;
import models.Nivelet;
import repository.NiveletRepository;

public class NiveletService extends BaseService<Nivelet, CreateNiveletDto, UpdateNiveletDto, NiveletRepository>{
    private final NiveletRepository repository;

    public NiveletService() {
        super("nivelet", new NiveletRepository());
        this.repository = this.modelRepository;
    }

    @Override
    public Nivelet create(CreateNiveletDto dto) {

        if (repository.existsByNiveli(dto.getNiveli())) {
            throw new IllegalArgumentException("Niveli ekziston");
        }
        if (repository.existsByNiveliEnglish(dto.getNiveliEnglish())) {
            throw new IllegalArgumentException("Level already exists");
        }

        return repository.create(dto);
    }

    @Override
    public Nivelet update(UpdateNiveletDto dto) {

        if (repository.getById(dto.getId()) == null) {
            throw new IllegalArgumentException("Niveli me ID nuk eshte gjetur: " + dto.getId());
        }
        return repository.update(dto);
    }

    public boolean niveliExists(String niveli) {
        return repository.existsByNiveli(niveli);
    }

    public boolean niveliEnglishExists(String niveliEnglish) {
        return repository.existsByNiveliEnglish(niveliEnglish);
    }

    public String getNiveliName( int id){
        return repository.getById(id).getNiveli();
    }
}