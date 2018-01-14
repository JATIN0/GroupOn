package serverside;

import java.io.Serializable;

/**
 *
 * @author iLLuMinatTi
 */
class ServerNotification implements Serializable
{
    private String sender; //sender's name will be sent by server, after searching it from database
    private String reciever;
    private String type;
    private String dateTime;
    
    public ServerNotification(String sender,String reciever,String type, String dateTime)
    {
        this.sender = sender;
        this.reciever = reciever;
        this.type = type;
        this.dateTime = dateTime;
    }
    
   public String getSender()
   {
       
       return sender;
   }
   
   public String getReciever()
   {
       return reciever;
   }
    
   public String getType()
   {
       return type;
   }
    
   public String getDateTime()
   {
       return dateTime;
   }
}
