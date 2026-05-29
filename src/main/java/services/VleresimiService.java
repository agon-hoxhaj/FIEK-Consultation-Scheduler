package services;

import models.DTO_data_transfer_object.CreateVleresimiDto;
import models.DTO_data_transfer_object.UpdateVleresimiDto;
import models.Vleresimi;
import repository.VleresimiRepository;

public class VleresimiService extends BaseService<Vleresimi, CreateVleresimiDto, UpdateVleresimiDto, VleresimiRepository> {
    private final VleresimiRepository vleresimiRepository;

    public VleresimiService(){
        super("vleresimi", new VleresimiRepository());
        this.vleresimiRepository = this.modelRepository;
    }

    protected Vleresimi create(CreateVleresimiDto createVleresimi){ return null; }
    public Vleresimi update(UpdateVleresimiDto updateVleresimi){ return null; }
}
