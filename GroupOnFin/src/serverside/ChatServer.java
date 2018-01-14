
package serverside;

import com.mysql.fabric.Server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iLLuMinaTi
 */
class ChatServer extends ServerClass{
    private ServerSocket listenSocket;
    private Socket clientSocket;
    static ArrayList<SessionClass> sessionArr; //maintain array of all the sessions[ now instead of the session variable , just pass the index of array list]
    private Util utility = null;
    static Socket dashServerSocket;
    static ObjectOutputStream oosDash;        
    public ChatServer(int port)
    {
        try 
        {   
            
            dashServerSocket=new Socket("localhost",1221);
             oosDash=new ObjectOutputStream(dashServerSocket.getOutputStream());
             oosDash.flush();
            listenSocket = new ServerSocket(port);
            
            sessionArr=new  ArrayList<SessionClass> (100);
           
            System.out.println("Chatserver Started Listening....");
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
                System.out.println("New instance of Client Connected with chat server");
                //Making thread for each client            
                Thread chatServerThread = new Thread(new ChatServerThread(clientSocket,utility));
                chatServerThread.start(); //started the thread for receiving the messages from recently connected client
            }catch (IOException ex) 
            {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
