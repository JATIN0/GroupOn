package serverside;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import clientside.Message;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author iLLuMinaTi
 */
//this is Util class for uitlity functions...
class Util 
{
    private JDBCUtil db = new JDBCUtil();
    
    //to check whether approved login or not
    public String[] loginCheck(String uName,String pass)
    {
        String query = "SELECT UID FROM userdetails WHERE UName = ?";
        String [] params = {uName};
        ResultSet rs = db.selectQuery(query,params);
        try {
                 // Extract data from result set
                if(rs!=null)
                {
                    while (rs.next()) 
                    {
                       // Retrieve by column name
                       String uid= rs.getString("UID"); //  column-name/attribute

                       //Check if password is matching
                       query = "SELECT UName,FName,LName FROM userdetails WHERE UName = ? AND Pass = ?";
                       String[] params1 = {uName,pass};
                       rs = db.selectQuery(query,params1);
                       if(rs!=null)
                       {
                            while(rs.next())
                            {
                                String [] ret  = {uid,rs.getString("FName"),rs.getString("LName")};

                                // Display values
                                System.out.println("You are logged in !!\nUID: " + uid + "\nUsername: "+ rs.getString("UName"));
                                query = "UPDATE userdetails SET isLoggedIn = ? ,lastLogged = ? WHERE UName = ? ";
                                
                                LocalDateTime dt = LocalDateTime.now();
                                String sqlPattern = "yyyy-MM-dd HH:mm:ss";
                                String currSqlTime = dt.format(DateTimeFormatter.ofPattern(sqlPattern));
                                
                                db.uiQuery(query, new String[] {"1",currSqlTime,uName});
                                return ret;
                            }
                       }

                       // row in rs is empty
                       System.out.println("Incorrect Username or Password...");
                       String [] ret = {"IUP"};
                       return ret;
                    }
                }
                 //if row in rs is empty
                  String [] ret = {"UDE"};
                 System.out.print("Username doesn't exist!!\n");
                 return ret;
                 
             } catch (SQLException ex) {
                 System.out.println("rs closed or database connection error or improper column name..");
                 Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                 return null;
             }
    }
    
    
    //to check whether register details are correct or not
     public String registerCheck(String firstName,String lastName,String userName,String password){        
        String error;
        if(("").equals(firstName)||("").equals(lastName)||("").equals(userName)||"".equals(password)){
            error="Some fields are empty!! \nPlease check again";
            System.out.println(error);
            return error;
        }
            
        
        int passLen=password.length();
        if(passLen<8){
            error="Password length must be greater than 8 characters!";
            System.out.println(error);
            return error;           
        }        
        String query = "SELECT `UName` FROM userdetails WHERE UName = ?";
        String [] params = {userName};
        
        //System.out.println("username = "+userName);
        ResultSet rs = db.selectQuery(query,params);
        try {
            if(rs.next()){
                error="Username already exists!";
                System.out.println(error);
                return error;           
            }
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tmp="ok";
        return tmp;
    }
    
    
    
    
    
    public boolean register(String firstName,String lastName,String userName,String password)
    {
        String query1 = "INSERT INTO `userdetails`(`FName`,`LName`,`UName`,`Pass`) VALUES(?,?,?,?)";
     
        String uid;
       // int id=1;
       // System.out.println("in util , in register ,value of id is "+id);
        //            if(rs.next())
//                id=rs.getInt("TOT");
//            
//            id++;
//            System.out.println("value of id is "+id);
//            uid = String.valueOf(id);
//            System.out.println("value of uid is "+uid);
//            @SuppressWarnings("MismatchedReadAndWriteOfArray")
        String[] params1={firstName,lastName,userName,password};
// PreparedStatement stmt =  conn.prepareStatement(query);
        boolean res=db.uiQuery(query1,params1);
        return res;     
    }
    
    public boolean insertMessage(int rid,int uid,String content,int priority,String sorg,String date)
    {
        String query = "INSERT INTO `message`( `RID`, `UID`,`Content`,`Priority`,`SorG`,`DateTime`) VALUES(?,?,?,?,?,?)";    
        String[] params={Integer.toString(rid),Integer.toString(uid),content,Integer.toString(priority),sorg,date};
        boolean res=db.uiQuery(query,params);
        return res;
    }
   
    
    
    
    
    
    public int getUid(String userName){
        String query = "SELECT `UID` FROM `userdetails` WHERE `UNAME`=?";
        String [] params={userName};
        
        ResultSet rs=db.selectQuery(query,params);
        int id=0;
        try {            
            if(rs.next())
                id=rs.getInt("UID");
            
            return id;
        }catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }     
    }
    
    public String getUserName(String uid){
        String query = "SELECT `UName` FROM `userdetails` WHERE `UID`=?";
        String [] params={uid};
        
        ResultSet rs=db.selectQuery(query,params);
        String name="";
        try {            
            if(rs.next())
                name=rs.getString("UName");
            
            return name;
        }catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return name;
        }     
    }
    
    //this function will give old messages where sender is either sUid or rUid
    public ArrayList<Message> getOldMsgsSingle(String sUid,String rUid,String sUName,String rUName)
    {
        ArrayList<Message> arrMsg = new ArrayList<Message> ();
        
        String query = "SELECT * FROM `message` WHERE ((`UID` = ? AND `RID` = ? AND `SorG` = ?) OR (`UID` = ? AND `RID` = ? AND `SorG` = ?)) ORDER BY `DateTime`";
        String [] params = {sUid,rUid,"S",rUid,sUid,"S"};
        ResultSet rs = db.selectQuery(query, params);
        if(rs!=null)
        {
            try {
                while(rs.next())
                {
                    String sender,reciever;
                    
                    //checking for sender and reciever
                    if(rs.getString("UID").equals(sUid))
                    {
                        sender = sUName;
                        reciever = rUName;
                    }
                    else
                    {
                        sender = rUName;
                        reciever = sUName;
                    }
                    //adding the message in arraylist
                    
                    String dtm = rs.getString("DateTime").substring(0, 19);
                    System.err.println("in rs, DateTime is " + dtm);
                    arrMsg.add(new Message(rs.getInt("MsgID"),sender,reciever,dtm,rs.getString("Content"),rs.getInt("Priority")));
                }
                return arrMsg;
            } catch (SQLException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }
    
    
    
    
    
    
}
