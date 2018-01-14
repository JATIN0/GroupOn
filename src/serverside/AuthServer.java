package serverside;

import com.mysql.fabric.Server;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author iLLuMinaTi
 */
class AuthServer extends ServerClass{
    private ServerSocket listenSocket;
    private Socket clientSocket;
    private Util utility = null; 
    public AuthServer(int port)
    {
        try 
        {   
            listenSocket = new ServerSocket(port);
            System.out.println("Authserver Started Listening....");
            utility = new Util(); //this object of Util will be passed in every thread beacause it has connection made with Database
        }catch (IOException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override   
    public void communicate(){        
        while(true){
            try 
            {
                clientSocket = listenSocket.accept();//this will return the socket with client's information in it and connection established with the client
                //.accept() will freeze the program till new socket isn't recieved                            
                System.out.println("New Client Connected");
                //Making thread for each client
                Thread authRecvThread = new Thread(new AuthServerThread(clientSocket,utility));
                authRecvThread.start(); //started the thread for receiving the messages from recently connected client
                
            }catch (IOException ex) 
            {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}   
