package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JAshMe
 */

//This class is made for JDBC Functions...
class JDBCUtil 
{
    private Connection conn;
    
    public JDBCUtil()
    {
        this.makeConnection();
    }
    private void makeConnection()
    {
            
            try {
                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                
                // Open a connection
                System.out.println("Connecting to database...\n");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupon", "root", "");
                System.out.println("Connection Successful..\n");   
            }catch (SQLException se) {
            System.out.println("SQLException occured");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found Exception...");
        }

    }
    
    private void closeConnection()
    {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public boolean uiQuery(String query, String []params) //for update and insert
    {
            
            try //for update and insert
            {
                
                //Insert a record (Example of insert query)
                /*String insertSql = "INSERT INTO `student`( `regno`, `name`,`section`) VALUES(?,?,?)";*/
                PreparedStatement stmt =  conn.prepareStatement(query);
                for(int i=0;i<params.length;i++)
                    stmt.setString(i+1,params[i]);
                stmt.executeUpdate();
                    
                //Update a record (Example of Update query)
                /* String updateSql = "UPDATE `student` SET `regno`='3000' WHERE regno='4000'";
                */
                
                return true;
                // Clean-up environment
            } catch (SQLException ex) {
                System.out.println("stmt error....");
                Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
                return false;
                
            }
    }
    
    public ResultSet selectQuery(String query ,String []params)
    {
         try {
             // Execute a query
             PreparedStatement stmt = conn.prepareStatement(query);
             for(int i=0;i<params.length;i++)
                    stmt.setString(i+1,params[i]);
             ResultSet rs = stmt.executeQuery();  // execute query
             System.out.println("Query executed..");
             
             
             return rs;
         } catch (SQLException ex) {
                    System.out.println("stmt error....");
                    Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
         
    }

public ArrayList<String> getAllNotifs(String rUid,String rUName)
    {
        ArrayList<String> arrNotif = new ArrayList<String> ();
        
        String query = "SELECT * FROM `notification` WHERE `RUName` = ? ORDER BY `DateTime`";
        String [] params = {rUName};
        ResultSet rs = selectQuery(query, params);
        if(rs!=null)
        {
            try {
                while(rs.next())
                {
                     
                    //getting correct date in correct format
                    String dtm = rs.getString("DateTime").substring(0, 19);
                    
                    System.err.println("in rs, DateTime is " + dtm);
                    LocalDateTime dt= LocalDateTime.parse(dtm,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    String chatPattern = "dd-MM-yyyy HH:mm:ss";
                    String currChatTime = dt.format(DateTimeFormatter.ofPattern(chatPattern));
                    arrNotif.add(rs.getByte("SUName") + " sent you a message.. To see, select this and click on view       " + currChatTime);
                }
                return arrNotif;
            } catch (SQLException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }
    



 public String getUserName(String uid){
        String query = "SELECT `UName` FROM `userdetails` WHERE `UID`=?";
        String [] params={uid};
        
        ResultSet rs= selectQuery(query,params);
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
 
}