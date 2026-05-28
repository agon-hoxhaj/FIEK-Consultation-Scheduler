package services;

import models.DTO_data_transfer_object.CreateOrariDataDto;
import models.DTO_data_transfer_object.UpdateOrariDataDto;
import models.OrariData;
import repository.OraretDataRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class OrariDataService extends BaseService<OrariData, CreateOrariDataDto, UpdateOrariDataDto, OraretDataRepository>{
    private final OraretDataRepository oraretDataRepository;

    public OrariDataService(){
        super("oraret_data",new OraretDataRepository());
        this.oraretDataRepository =this.modelRepository;
    }

    @Override
    public OrariData create(CreateOrariDataDto createOrariData){
        try {
            Date todaySqlDate = Date.valueOf(LocalDate.now());
            if (createOrariData.getData().before(todaySqlDate)) {
                throw new Exception(" Date invalide");
            }
            OrariData orariData = super.modelRepository.create(createOrariData);
            if (orariData == null) {
                throw new Exception(" Orari Data nuk eshte krijuar");
            }
            return orariData;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrariData update(UpdateOrariDataDto updateOrariDataDto){
        return null;
    }

    public void generateValidOrareData(){
        oraretDataRepository.generateValidOrareData();
    }

    public List<OrariData> getValidOrariDataByOrarId(int orariId) {
        return this.modelRepository.getValidOrariDataByOrarId(orariId);
    }
}
