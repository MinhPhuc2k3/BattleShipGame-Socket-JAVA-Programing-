/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.client;

import dto.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author MINH PHUC
 */
public class ServerListener implements Runnable{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ClientControl control;

    public ServerListener(Socket socket, ObjectInputStream input, ObjectOutputStream output, ClientControl control) {
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.control = control;
    }
    
    @Override
    public void run() {
        Message msg = null;
        boolean hasLogined = true;
        while(!socket.isClosed()&& hasLogined){
            try {
                msg = (Message) input.readObject();
                switch (msg.getCommand()) {
                    case Message.UPDATEONLINE -> control.updateListUserResponse(msg.getPlayerDTO().getPlayers(), msg.getPlayerDTO().getPlayersOnline(), msg.getPlayerDTO().getPlayersInGame());
                    case Message.LOGOUT -> hasLogined = false;
                    case Message.BATTLE -> control.battleRespone(msg);
                    case Message.READY -> control.readyResponse(msg);
                    case Message.SHOOT -> control.shootRespone(msg);
                    case Message.MATCHHISTORY -> control.matchHistoryReponse(msg);
                    case Message.EXITGAME -> control.exitGameReponse(msg);
                    default -> throw new AssertionError();
                }
            } catch (IOException | ClassNotFoundException ex) {
                
            }
            
        }
    }
    
}
