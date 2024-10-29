/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.client;

import battle.ship.model.Player;
import dto.BattleDTO;
import dto.Message;
import dto.LoginDTO;
import dto.MatchHistoryDTO;
import dto.PointDTO;
import dto.ShootDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.BattleViewFrm;
import view.MainFrm;
import view.MatchHistoryFrm;
import view.ReadyFrm;

/**
 *
 * @author MINH PHUC
 */
public class ClientControl {

    private Socket socket;
    private Player player;
    private ExecutorService listener;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private MainFrm mainFrm;
    private ReadyFrm readyFrm;
    private BattleViewFrm battleViewFrm;
    private MatchHistoryFrm historyFrm;
    
    public ClientControl() {
        try {
            socket = new Socket("localhost", 23000);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setBattleViewFrm(BattleViewFrm battleViewFrm) {
        this.battleViewFrm = battleViewFrm;
    }

    public void setMainFrm(MainFrm mainFrm) {
        this.mainFrm = mainFrm;
        listener = Executors.newSingleThreadExecutor();
        listener.submit(new ServerListener(socket, input, output, this));
    }

    public void setReadyFrm(ReadyFrm readyFrm) {
        this.readyFrm = readyFrm;
    }
    
    public void setHistoryFrm(MatchHistoryFrm historyFrm) {
        this.historyFrm = historyFrm;
    }
    
    public MainFrm getMainFrm() {
        return mainFrm;
    }

    public Player getPlayer() {
        return player;
    }

    public void login(LoginDTO login) {
        try {
            Message message = new Message();
            message.setCommand(Message.LOGIN);
            message.setLoginDTO(login);
            output.writeObject(message);
            output.flush();
            Message messageLogin = (Message) input.readObject();
            if (messageLogin.getLoginDTO() != null) {
                player = messageLogin.getLoginDTO().getPlayer();
                login.setPlayer(player);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logout() {
        Message message = new Message();
        message.setCommand(Message.LOGOUT);
        listener.shutdownNow();
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateListUserResponse(List<Player> players, List<Player> playersOnline, List<Player> playersInGame) {
        System.out.println("update danh sach");
        mainFrm.updatePlayerTable(players, playersOnline, playersInGame);
    }

    public void updateListUserRequest() {
        Message message = new Message();
        message.setCommand(Message.UPDATEONLINE);
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void battleRespone(Message message) {
        //System.out.println("xxxxx");
        if (message.getBattleDTO().getStatus() == BattleDTO.REQUEST) {
            System.out.println(player.getUsername() + "   get resquest");
            if (!(mainFrm.showRequestBattle(message.getBattleDTO().getOpponent()) == JOptionPane.YES_OPTION)) {
                message.getBattleDTO().setAccept(false);
            }
            try {
                //System.out.println(message.toString());
                output.writeObject(message);
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (message.getBattleDTO().getStatus() == BattleDTO.RESPONSE) {
            //System.out.println(player.getUsername()+"   get response");
            mainFrm.playGame(message);
        }
    }

    public boolean battleRequire(Player opponent) {
        Message message = new Message();
        message.setCommand(Message.BATTLE);
        message.setBattleDTO(new BattleDTO(opponent));
        message.getBattleDTO().setAccept(true);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            //Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }

    public void readyRequire() {
        try {
            Message msg = new Message();
            msg.setCommand(Message.READY);
            output.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readyResponse(Message message) {
        //System.out.println("get ready response");
        readyFrm.playGame(message.getReadyDTO().getTurn());
    }

    public void shootRequest(int gridX, int gridY) {
        try {
            Message message = new Message();
            ShootDTO sdto = new ShootDTO();
            sdto.setX(gridX);
            sdto.setY(gridY);
            sdto.setStatus(0);
            sdto.setWin(false);
            message.setCommand(Message.SHOOT);
            message.setShootDTO(sdto);

            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void shootRespone(Message msg) {
        if (msg.getShootDTO().getStatus() == 0) {
            //System.out.println(player.getUsername());
            int x = msg.getShootDTO().getX();
            int y = msg.getShootDTO().getY();
            msg.getShootDTO().setStatus(1);
            int status = battleViewFrm.getHitStatus(x, y);

            switch (status) {
                case 1 ->
                    msg.getShootDTO().setHit(true);
                case 2 ->
                    msg.getShootDTO().setHit(false);
                case 3->{
                    return;
                }
                default -> {
                    msg.getShootDTO().setWin(true);
                    msg.getShootDTO().setHit(true);
                }
            }
            try {
                output.writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (battleViewFrm.getHp() == 0) {
                battleViewFrm.loseHandle();
            }
        } else {
            ShootDTO dto = msg.getShootDTO();
            battleViewFrm.showHitStatus(dto.getX(), dto.getY(), dto.isHit());
        }
    }

    public void updatePointRequest(int point) {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setPoint(point);
        Message message = new Message();
        message.setCommand(Message.POINT);
        message.setPointDTO(pointDTO);
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void matchHistoryRequest(){
        MatchHistoryDTO historyDTO = new MatchHistoryDTO();
        Message message = new Message();
        message.setHistoryDTO(historyDTO);
        message.setCommand(Message.MATCHHISTORY);
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void matchHistoryReponse(Message message) {
        System.out.println("Lich su dau");
        historyFrm.renderHistoryTable(message.getHistoryDTO().getMatchHistories());
    }
 
    
    public void exitGameReponse(Message  message){
        battleViewFrm.exitGame();
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
