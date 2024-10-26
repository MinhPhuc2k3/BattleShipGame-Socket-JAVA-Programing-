/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battle.ship.model;

import controller.server.ClientListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author MINH PHUC
 */
public class Room {
    public static Map<String, Room> playerMap = new TreeMap<>();
    private List<ClientListener> listHandler = new ArrayList<>();
    private int number = 0;
    private int numberOfReady = 0;
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public void addPlayer(String username, ClientListener handler){
        playerMap.put(username, this);
        listHandler.add(handler);
        number++;
    }

    public List<ClientListener> getListHandler() {
        return listHandler;
    }
    
    
    
    public void removePlayer(String username){
        playerMap.remove(username);
    }
    
    public void increaseReady(){
        numberOfReady++;
    }
    
    public int getNumberOfReady(){
        return numberOfReady;
    }
    
    public ClientListener getOpponentHandler(ClientListener player){
        for(ClientListener x:listHandler){
            if(!x.equals(player)){
                return x;
            }
        }
        return null;
    }
    
}
