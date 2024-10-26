/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MINH PHUC
 */
public class ServerControl {
        private ServerSocket serverSocket;
        private int serverPort;
        
        public ServerControl(){
            openServer();
            ClientListener();
        }
        
        private void openServer(){
            serverPort = 23000;
            try {
                serverSocket = new ServerSocket(serverPort);
            } catch (IOException ex) {
                
            }
        }
        
        private void ClientListener(){
            while(true){
                try {
                    Socket socket = serverSocket.accept();
                    ExecutorService executorService = Executors.newFixedThreadPool(100);
                    executorService.submit(new ClientListener(socket));
                    executorService.shutdown();
                } catch (IOException ex) {
                    Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
}
