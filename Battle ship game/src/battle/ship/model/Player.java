package battle.ship.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author MINH PHUC
 */
public class Player implements Serializable{
    private int id;
    private String username;
    private String password;
    private String email;
    private int diem;
    private boolean online;
    public Player(){
    }
    
    public Player(int id, String username, String password, String email, int diem) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.diem = diem;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
