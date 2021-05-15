package sample.businessLayer;

import java.io.Serializable;

public class User implements Serializable {
    private int ID;
    private String username;
    private String password;
    private String type;

    public User(int ID, String username, String password, String type) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
