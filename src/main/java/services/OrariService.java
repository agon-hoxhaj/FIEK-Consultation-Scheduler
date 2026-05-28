package services;

import models.DTO_data_transfer_object.CreateOrariDto;
import models.DTO_data_transfer_object.UpdateOrariDto;
import models.Orari;
import repository.OraretRepository;

import java.util.ArrayList;

public class OrariService extends BaseService<Orari , CreateOrariDto, UpdateOrariDto,OraretRepository>{
    private final OraretRepository oraretRepository;

    public OrariService() {
        super("oraret", new OraretRepository());
        this.oraretRepository = this.modelRepository;
    }

    @Override
    public Orari create(CreateOrariDto createOrari) {
        try {
            if (createOrari == null) {
                throw new Exception("nuk mund te krijoni nje orar");
            }
            Orari orari =oraretRepository.create(createOrari);
            return orari;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Orari update(UpdateOrariDto updateOrariDto) {
        try {
            if (updateOrariDto == null) {
                throw new Exception("nuk mund te krijoni nje orar");
            }
            Orari orari =oraretRepository.update(updateOrariDto);
            return orari;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Orari> getActiveOraret(int idDNSL){
        if(idDNSL>0) {
            return oraretRepository.getActiveOraret(idDNSL);
        }else{
            throw new IllegalArgumentException("Invalid ID: pritet nje vlere me e madhe se 0");
        }
    }
    public int count(int idDNSL){
        if(idDNSL>0) {
            return oraretRepository.count(idDNSL);
        }else{
            throw new IllegalArgumentException("Invalid ID: pritet nje vlere me e madhe se 0");
        }
    }

    public int countReservedTerminet(int orariDataId) {
        return this.modelRepository.countReservedTerminet(orariDataId);
    }


}
