/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import battle.ship.model.Player;
import java.io.Serializable;

/**
 *
 * @author MINH PHUC
 */
public class BattleDTO implements Serializable{
    private Player opponent;
    private boolean accept;
    private int status;
    private boolean inGame;
    private static final long serialVersionUID = 1L;
    
    public static final int REQUEST = 0;
    public static final int RESPONSE = 1;

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    public BattleDTO(Player opponent) {
        this.opponent = opponent;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    
    
    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
    
}
