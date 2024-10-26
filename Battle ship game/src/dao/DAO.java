/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * @author MINH PHUC
 */
public abstract class DAO {
    protected Connection con;
    private static String DB_URL = "jdbc:mysql://localhost:3306/battleshipgame";
    private static String USER_NAME="root";
    private static String PASSWORD = "123456";

    public DAO() {
    }
     
    protected void getConnection(){
            con = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
              //  System.out.println(DB_URL+" "+USER_NAME+" "+PASSWORD);
                con = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
              //  System.out.println("connect successfully!");
            } catch (Exception ex) {
                //System.out.println("connect failure!");
                ex.printStackTrace();
            }
    }
}
