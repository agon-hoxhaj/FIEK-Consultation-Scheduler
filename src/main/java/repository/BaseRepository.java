package repository;

import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public abstract class BaseRepository<Model,CreateModelDto, UpdateModelDto> {
   protected Connection connection;
   private String emriTabeles;

   public BaseRepository(String emriTabeles){
       this.connection = DBConnection.getConnection();
       this.emriTabeles = emriTabeles;
   }

   abstract Model fromResultSet(ResultSet res) throws SQLException;
   abstract Model create( CreateModelDto create);
   abstract Model update( UpdateModelDto update);

   public Model getById(int id){
       if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
           throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
       }
       String query = "SELECT * FROM " + this.emriTabeles + " WHERE id=?";
       try{
           PreparedStatement ppstm = this.connection.prepareStatement(query);
           ppstm.setInt(1,id);
           ResultSet res = ppstm.executeQuery();
           if(res.next()){
               return this.fromResultSet(res);
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
       return null;
   }

    public ArrayList<Model> getAll(){
        if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
            throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
        }
        ArrayList<Model> models = new ArrayList<>();
        String query = "SELECT * FROM " + this.emriTabeles;
        try{
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                models.add(this.fromResultSet(res));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return models;
    }

    public ArrayList<Model> getAll(String condition){
        if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
            throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
        }
        ArrayList<Model> models = new ArrayList<>();
        String query = "SELECT * FROM " + this.emriTabeles + " " + condition;
        try{
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                models.add(this.fromResultSet(res));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return models;
    }

    public ArrayList<Model> getAllByIds(ArrayList<Integer> ids, String kolona) {
        System.out.println(ids.get(0));
        if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
            throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
        }

        if (kolona == null || kolona.trim().isEmpty()) {
            throw new IllegalArgumentException("Kushti është i zbrazët.");
        }

        String query = "SELECT * FROM " + this.emriTabeles + " WHERE " + kolona + " = ?";

        ArrayList<Model> models = new ArrayList<>();

        try {
            for (Integer id : ids) {
                PreparedStatement ppstm = this.connection.prepareStatement(query);
                ppstm.setInt(1, id);
                ResultSet res = ppstm.executeQuery();

                while (res.next()) {
                    models.add(this.fromResultSet(res));
                }

                res.close();
                ppstm.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return models.isEmpty() ? null : models;
    }

    public ArrayList<Integer> getIdsById(int id, String kolona) {
        if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
            throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
        }

        if (kolona == null || kolona.trim().isEmpty()) {
            throw new IllegalArgumentException("Kolona është e zbrazët.");
        }

        String query = "SELECT id FROM " + this.emriTabeles + " WHERE " + kolona + " = ?";

        ArrayList<Integer> values = new ArrayList<>();

        try {
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1, id);
            ResultSet res = ppstm.executeQuery();

            while (res.next()) {
                values.add(res.getInt("id"));
            }

            res.close();
            ppstm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return values.isEmpty() ? null : values;
    }


   public boolean delete(int id){
       if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
           throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
       }
       String query = "DELETE FROM " + this.emriTabeles + " WHERE id=?";
       try{
           PreparedStatement ppstm = this.connection.prepareStatement(query);
           ppstm.setInt(1, id);
           return ppstm.executeUpdate() == 1;
       }catch(SQLException e){
           e.printStackTrace();
       }
       return false;
   }

   public boolean existsById(int id){
       if (this.emriTabeles == null || this.emriTabeles.isEmpty()) {
           throw new IllegalArgumentException("Tabela është e zbrazët ose nuk ekziston.");
       }
       String query = "SELECT 1 FROM " + this.emriTabeles + " WHERE id=?";
       try{
           PreparedStatement ppstm = this.connection.prepareStatement(query);
           ppstm.setInt(1,id);
           try(ResultSet res = ppstm.executeQuery()) {
               return res.next();
           }

       }catch(SQLException e){
           e.printStackTrace();
       }
       return false;
   }

   public int count(){
       String query = "SELECT COUNT(*) FROM " + this.emriTabeles;
       try{
           Statement stmt = this.connection.createStatement();
           ResultSet res = stmt.executeQuery(query);
           if(res.next()){
               return res.getInt(1);
           }
       }catch( SQLException e){
           e.printStackTrace();
       }
       return 0;
   }

    protected int count(String condition){
        String query = "SELECT COUNT(*) FROM " + this.emriTabeles +" "+condition;
        try{
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if(res.next()){
                return res.getInt(1);
            }
        }catch( SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

   public Model getLastAdded(){
       String query = "SELECT * FROM " + this.emriTabeles + " ORDER BY id DESC LIMIT 1";
       try{
           Statement stm = this.connection.createStatement();
           ResultSet res = stm.executeQuery(query);
           if(res.next()){
               return this.fromResultSet(res);
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
       return null;
   }

    public int getIdByUserId(int userId){
        String query = "SELECT id FROM " + this.emriTabeles + " WHERE perdoruesi=?";

        try{
            PreparedStatement ppstm = this.connection.prepareStatement(query);
            ppstm.setInt(1,userId);
            ResultSet res = ppstm.executeQuery();
            if(res.next()){
                return res.getInt("id");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
