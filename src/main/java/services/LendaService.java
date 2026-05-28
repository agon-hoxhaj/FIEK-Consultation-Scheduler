package services;


import models.DTO_data_transfer_object.CreateLendaDto;
import models.DTO_data_transfer_object.UpdateLendaDto;
import models.Lenda;
import repository.LendetRepository;

public class LendaService extends BaseService<Lenda, CreateLendaDto, UpdateLendaDto, LendetRepository>{
    private final LendetRepository lendetRepository;

    public LendaService(){
        super("oraret", new LendetRepository());
        this.lendetRepository = this.modelRepository;
    }

    protected Lenda create(CreateLendaDto createLenda){ return null; }
    public Lenda update(UpdateLendaDto updateLenda){ return null; }

}
