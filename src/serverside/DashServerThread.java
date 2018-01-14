package serverside;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iLLuMinatTi
 */
class DashServerThread implements Runnable
{
    private DataExchangeServer des =null;
    private JDBCUtil jUtil =null;
    private String uid;
    private String uName;
    
    public DashServerThread(Socket sock, JDBCUtil jUtility,DataExchangeServer des)
    {      
        this.des= des;
        this.jUtil = jUtility;
        this.uName = jUtil.getUserName(uid);
    }
  
    @Override
    public void run()
    {
       //sending notifications from database
//        des.send(new String[] {"updateNotif"});
//        ArrayList<String> arrNotif;
//                arrNotif = jUtil.getAllNotifs(uid,uName);
//                
//                //sending all the notifs
//                for(int i=0;i<arrNotif.size();i++)
//                    des.send(new String [] {arrNotif.get(i)});
//                
//                //sending end notif
//                des.send(new String[] {"<>?"});
//        
        //after connection, it will take the uid of the user...
        
        
        uid = des.getData();
        //uName = des.getData();
        while(true)
        {
            
            String func = des.getData();
            System.out.println("func is : " + func);
            if(func.equals(""))
                break;
//            switch(func)
//            {
//                case "getOnlineUsers":
//                    this.getOnlineList();
//                    break;
//                default:
//            }

            if(func.equals("getOnlineUsers")){
                this.getOnlineList();
            }
            
            else if(func.equals("searchUser")){
                String name=des.getData();
                System.out.println("just outside search ( " + name + ")");
                this.searchUser(name);
            }
            
        }
        
    }
    
    
    public void searchUser(String name)
    {    
        System.err.println("inside SearchUser func and name="+name);
        String query = "SELECT UID,UName,FName,LName FROM userdetails WHERE UName LIKE ?";
        String x="%"+name+"%";
        String [] param = {x};
        ResultSet rs = jUtil.selectQuery(query, param);
        if(rs!=null)
        {
            //sending type of message
            des.send(new String[] {"search"});
            try {
                    while(rs.next())
                    {
                        if(!rs.getString("UID").equals(uid))
                        {
                            String [] entry = {rs.getString("UName") + "(" + rs.getString("FName") + " " + rs.getString("LName") + ")"};
                            des.send(entry);
                        }
                        
                    }
                    //After getting all entries, send end signature
                    String [] end = {"<>?"};
                    des.send(end);
            } catch (SQLException ex) {
                Logger.getLogger(DashServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    //this will send list of all the online users except itself
    public void getOnlineList()
    {
        System.out.println("inside getOnlineList func");
        String query = "SELECT UID,UName,FName,LName FROM userdetails WHERE isLoggedIn = ?";
        String [] param = {"1"};
        ResultSet rs = jUtil.selectQuery(query, param);
        if(rs!=null)
        {
            //sending type of message
            des.send(new String[] {"list"});
            try {
                    while(rs.next())
                    {
                        if(!rs.getString("UID").equals(uid))
                        {
                            String [] entry = {rs.getString("UName") + "(" + rs.getString("FName") + " " + rs.getString("LName") + ")"};
                            des.send(entry);
                        }
                        
                    }
                    //After getting all entries, send end signature
                    String [] end = {"<>?"};
                    des.send(end);
            } catch (SQLException ex) {
                Logger.getLogger(DashServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
