package clientside;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iLLuMinatTih
 */

//class which handles data exchange between a particular client socket and server socket
class DataExchangeClient 
{
    private String serverIP;
    private int serverPort;
    private Socket cSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public DataExchangeClient(String serverIP,int serverPort)
    {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.makeNewConnection();   
    }
    
    public void setServerIP(String serverIP) // make connection to a new server or update IP of current server
    {
        
        this.serverIP = serverIP;
        this.makeNewConnection();           
    }
    public String getServerIP()
    {
        return serverIP;
    }
    
    public void setServerPort(int port) // make connection with new port
    {
        serverPort = port;
        this.makeNewConnection();
    }
    public int getServerPort()
    {
        return serverPort;
    }
    public Socket getSocket()
    {
        return cSocket;
    }
    public void closeConnection()
    {
        try {
            if((oos!= null && ois!=null) && cSocket!=null)
            {
                oos.close();
                ois.close();
                cSocket.close();
                System.out.println("Connection closed with Auth Server...");
            }
        } catch (IOException ex) {
            Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void makeNewConnection()
    {
        try {
            //closing any existing connections..
            closeConnection();
            
            //making new connections
            cSocket = new Socket(serverIP,serverPort);
            System.out.println("Connection Successful with Server IP: " + this.getServerIP() + "and Port: " + this.getServerPort());
            oos = new ObjectOutputStream(cSocket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(cSocket.getInputStream());
            System.out.println("All the streams created successfully");
        } catch (IOException ex) {
            System.out.println("Connection Problem!");
            Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(String[] data)
    {
            try {
                for (String data1 : data) {
                    oos.writeObject(data1);
                    oos.flush();
                    System.out.println("Sent: " + data1);
                }
                System.out.println("All data successfully sent");
            } catch (IOException ex) {
                Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public String getData()
    {            
        try {
            String data = (String) ois.readObject();
            System.out.println("Recieved: " + data);
            return data;
        } catch (IOException ex) {
            Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public Object getObject()
    {
            Object data;
            try {
                try {
                    data = ois.readObject();
                    System.out.println("Recieved: " + data);
                    return data;
                } catch (IOException ex) {
                    Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    
    public void sendObject(Object obj)
    {
        try {
                oos.writeObject(obj);
                oos.flush();
                System.out.println("Sent: " + obj);
        } catch (IOException ex) {
            Logger.getLogger(DataExchangeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
