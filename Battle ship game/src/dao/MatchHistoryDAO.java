/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import battle.ship.model.MatchHistory;
import battle.ship.model.Player;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author MINH PHUC
 */
public class MatchHistoryDAO extends DAO{
    public MatchHistoryDAO(){
        super();
    }
    
    public void updateMatchHistory(MatchHistory matchHistory){
        String sql  = "INSERT INTO matchhistory(winer_id, loser_id, time) VALUES (?,?,?)";
        PreparedStatement stmt = null;
        getConnection();
        try {
            con.setAutoCommit(false);
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, matchHistory.getWiner().getId());
            stmt.setInt(2, matchHistory.getLoser().getId());
            stmt.setTimestamp(3, new Timestamp(matchHistory.getTime().getTime()));
            stmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(MatchHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(stmt!=null) stmt.close();
                if(con!=null) con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MatchHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<MatchHistory> getMatchHistory(Player player){
        List<MatchHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM matchhistory WHERE winer_id = ? or loser_id = ?";
        PreparedStatement stmt  = null;
        ResultSet rs = null;
        getConnection();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, player.getId());
            stmt.setInt(2, player.getId());
            rs = stmt.executeQuery();
            while(rs.next()){
                MatchHistory match = new MatchHistory();
                Player p = new Player();
                p.setId(rs.getInt("winer_id"));
                match.setWiner(p);
                p = new Player();
                p.setId(rs.getInt("loser_id"));
                match.setLoser(p);
                match.setTime(rs.getTimestamp("time"));
                list.add(match);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(stmt!=null) stmt.close();
                if(rs!=null) rs.close();
                if(con!=null) con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MatchHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}
