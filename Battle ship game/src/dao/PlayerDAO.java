/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import battle.ship.model.Player;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author MINH PHUC
 */
public class PlayerDAO extends DAO{
    
    public PlayerDAO() {
        super();
    }
   
    public Player getPlayer(Player player){
        Player playerLogin = null;
        String sql =  "SELECT * from Player where username = ? and password = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        getConnection();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1,  player.getUsername());
            stmt.setString(2, player.getPassword());
            rs = stmt.executeQuery();
            if(rs.next()){
                playerLogin = new Player();
                playerLogin.setId(rs.getInt("id"));
                playerLogin.setEmail(rs.getString("email"));
                playerLogin.setUsername(rs.getString("username"));
                playerLogin.setPassword(rs.getString("password"));
                playerLogin.setDiem(rs.getInt("diem"));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } 
        finally{
            try{
                if(con!=null) con.close();
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return playerLogin;
    }
    
    public List<Player> getListPlayer(){
        List<Player> players = new ArrayList<>();
        String sql =  "SELECT * from Player";
        Statement stmt = null;
        ResultSet rs = null;
        getConnection();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            Player player;
            while(rs.next()){
                player = new Player();
                player.setId(rs.getInt("id"));
                player.setEmail(rs.getString("email"));
                player.setUsername(rs.getString("username"));
                player.setDiem(rs.getInt("diem"));
                players.add(player);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } 
        finally{
            try{
                if(con!=null) con.close();
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return players;
    }
    
    public void updatePoint(Player player, int point){
        String sql = "UPDATE Player SET diem = ? WHERE id=?";
        PreparedStatement stmt = null;
        getConnection();
        try {
            con.setAutoCommit(false);
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Math.max(0, player.getDiem()+point));
            stmt.setInt(2, player.getId());
            stmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(stmt!=null) stmt.close();
                if(con!=null) con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
