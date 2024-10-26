package battle.ship.model;

import java.io.Serializable;
import java.util.Date;

public class MatchHistory implements Serializable{
    private Player winer;
    private Player loser;
    private Date time;

    public Player getWiner() {
        return winer;
    }

    public void setWiner(Player winer) {
        this.winer = winer;
    }

    public Player getLoser() {
        return loser;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
}
