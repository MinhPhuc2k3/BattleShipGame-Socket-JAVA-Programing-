package controller.server;

import battle.ship.model.MatchHistory;
import battle.ship.model.Room;
import battle.ship.model.Player;
import dao.MatchHistoryDAO;
import dao.PlayerDAO;
import dto.BattleDTO;
import dto.ExitGameDTO;
import dto.Message;
import dto.LoginDTO;
import dto.PlayerDTO;
import dto.PointDTO;
import dto.ReadyDTO;
import dto.ShootDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MINH PHUC
 */
public class ClientListener implements Runnable {

    private static List<ClientListener> clientHandlers = new ArrayList<>();
    private static List<ClientListener> clientInGame = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private PlayerDAO playerDAO = new PlayerDAO();
    private MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO();
    private Player player;

    public ClientListener(Socket socket) {
        try {
            this.socket = socket;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            closeConnection();
        }
    }
    
    
    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Message message = (Message) input.readObject();
                switch (message.getCommand()) {
                    case Message.LOGIN -> {
                        login(message);
                    }
                    case Message.LOGOUT -> {
                        logout();
                    }
                    case Message.SHOOT -> {
                        System.out.println(player.getUsername() + "  request");
                        shoot(message);
                    }
                    case Message.BATTLE -> {
                        battle(message);
                    }
                    case Message.READY -> {
                        ready(message);
                    }
                    case Message.POINT -> {
                        point(message);
                    }
                    case Message.UPDATEONLINE -> {
                        updateListPlayerOnline();
                    }
                    case Message.MATCHHISTORY -> {
                        sendListMatchHistory(message);
                    }
                    case Message.EXITGAME -> {
                        exitGame();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                closeConnection();
                updateListPlayerOnline();
            }
        }

    }

    private void point(Message message){
        System.out.println(player.getUsername()+ " cap nhat diem");
        PointDTO point = message.getPointDTO();
        playerDAO.updatePoint(player, point.getPoint());
        Room.playerMap.remove(player.getUsername());
        updateListPlayerOnline();
    }
    
    private void shoot(Message message){
        System.out.println(player.getUsername() +"  playing");
        Room room = Room.playerMap.get(player.getUsername());
        try {
            ClientListener x = room.getOpponentHandler(this);
            System.out.println(x.player.getUsername());
            if(message.getShootDTO().isWin()){
                MatchHistory matchHistory = new MatchHistory();
                matchHistory.setLoser(player);
                matchHistory.setWiner(x.player);
                matchHistory.setTime((new Date(System.currentTimeMillis())));
                matchHistoryDAO.updateMatchHistory(matchHistory);
                playerDAO.updatePoint(player, -1);
                playerDAO.updatePoint(x.player, 1);
                Room.playerMap.remove(x.player.getUsername());
                Room.playerMap.remove(player.getUsername());
                clientInGame.remove(this);
                clientInGame.remove(x);
            }
            x.output.writeObject(message);
            x.output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void login(Message messenge) {
        LoginDTO login = messenge.getLoginDTO();
        Player playerLogin = new Player();
        playerLogin.setUsername(login.getUsername());
        playerLogin.setPassword(login.getPassword());
        player = playerDAO.getPlayer(playerLogin);
        Message res = new Message();
        login.setPlayer(player);
        res.setLoginDTO(login);
        try {
            output.writeObject(res);
            output.flush();
            if (player != null) {
                clientHandlers.add(this);
                updateListPlayerOnline();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void logout() {
        Message res = new Message();
        this.player = null;
        try {
            output.writeObject(res);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateListPlayerOnline();
    }

    private void battle(Message message) {
        Player opponent = message.getBattleDTO().getOpponent();
        boolean hasRespone = false;
        for (ClientListener x : clientHandlers) {
            if (x.player.getUsername().equals(opponent.getUsername())) {
                System.out.println(player.getUsername() + "  sending..." + x.player.getUsername());
                if (Room.playerMap.containsKey(x.player.getUsername())) {
                    if (Room.playerMap.get(x.player.getUsername()).getNumber() == 1) {
                        message.getBattleDTO().setStatus(BattleDTO.RESPONSE);
                        Room room = Room.playerMap.get(x.player.getUsername());
                        if (message.getBattleDTO().isAccept()) {
                            try {
                                room.addPlayer(player.getUsername(), this);
                                clientInGame.add(this);
                                updateListPlayerOnline();
                                output.writeObject(message);
                                output.flush();
                                x.output.writeObject(message);
                                x.output.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            clientInGame.remove(this);
                            try {
                                clientInGame.remove(x);
                                room.removePlayer(x.player.getUsername());
                                x.output.writeObject(message);
                            } catch (IOException ex) {
                                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            updateListPlayerOnline();
                        }
                    }else if(Room.playerMap.get(x.player.getUsername()).getNumber() == 2){
                        try {
                            message.getBattleDTO().setInGame(true);
                            output.writeObject(message);
                            output.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }  else {
                    try {
                        Room room = new Room();
                        room.addPlayer(player.getUsername(), this);
                        message.getBattleDTO().setStatus(BattleDTO.REQUEST);
                        System.out.println(player.getUsername() + " tạo phòng ");
                        message.getBattleDTO().setOpponent(player);
                        clientInGame.add(this);
                        updateListPlayerOnline();
                        x.output.writeObject(message);
                        x.output.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                hasRespone = true;
                break;
            }
        }
        if (hasRespone == false) {
            try {
                message.getBattleDTO().setAccept(false);
                output.writeObject(message);
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void ready(Message message) {
        Room room = Room.playerMap.get(player.getUsername());
        room.increaseReady();
        System.out.println(player.getUsername() + "  get ready");
        if (room.getNumberOfReady() == 2) {
            try {
                int turn = 0;
                for (ClientListener x : room.getListHandler()) {
                    ReadyDTO dto  = new ReadyDTO();
                    dto.setTurn(turn);
                    message.setReadyDTO(dto);
                    x.output.writeObject(message);
                    turn  = 1-turn;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void closeConnection() {
        System.out.println("Đóng kết nối với " + player.getUsername());
        try {
            if (socket != null) {
                socket.close();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            
            if(player!=null && Room.playerMap.containsKey(player.getUsername())){
                Room room = Room.playerMap.get(player.getUsername());
                for(ClientListener x:room.getListHandler()){
                    if(x!=this){
                        Message message = new Message();
                        ExitGameDTO exitGameDTO = new ExitGameDTO();
                        message.setExitGameDTO(exitGameDTO);
                        message.setCommand(Message.EXITGAME);
                        x.output.writeObject(message);
                    }
                }
            }
            clientInGame.remove(this);
            clientHandlers.remove(this);
            System.out.println("So nguoi online la: "+clientHandlers.size());
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateListPlayerOnline() {
        System.out.println("Server gui cap nhat danh sach player");
        Message message = new Message();
        message.setCommand(Message.UPDATEONLINE);
        PlayerDTO playerDTO = new PlayerDTO();
        List<Player> playerOnline = new ArrayList<>();
        List<Player> playerInGame = new ArrayList<>();
        for (ClientListener handler : clientHandlers) {
            if (handler.player != null) {
                playerOnline.add(handler.player);
            }
        }
        
        for(ClientListener handler : clientInGame){
            playerInGame.add(handler.player);
        }
        playerDTO.setPlayersInGame(playerInGame);
        playerDTO.setPlayers(playerDAO.getListPlayer());
        playerDTO.setPlayersOnline(playerOnline);
        message.setPlayerDTO(playerDTO);
        for (ClientListener tmp : clientHandlers) {
            try {
                if (tmp.player != null) {
                    tmp.output.writeObject(message);
                    tmp.output.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void  sendListMatchHistory(Message message){      
        message.getHistoryDTO().setMatchHistories(matchHistoryDAO.getMatchHistory(player));
        message.setCommand(Message.MATCHHISTORY);
        try {
            output.writeObject(message);
            output.flush();
            System.out.println("lich su dau");
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void exitGame(){
                Room room = Room.playerMap.get(player.getUsername());
                ClientListener x = room.getOpponentHandler(this);
                MatchHistory matchHistory = new MatchHistory();
                matchHistory.setLoser(x.player);
                matchHistory.setWiner(player);
                matchHistory.setTime((new Date(System.currentTimeMillis())));
                matchHistoryDAO.updateMatchHistory(matchHistory);
                playerDAO.updatePoint(x.player, -1);
                playerDAO.updatePoint(player, 1);
                Room.playerMap.remove(player.getUsername());
                Room.playerMap.remove(x.player.getUsername());
                clientInGame.remove(this);
                clientInGame.remove(x);
                updateListPlayerOnline();
    }
}
