package services;

import models.User;

public class SessionManager {

    private static SessionManager instance;
    private String username;
    private int userId;
    private int studentId;
    private int adminId;
    private int profId;
    private User currentUser;

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getProfId() {
        return this.profId;
    }

    public void setProfId(int profId) {
        this.profId = profId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void clearSession() {
        this.username = null;
        this.userId = 0;
        this.studentId = 0;
        this.adminId = 0;
        this.profId = 0;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
