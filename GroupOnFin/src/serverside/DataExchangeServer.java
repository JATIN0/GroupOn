package serverside;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iLLuMinatTi
 */

//This class fascillitates DataExchangeServer of server socket with the client sockets connected
class DataExchangeServer 
{
    private Socket sSocket;
    private String uid;
    private String username;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public DataExchangeServer(Socket sSocket)
    {
       this.sSocket = sSocket;
       this.makeStreams();
    }
   
    private void makeStreams()
    {
        try {
            //making the streams
            oos = new ObjectOutputStream(sSocket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(sSocket.getInputStream());
            System.out.println("All the streams created successfully");
        } catch (IOException ex) {
            System.out.println("Connection Problem!");
            Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isSocketNull()
    {
        return sSocket == null;
    }
    public void closeConnection()
    {
        try {
            if((oos!= null && ois!=null) && sSocket!=null)
            {
                oos.close();
                ois.close();
                sSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getData()
    {
        try {
            String data = (String) ois.readObject();
            System.out.println("Recieved: " + data);
            return data;
        } catch (IOException ex) {
                System.out.println("Socket Closed");
                Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
            return ""; 
        }
    }
    
    public Object getObject()
    {
        try {
            Object data;
            try {
                data = ois.readObject();
                System.out.println("Recieved: " + data);
            return data;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataExchangeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUID(String uid)
    {
        this.uid = uid;
    }
    public void setUsername(String usrname)
    {
        this.username = usrname;
    }
    public String getUid()
    {
        return this.uid;
    }
    public String getUName()
    {
        return this.username;
    }
}
