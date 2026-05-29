package services;

import models.DTO_data_transfer_object.CreateShtetiDto;
import models.DTO_data_transfer_object.UpdateShtetiDto;
import models.Shteti;
import repository.ShtetiRepository;


public class ShtetiService extends BaseService<Shteti, CreateShtetiDto, UpdateShtetiDto, ShtetiRepository> {
    private final ShtetiRepository shtetiRepository;

    public ShtetiService(){
        super("shtetet", new ShtetiRepository());
        this.shtetiRepository = this.modelRepository;
    }

    protected Shteti create(CreateShtetiDto createShteti){ return null; }
    public Shteti update(UpdateShtetiDto updateShteti){ return null; }
}
