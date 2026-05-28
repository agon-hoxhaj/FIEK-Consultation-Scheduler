package services;

import models.DTO_data_transfer_object.CreateDrejtimetDto;
import models.DTO_data_transfer_object.UpdateDrejtimetDto;
import models.Drejtimet;
import repository.DrejtimetRepository;

public class DrejtimetService extends BaseService<Drejtimet,CreateDrejtimetDto,UpdateDrejtimetDto,DrejtimetRepository>{

    private final DrejtimetRepository drejtimetRepository ;

    DrejtimetService(){
        super("drejtimet",new DrejtimetRepository());
        this.drejtimetRepository=new DrejtimetRepository();
    }

    public Drejtimet create(CreateDrejtimetDto dto) {
        if (!isValidCreateDto(dto)) {
            throw new IllegalArgumentException("Të dhënat për krijimin e drejtimit janë të pavlefshme.");
        }
        return drejtimetRepository.create(dto);
    }

    @Override
    Drejtimet update(UpdateDrejtimetDto updateDrejtimetDto) {
        if (!isValidUpdateDto(updateDrejtimetDto)) {
            throw new IllegalArgumentException("Të dhënat për përditësim janë të pavlefshme.");
        }
        return drejtimetRepository.update(updateDrejtimetDto);
    }

    private boolean isValidCreateDto(CreateDrejtimetDto dto) {
        return dto.getDrejtimi() != null && !dto.getDrejtimi().isBlank() &&
                dto.getDrejtimiEnglish() != null && !dto.getDrejtimiEnglish().isBlank();
    }

    private boolean isValidUpdateDto(UpdateDrejtimetDto dto) {
        return dto.getId() > 0 &&
                dto.getDrejtimi() != null && !dto.getDrejtimi().isBlank() &&
                dto.getDrejtimiEnglish() != null && !dto.getDrejtimiEnglish().isBlank();
    }

    public String getDrejtimiName(int idDrejtimi){
        return drejtimetRepository.getById(idDrejtimi).getDrejtimi();
    }

    public String getDrejtimiNameEnglish(int idDrejtimi){
        return drejtimetRepository.getById(idDrejtimi).getDrejtimiEnglish();
    }
}
