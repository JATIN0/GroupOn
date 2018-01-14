package serverside;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iLLuMinatTi
 */

//this thread will originate form main thread of dash server, which will constantly listen to chat server, for notifications...
class ChatDashThread implements Runnable
{
    Socket dashSocket;
    ObjectInputStream ois ;
    ObjectOutputStream oos;
    ServerNotification servNot;
    JDBCUtil utility;
    public ChatDashThread(Socket sock,JDBCUtil util)
    {
        try {
            dashSocket = sock;
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.flush();
           // Thread.sleep(1000);
            
            ois = new ObjectInputStream(sock.getInputStream());
            System.out.println("All streams of dash server with chat server are created...");
            utility = util;
        } catch (IOException ex) {
            Logger.getLogger(ChatDashThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run()
    {
        while(true)
        {
            try {
                System.out.println("Before Reading..");
                servNot = (ServerNotification) ois.readObject();
                System.out.println("Recieved Notif :  " + servNot);
                sendNotif();
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ChatDashThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void sendNotif()
    {
                String sName = servNot.getSender();
                String rName = servNot.getReciever();
                
             
                        //sending the notification to the user with ruid as uid, by checking their des and also sending through them
                        for(int i=0;i<DashServer.arrDes.size();i++)
                        {
                            //if ruid equals to uid of any user currently online (connected to the dash server) then send it..
                            if(DashServer.arrDes.get(i).getUName().equals(rName))
                            {
                                //if notification type is a message
                                if(servNot.getType().equals("message"))
                                {
                                    //sending with type of message also
                                    DashServer.arrDes.get(i).send(new String[]{"notification",sName + " sent you a message.. To see, select this and click on view       " + servNot.getDateTime()});
                                }
                                break;
                            }
                        }
                        System.out.println("After loop");    
                        //Storing this notification in DataBase also(for both online and offline users)...
                        String query = "INSERT INTO notification (Type,SUName,RUName,DateTime) VALUES (?,?,?,?)";
                        utility.uiQuery(query,new String [] {servNot.getType(),sName,rName,servNot.getDateTime()});
                
                
    }
}
