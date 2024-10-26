package dto;


import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int LOGIN = 1;
    public static final int LOGOUT = 2;
    public static final int SHOOT = 3;
    public static final int UPDATEONLINE = 4;
    public static final int BATTLE = 5;
    public static final int READY = 6;
    public static final int POINT=7;
    public static final int MATCHHISTORY=8;
    private int command;
    private LoginDTO loginDTO;
    private PlayerDTO playerDTO;
    private BattleDTO battleDTO;
    private ReadyDTO readyDTO;
    private ShootDTO shootDTO;
    private PointDTO pointDTO;
    private MatchHistoryDTO historyDTO;

    public MatchHistoryDTO getHistoryDTO() {
        return historyDTO;
    }

    public void setHistoryDTO(MatchHistoryDTO historyDTO) {
        this.historyDTO = historyDTO;
    }
    
    
    
    public PointDTO getPointDTO() {
        return pointDTO;
    }

    public void setPointDTO(PointDTO pointDTO) {
        this.pointDTO = pointDTO;
    }
    
    
    public ShootDTO getShootDTO() {
        return shootDTO;
    }

    public void setShootDTO(ShootDTO shootDTO) {
        this.shootDTO = shootDTO;
    }
    
    
    public ReadyDTO getReadyDTO() {
        return readyDTO;
    }

    public void setReadyDTO(ReadyDTO readyDTO) {
        this.readyDTO = readyDTO;
    }
    
    
    public BattleDTO getBattleDTO() {
        return battleDTO;
    }

    public void setBattleDTO(BattleDTO battleDTO) {
        this.battleDTO = battleDTO;
    }
    
    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
    }

    @Override
    public String toString() {
        return "Message{" + "command=" + command + ", loginDTO=" + loginDTO + ", playerDTO=" + playerDTO + ", battleDTO=" + battleDTO + '}';
    }
 
    
    
    
}