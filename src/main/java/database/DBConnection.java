package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost/FIEK-Consultation-Scheduler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Agoni123.,";
    private static Connection connection;
    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
            return connection;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
