/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import battle.ship.model.MatchHistory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MINH PHUC
 */
public class MatchHistoryDTO implements Serializable{
    List<MatchHistory> matchHistories = new ArrayList<>();

    public List<MatchHistory> getMatchHistories() {
        return matchHistories;
    }

    public void setMatchHistories(List<MatchHistory> matchHistories) {
        this.matchHistories = matchHistories;
    }
    
}
