package serverside;

import com.mysql.fabric.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iLLuMinatTi
 */

//server to fascilitate users online, workspaces of user and search the users etc.. facilities (except chat)
class DashServer extends ServerClass
{
    private ServerSocket listenSocket;
    private Socket clientSocket;
    private JDBCUtil utility = null; 
    private boolean chatServerConn = false;
    public static ArrayList<DataExchangeServer> arrDes = new ArrayList<DataExchangeServer>(); 
    
    public DashServer(int port)
    {
        try 
        {   
            listenSocket = new ServerSocket(port);
            System.out.println("Dashserver Started Listening....");
            utility = new JDBCUtil(); //this object of Util will be passed in every thread beacause it has connection made with Database
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
                if(!chatServerConn) //if chat server not connected, then first connection would be of chat server
                {
                    System.out.println("Connected with Chat Server and making thread to handle it...");
                    Thread chatDash = new Thread(new ChatDashThread(clientSocket,utility));
                    chatDash.start();
                    chatServerConn = true;
                    continue;
                }
                System.out.println("New Client Connected to Dash Server..");
                
                //Making dataexchange for this newly connected client
                DataExchangeServer des = new DataExchangeServer(clientSocket);
                
                //Getting clients UID and username to set it in dataexchangeserver
                des.setUID(des.getData());
                des.setUsername(des.getData());
                
                //Adding the des in the arraylist
                arrDes.add(des);
       
                
                //Making thread for each client
                Thread dashRecvThread = new Thread(new DashServerThread(clientSocket,utility,des));
                dashRecvThread.start(); //started the thread for receiving the messages from recently connected client
                
            }catch (IOException ex) 
            {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
