package services;

import enums.Role;
import repository.BaseRepository;

import java.util.ArrayList;

public abstract class BaseService<Model, CreateModelDto, UpdateModelDto, ModelRepository extends BaseRepository<Model, CreateModelDto, UpdateModelDto>>  {
    protected ModelRepository modelRepository;
    private String emriTabeles;

    public BaseService(String emriTabeles, ModelRepository modelRepository){
        this.emriTabeles = emriTabeles;
        this.modelRepository = modelRepository;
    }

    protected abstract Model create(CreateModelDto createModelDto);
    abstract Model update(UpdateModelDto updateModelDto);

    public Model getById(int id) {
        try {
            if (id < 1) {
                throw new Exception("Id nuk është valid!");
            }
            Model model=this.modelRepository.getById(id);
            if(model == null){
                throw new Exception(this.emriTabeles+" Id: " + id + " nuk ekziston në tabelen "+emriTabeles+" !");
            }
            return model;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidRole(Role role){
        return role.equals(Role.student) || role.equals(Role.profesor) || role.equals(Role.staf_administrativ);
    }

    public ArrayList<Model> getAll(){
        return modelRepository.getAll();
    }

    public  ArrayList<Model> getAll(String condition){
        return modelRepository.getAll(condition);
    }
    public boolean deleteById(int id){
        try{
            if(id<=0){
                throw new IllegalArgumentException("Id invalide!");
            }
            return modelRepository.delete(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Model getLastAdded(){
        return this.modelRepository.getLastAdded();
    }

    public int getIdByUserId(int userId){
        return this.modelRepository.getIdByUserId(userId);
    }

}
