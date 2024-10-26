/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import battle.ship.model.Player;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author MINH PHUC
 */
public class PlayerDTO implements  Serializable{
    private static final long serialVersionUID = 1L;
    private List<Player> playersOnline;
    private List<Player> players;

    public PlayerDTO() {
    }

    public List<Player> getPlayersOnline() {
        return playersOnline;
    }

    public void setPlayersOnline(List<Player> playersOnline) {
        this.playersOnline = playersOnline;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    
}
