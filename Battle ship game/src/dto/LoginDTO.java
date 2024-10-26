package dto;
import battle.ship.model.Player;
import java.io.Serializable;

public class LoginDTO implements Serializable{
    private String username;
    private String password;
    private Player player;
    private static final long serialVersionUID = 1L;
    public LoginDTO() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
}